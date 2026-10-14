package com.example.wonit.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

// @Data // @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequriedArgsConstructor
// 보안이 중요한 데이터 처리에는 setter를 직접 만들어서 사용(@Setter 안 씀 !)
@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor

@Entity
public class Account {

    @Id // Primary Key(고유값을 의미하는 컬럼)을 지정해줘야 이 컬럼을 기반으로 쿼리들이 만들어집니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer accountId;

    private Integer customerId;
    private String accountNo;
    private String accountType;
    private String status;
    private long balance;

    // 단일 Entity(Model)의 단점
    // -> 불필요한 값들(예: customerId 등)도 포함됨
    // => DTO(Data Transfer Object)라는 별도의 클래스를 만들어서 필요한 값만 전달하는 방식이 있음

    // @Setter 대신에 우리가 만든 별도의 status가 지급정지는 아닌지, 잔액이 불충분하지는 않은지 확인하는 예외를 만들어서 처리
    // 10000원 넣으면서 통장 만들어주세요 -> 0원 계좌 만들고 10000원 입금 메서드로 추가
        // - 보안 감사 때문에 -> 그래서 @Setter 안 씀

    // 계좌 생성 메서드
    public static Account open(Integer customerId, String accountNo, String accountType) {
        Account account = new Account();
        account.customerId = customerId;
        account.accountNo = accountNo;
        account.accountType = accountType;
        account.status = "정상";
        account.balance = 0;
        return account;
    }
}
