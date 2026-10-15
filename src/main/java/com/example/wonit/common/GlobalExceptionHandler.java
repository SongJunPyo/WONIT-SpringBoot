package com.example.wonit.common;

import com.example.wonit.dto.ErrorResponse;
import com.example.wonit.exception.AccountFrozenException;
import com.example.wonit.exception.InvalidAccountRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice       // "RestController에서 참조해야 하는 클래스입니다~"
// 발생한 Exception을 최초로 관리/등록하는 Handler
// 여기서 구체적인 Exception들을 호출함 -> 왜? 예외가 발생한 곳에 대해서만 처리 가능(아니라면 각 예외에 대해 일일이 작성해야 됨)
public class GlobalExceptionHandler {

    // 손님 잘못 → WARN 한 줄. 스택트레이스까지 찍으면 로그가 쓰레기로 가득 찬다
    @ExceptionHandler(AccountFrozenException.class)
    public ResponseEntity<ErrorResponse> handleAccountFrozen(AccountFrozenException e) {
        log.warn("지급정지 계좌 출금 시도: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse("ACCOUNT_FROZEN", e.getMessage()));
    }

    @ExceptionHandler(InvalidAccountRequestException.class)
    public ResponseEntity<ErrorResponse> handleInvalidAccount(InvalidAccountRequestException e) {
        log.warn("존재하지 않는 사용자입니다.: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse("INVALID_ACCOUNT", e.getMessage()));
    }

    // 맨 마지막 그물. 🚨 e.getMessage() 를 그대로 내보내면 SQL·클래스명이 손님 화면에 찍힌다
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnknown(Exception e) {
        log.error("처리하지 못한 예외", e);   // ← e 를 {} 없이 마지막에 넘기면 스택트레이스 전체가 찍힌다
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("INTERNAL_ERROR", "잠시 후 다시 시도해 주세요."));
    }
}