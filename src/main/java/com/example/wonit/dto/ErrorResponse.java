package com.example.wonit.dto;


// 클래스 대신 final로 값을 전ㄴ달만 하는 data를 위한 빠르게 만드는 클래스
// { code: "404", message: "계좌를 찾을 수 없습니다." } 형식으로 지정
public record ErrorResponse(String code, String message) {
}
