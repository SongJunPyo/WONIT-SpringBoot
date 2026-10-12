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

    // 단일 Entity(Model)의 단점
    // -> 불필요한 값들(예: customerId, customerId 등)도 포함됨
    // => DTO(Data Transfer Object)라는 별도의 클래스를 만들어서 필요한 값만 전달하는 방식이 있음


    // @Setter 대신에 우리가 만든 별도의 status가 지급정지는 아닌지, 잔액이 부족하진 않는지 점검하고 처리

}
