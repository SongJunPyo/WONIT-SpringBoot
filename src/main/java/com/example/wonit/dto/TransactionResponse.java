package com.example.wonit.dto;

import com.example.wonit.model.Account;
import com.example.wonit.model.Transaction;

import java.time.LocalDateTime;

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