package com.example.wonit.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;


// 보안이 중요한 데이터를 실어나르는 경우는 @Setter를 사용하지 않음
@Data
@Entity
// @Table(name = "`transaction`" -> DB의 테이블과 우리가 자바에서 사용하는 클래스명이 다를 경우 호환해서 사용
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