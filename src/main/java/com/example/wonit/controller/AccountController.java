package com.example.wonit.controller;

import com.example.wonit.dto.AccountResponse;
import com.example.wonit.model.Account;
import com.example.wonit.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController     // Spring이 "아 라우팅 클래스구나" 하고 관리함
@RequestMapping("/api/accounts")   // 이 컨트롤러의 기본 URL 경로를 설정
public class AccountController { // 역할: 주소 받고 & 아래로 전달

    // 어차피 스프링 컨테이너가 관리하는 bean이므로, private으로 막아둠
    @Autowired      // 서로서로 연결된 bean임을 알려줌. 생략하면 Springboot가 넣어줌
    private AccountService accountService;;

    @GetMapping     // 모든 accounts 조회
    // jpa의 규칙은 Repository에서만 영향을 미침
    public List<AccountResponse> findAll() {

        // 이 값을 service에게 전달
//        return accountService.findAll();

        // Service에서 받은 값을 Controller에서 가공해서 return
        return accountService.findAll().stream()
                .map(AccountResponse::fromEntity)
                .toList();
    }

    // 특정 계좌번호의 세부 정보 조회
    @GetMapping("/{accountNo}")
    public AccountResponse findOne(@PathVariable("accountNo") String accountNo) {
        return AccountResponse.fromEntity(accountService.findOne(accountNo).orElseThrow(() -> new AccountNotFoundException(accountNo + " 계좌를 찾을 수 없습니다.")));
    }

    // 계좌 전체를 조회해서 그중에 '정상' 계좌만 출력

    // 전체 계좌중에 account_type이 '적금'인 경우만 금액을 모두 더해서 리턴

}
