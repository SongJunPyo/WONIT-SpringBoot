package com.example.wonit.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Data
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tx_id;

    private Integer account_id;
    private Long transfer_id;
    private String tx_type;
    private Long amount;
    private Long balance_after;
    private String category;
    private String memo;
    private String counterparty;
    private LocalDateTime tx_datetime;
}