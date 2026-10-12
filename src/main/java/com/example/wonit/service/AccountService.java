package com.example.wonit.service;

import com.example.wonit.model.Account;
import com.example.wonit.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// Controller와만 통신하며, 요청에 대한 실제 로직을 담당
@Service
public class AccountService {

    // Service는 Repository와만 통신하며, 요청에 대한 실제 로직을 담당
    @Autowired      // 서로서로 연결된 bean임을 알려줌. 생략하면 Springboot가 넣어줌
    private AccountRepository accountRepository;

    // 모든 계좌를 return(Repository로)
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    // 하나의 계좌 정보를 return
    public Account findOne(String accountNo) {
        return accountRepository.findByAccountNo(accountNo);
    }
}
