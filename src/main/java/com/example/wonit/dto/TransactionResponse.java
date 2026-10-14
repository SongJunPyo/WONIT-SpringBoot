package com.example.wonit.dto;

import com.example.wonit.model.Account;
import com.example.wonit.model.Transaction;

import java.time.LocalDateTime;

// class로 했을 때, 만약 누군가 DTO 객체에 직접 접근해서 변수를 가로채면 보안상 큰 위험이 됨
public record TransactionResponse(String tx_type, Long amount, Long balance_after, String category,
                                  String memo, String counterparty, LocalDateTime tx_datetime) {

    public static TransactionResponse from(Transaction transaction) {
        return new TransactionResponse(

                // 이것들만 끄집어 내서 TransactionResponse형으로 활용
                transaction.getTx_type(),
                transaction.getAmount(),
                transaction.getBalance_after(),
                transaction.getCategory(),
                transaction.getMemo(),
                transaction.getCounterparty(),
                transaction.getTx_datetime()
        );
    }
}