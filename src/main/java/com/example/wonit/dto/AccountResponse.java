package com.example.wonit.dto;

// Model(Entity)에서 불필요한 정보를 제외하고 필요한 정보만 담아서 전달 -> Controller와 Service 사이에서만 데이터를 전달하는 용도로 사용
// 금융 특성상 보안이 중요하기 때문에 알아둬야 함. accountId는 내부용 번호라서 밖으로 내보내지 않습니다

import com.example.wonit.model.Account;

// 데이터를 final로(중간에 변경하지 않는 대신 편하게 실어나르는 자바의 새 문법)
public record AccountResponse(String accountNo, String accountType, long balance, String status) {

    public static AccountResponse from(Account account) {
        return new AccountResponse(
                // 이것들만 끄집어 내서 AccountResponse형으로 활용
                account.getAccountNo(),
                account.getAccountType(),
                account.getBalance(),
                account.getStatus()
        );
    }
}
