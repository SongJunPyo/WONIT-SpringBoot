package com.example.wonit.service;

import org.springframework.stereotype.Service;

// Controller와만 통신하며, 요청에 대한 실제 로직을 담당
@Service
public class AccountService {

    // 모든 계좌를 return(Repository로)
    public List<Account> findAll() {
        return AccountRepository.findAll();
    }

}
