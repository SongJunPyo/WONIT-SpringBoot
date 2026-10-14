// Controller: 서버 내부에 있는 어떤 함수에 매핑할 것인지 확인 후 이를 Service에 연결해주는 역할

package com.example.wonit.controller;

import com.example.wonit.dto.AccountCreateRequest;
import com.example.wonit.dto.AccountResponse;
import com.example.wonit.exception.AccountNotFoundException;
import com.example.wonit.model.Account;
import com.example.wonit.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
// 시스템 로그 / 사용자 로그(A가 B상품 5개를 결제했습니다. => 행동 양식 결정)
// 같은 방식으로 수집 및 활용
@Tag(name = "swagger 테스트 API", description = "swagger 테스트를 진행하는 API")

@RestController
@RequestMapping("/api/accounts") // localhost:8080/api/accounts
public class AccountController {

    // 주소창에서 2가지 방법으로 메서드 호출
    // 1. @PathVariable : 주소줄에 있는 값을 가져오는 방법
        // 예시: localhost:8080/api/accounts/1002-345-678901
    // 2. @RequestParam : 주소줄에 있는 ?뒤에 있는 값을 가져오는 방법
        // 예시: localhost:8080/api/accounts/balance?minBalance=500000&maxBalance=1000000

    // Inversion Of Control (개발자가 Bean으로 등록해놓은 객체는 스프링 컨테이너가 관리하므로 개발자는 가져다 쓰기만 하면 됩니다.)
    // AccountService aService = new AccountService();
    // 어차피 스프링컨테이너가 관리하는 bean이므로 private으로 막아둡니다.
    @Autowired // 서로서로 연결된 bean임을 알려줍니다. 생략하면 Springboot가 넣어줌
    private AccountService accountService;      // 외부에서 접근하지 못하도록 -> 이 클래스 안에서만 사용할 수 있도록

    // 모든 accounts를 조회
    // jpa의 규칙은 Repository에서만 영향을 미칩니다.
    @Operation(summary = "정보 모두 조회", description = "Account의 전체 정보를 조회합니다.")
    @GetMapping
    public List<AccountResponse> findAll() {
        // stream().filter() : 값이 true인 것만 반환
        // stream().map() : 모든 값에 같은 메서드를 일괄 적용
        return accountService.findAll().stream().map(AccountResponse::from).toList(); // Account -> AccountResponse로 변환
    }

    // JavaDoc <- Spring이 확인하고, 해당 Doc 내 메서드("findOne")에 포인터 올리면 함수 설명함
    // 개발자나 AI가 활용 가능
    // Swagger 보다 훨씬 편함
    /**
     * 계좌번호로 계좌 하나를 찾는다.
     * @param accountNo 찾을 계좌번호 (예: 1002-345-112233)
     * @return 찾은 계좌
     * @throws AccountNotFoundException 그런 계좌가 없을 때
     */
    // 특정 계좌번호의 세부 정보를 조회  localhost:8080/api/accounts/1002-345-678901
    @GetMapping("/{accountNo}")
    public AccountResponse findOne(@PathVariable("accountNo") String accountNo){
        log.trace("trace level의 로그 - 자잘함");
        log.debug("debug level의 로그 - 개발자가 flow 따라갈 수 있는 수준의 로그");
        log.info("info level의 로그 - 사용자나 시스템의 의미있는 내용을 담은 로그, 실제 운영환경에는 info 이상만 수집");
        log.warn("warn level의 로그 - 현재는 문제 없지만 앞으로 문제가 될 수 있는 상황에 대한 경고");
        log.error("error level의 로그 - 치명적인 로그");

        log.info("계좌 조회 = " + accountNo);   // 문자열을 더하는 과정을 거치고 나서 로그를 제외
        log.info("계좌 조회:={}", accountNo);   // 아예 보지도 않고 실행 -> (더 추천)
//        System.out.println(accountNo);
        return AccountResponse.from(accountService.getAccount(accountNo));
    }

    // 계좌 전체를 조회해서 그중에 '정상' 계좌만 계좌번호만 출력
    @GetMapping("/report1")
    public List<String> normalAccountNos() {
        return accountService.normalAccountNos();
    }

    // 전체 계좌중에 account_type이 '적금'인 경우만 금액을 모두 더해서 리턴
    // /api/accounts/report2 라는 주소로
    @GetMapping("/report2")
    public long savingBalanceSum() {
        return accountService.savingBalanceSum();
    }

    // min~ max balance 사이의 계좌 정보를 검색
    // http://localhost:8080/api/accounts/balance?minBalance=500000&maxBalance=1000000
    @GetMapping("/balance")
    public List<Account> getAccountBetweenBalance(@RequestParam long minBalance, @RequestParam long maxBalance) {
        return accountService.getAccountBetweenBalance(minBalance, maxBalance);
    }

    // balance 순서대로 작은 금액에서 큰 금액 순서로 모든 계좌 조회
    @GetMapping("/sort") // localhost:8080/api/accounts/sort
    public List<AccountResponse> findAllAccountsByBalanceASC() {    // DTO형식(왜? 불필요한 건 걸러서)

        // Controller단에서 자료형 변환(stream 기반)
        return accountService.findAllAccountsByBalanceASC().stream().map(AccountResponse::from).toList();    // Account -> AccountResponse로 변환
    }


    // status 에서 정상 / 휴면 / 지급정지 들어오면 각 상황에 맞는 계좌만 필터링
    // 또는 "localhost:8080/api/accounts/status=정상" -> 아래 이유로 인해 이 방식이 맞겠군
//    @GetMapping("/{status}") // localhost:8080/api/accounts/정상 -> 이 방식의 현재 단점: @GetMapping("/{accountNo}")와 입력이 겹침
//    public List<AccountResponse> findAllAccountsByStatus(@PathVariable String status) {    // DTO형식(왜? 불필요한 건 걸러서)

    // 입력 예시: localhost:8080/api/accounts/status?status=정상
    @GetMapping("/status")
    public List<AccountResponse> findAllAccountsByStatus(@RequestParam(value = "status") String status) {

        // Controller단에서 자료형 변환(stream 기반)
        return accountService.findAllAccountsByStatus(status).stream().map(AccountResponse::from).toList(); // Account -> AccountResponse로 변환
    }

    // account_type에 적금 / 입출금 들어오면 각 상황에 맞는 계좌만 필터링

    // 입력 예시: localhost:8080/api/accounts/account_type/적금
    @GetMapping("/account-type/{accountType}")
    public List<AccountResponse> findAllAccountsByAccountType(@PathVariable String accountType) {
        // Controller단에서 자료형 변환(stream 기반)
        return accountService.findAllAccountsByAccountType(accountType).stream().map(AccountResponse::from).toList(); // Account -> AccountResponse로 변환
    }

    // 주소줄 작성방법
    // 1. accountType (camelCase: Java 스타일)
    // 2. account_type (snake_case: DB 스타일)
    // 3. account-type (kebab-case: HTTP 스타일)


//    // @RequestBody: 숨가고 싶은 정보, 주소줄에 전달하기에 너무 긴 값들을 body에 싣고 넘기는 방법
//    // ResponseEntity: 응답코드를 구체적을 ㅗ넘겨주기 위한 스프링부트
//    @PostMapping
//    public ResponseEntity<AccountResponse> createAccount(
//            @RequestBody AccountCreateRequest request
//    ) {
//        Account account = accountService.createAccount(request);
//
//        // 새로 발급받은 _{accountNo}로 주소를 돌려서 호출하는 부분
//        URI location = ServletUriComponentsBuilder
//                .fromCurrentContextPath()
//                .path("/api/accounts/{accountNo}")
//                .buildAndExpand(account.getAccountNo())
//                .toUri();
//
//        return ResponseEntity
//                .created(location)
//                .body(AccountResponse.from(account));
//    }

    // @RequestBody: 숨가고 싶은 정보, 주소줄에 전달하기에 너무 긴 값들을 body에 싣고 넘기는 방법
    @PostMapping
    public AccountResponse createAccount(@RequestBody AccountCreateRequest request) {
        Account account = accountService.createAccount(request);
        return AccountResponse.from(account); // dto 적용
    }
}