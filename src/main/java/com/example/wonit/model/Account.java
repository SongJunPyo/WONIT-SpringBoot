package com.example.wonit.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Account {

    @Id     // pk 컬럼 -> 이 컬럼 기반으로 쿼리 만들어짐
    private Integer accountId;

    private Integer customerId;
    private String accountNo;
    private String accountType;
    private Long balance;
    private String status;

}
