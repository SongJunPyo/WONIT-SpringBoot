package com.example.wonit.controller;

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
    public List<Account> findAll() {

        // 이 값을 service에게 전달
        return accountService.findAll();
    }

    // 특정 계좌번호의 세부 정보 조회
    @GetMapping("/{accountNo}")
    public Account findOne(@PathVariable("accountNo") String accountNo) {
        return accountService.findOne(accountNo);
    }


}
