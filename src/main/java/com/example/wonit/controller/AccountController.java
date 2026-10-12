package com.example.wonit.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController     // Spring이 "아 라우팅 클래스구나" 하고 관리함
@RequestMapping("/api/accounts")   // 이 컨트롤러의 기본 URL 경로를 설정
public class AccountController {

    @GetMapping


    // 특정 계좌번호의 세부 정보 조회
    @GetMapping("/{accountNo}")

}
