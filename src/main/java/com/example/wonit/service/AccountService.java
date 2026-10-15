/**
 * JavaDoc
 */

// 실제 업무 담당(비즈니스 로직) -> 가장 긴 코드 작성

package com.example.wonit.service;

import com.example.wonit.dto.AccountCreateRequest;
import com.example.wonit.dto.AccountResponse;
import com.example.wonit.exception.*;
import com.example.wonit.model.Account;
import com.example.wonit.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Controller와만 통신하며 요청에 대한 실제 로직을 담당
@Slf4j
@Service
@RequiredArgsConstructor    // accountRepository의 final 지정
public class AccountService {

    @Autowired // 서로서로 연결된 bean임을 알려줍니다. 생략하면 Springboot가 넣어줌
    private final AccountRepository accountRepository;

    // 모든 계좌를 리턴
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    // 하나의 계좌 정보를 repo에서 받아서 리턴하거나 NPE을 막아주는 함수
    public Optional<Account> findOne(String accountNo) {
        return accountRepository.findByAccountNo(accountNo);
    }

    /**
     *
     * @param accountNo 찾을 계좌번호
     * @return Account 찾은 계좌
     */
    // 하나의 계좌 정보를 리턴 -> 받아서 Account 자료형으로 받거나 예외를 일으키는 함수
    public Account getAccount(String accountNo) {
        log.info("계좌 조회={}", accountNo);
        return findOne(accountNo).orElseThrow(() -> new AccountNotFoundException(accountNo + "계좌를 찾을 수 없습니다"));
    }

    // db에서 모든 계좌를 조회해서 '정상' 상태인 계좌번호만 리턴
    public List<String> normalAccountNos() {
        return accountRepository.findAll()
                .stream().filter(account -> account.getStatus().equals("정상"))
                .map(Account::getAccountNo).toList();
    }

    public long savingBalanceSum() {
        return accountRepository.findAll()
                .stream().filter(account -> account.getAccountType().equals("적금"))
                .mapToLong(Account::getBalance).sum();
    }

    public List<Account> getAccountBetweenBalance(long minBalance, long maxBalance) {
        if(minBalance > maxBalance) {
            log.warn("잘못된 잔액 범위: minBalance={}, maxBalance={}", minBalance, maxBalance);
            throw new InvalidAccountRequestException("minBalance는 maxBalance보다 클 수 없습니다.");
        }
        
        log.debug("잔액 범위로 계좌 조회: minBalance={}, maxBalance={}", minBalance, maxBalance);
        
        return accountRepository.findByBalanceBetween(minBalance, maxBalance); // JPA의 규칙대로 작성한 메서드가 Hibernate에 의해 쿼리로 변환되어서 JDBC로 DB에 다녀옵니다.
    }

    // 여기서 자료형이 Account인 이유 -> Service 계층은 Repository로 부터 Entity를 그대로 받음
    public List<Account> findAllAccountsByBalanceASC() {
        return accountRepository.findAllByOrderByBalanceAsc();
    }

    public List<Account> findAllAccountsByStatus(String status) {
        return accountRepository.findByStatusContains(status);
    }

    public List<Account> findAllAccountsByAccountType(String accountType) {
        return accountRepository.findByAccountTypeLike(accountType);
    }

    public Account createAccount(AccountCreateRequest request) {
        if (request.customerId() == null || request.customerId() <= 0) {
            throw new InvalidAccountRequestException("올바른 customerId가 필요합니다.");
        }

        if (!List.of("입출금", "적금").contains(request.accountType())) {
            throw new InvalidAccountRequestException("지원하지 않는 accountType입니다.");
        }

        String accountNo;
        do {
            accountNo = java.util.UUID.randomUUID()
                    .toString()
                    .replace("-", "")
                    .substring(0, 20);
        } while (accountRepository.existsByAccountNo(accountNo));

        return accountRepository.save(
                Account.open(request.customerId(), accountNo, request.accountType())
        );
    }

    public Account updateOne(String accountNo, AccountCreateRequest request) {
        // < 순서 >

        // 1. 원본(기존 데이터) 가져오기
        Account account = getAccount(accountNo);

        // 2. 화면에 뿌리기(통째로 바꾸는 경우에는 생략 가능 But, 일부만 가져올 경우에는 원본의 내용과 일치하는 내용인지 확인해야 됨)
            // 업무 메서드를 하나 Entity에 작성해서 변경
            // 왜? @Setter 없으니, account.setCustomId(request.customId()); 등으로 변경 불가

        // 3. 바뀐 내용 새로 객체로 저장하기
        // 4. DB에 반영하기(Repository)
        Account saved = accountRepository.save(account);

        // 5. 반영된 상태 확인
        return account;
    }

    public AccountResponse deleteOne(String accountNo) {
        // CRUD는 JPA의 규칙대로 메서드를 만들어서 repository에 넘겨주기만 하면 됨
        // 만약 PK라면 JPA에 메서드를 만들어놓지 않아도 삭제 가능 (accountNo는 PK아니니까 만들어야 됨)
        return accountRepository.deleteByAccountNo(accountNo);
    }
}

// 2. 필드 변경(= 2. 화면에 뿌리기 + 3. 바뀐 내용 새로 객체로 저장하기)
    // PUT(POST) : 모두 가져와서 모두 다시 저장
    // PATCH : 모두 가져와서 수정된 값만 저장(수정할 필드 일일이 비교해야 되는 번거로움 있음)
