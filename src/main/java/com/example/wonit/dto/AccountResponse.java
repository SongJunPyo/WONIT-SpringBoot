package com.example.wonit.dto;

// Model(Entity)에서 불필요한 정보를 제외하고 필요한 정보만 담아서 전달 -> Controller와 Service 사이에서만 데이터를 전달하는 용도로 사용
// 금융 특성상 보안이 중요하기 떄문에 알아둬야 함 !
// Controller와 Service 사이에서만 데이터를 전달하는 용도로 사용

// 데이터를 final로(중간에 변경하지 않는 대신 편하게 실어나르는 자바의 새 문법)
public record AccountResponse(String accountNo, String accountType, long balance, String status) {

    public static AccountResponse fromEntity(com.example.wonit.model.Account account) {
        return new AccountResponse(
                account.getAccountNo(),
                account.getAccountType(),
                account.getBalance(),
                account.getStatus()
        );
    }
}
