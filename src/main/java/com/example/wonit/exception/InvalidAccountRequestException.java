// exception/InvalidAccountRequestException.java

// 없는 계좌에 대한 요청을 처리하는 예외

package com.example.wonit.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidAccountRequestException extends RuntimeException {

    public InvalidAccountRequestException(String message) {
        super(message);
    }
}