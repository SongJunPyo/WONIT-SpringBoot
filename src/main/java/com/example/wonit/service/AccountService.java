// 실제 업무 담당(비즈니스 로직) -> 가장 긴 코드 작성

package com.example.wonit.service;

import com.example.wonit.exception.*;
import com.example.wonit.model.Account;
import com.example.wonit.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Controller와만 통신하며 요청에 대한 실제 로직을 담당
@Service
public class AccountService {

    @Autowired // 서로서로 연결된 bean임을 알려줍니다. 생략하면 Springboot가 넣어줌
    private AccountRepository accountRepository;

    // 모든 계좌를 리턴
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    // 하나의 계좌 정보를 repo에서 받아서 리턴하거나 NPE을 막아주는 함수
    public Optional<Account> findOne(String accountNo) {
        return accountRepository.findByAccountNo(accountNo);
    }

    // 하나의 계좌 정보를 리턴 -> 받아서 Account 자료형으로 받거나 예외를 일으키는 함수
    public Account getAccount(String accountNo) {
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
        return accountRepository.findByBalanceBetween(minBalance, maxBalance); // JPA의 규칙대로 작성한 메서드가 Hibernate에 의해 쿼리로 변환되어서 JDBC로 DB에 다녀옵니다.
    }
}
