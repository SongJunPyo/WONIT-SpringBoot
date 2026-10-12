package com.example.wonit.repository;

import com.example.wonit.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// JPA의 규칙대로 우리가 메서드를 설계해두면 Hibernate라는 동역기가 db에 맞는 SQL문으로 변경해서 데이터를 가져옴
@Repository                                         // <엔티티명, 엔티티의 pk컬럼
// interface여도 괜찮은 이유 -> JPA를 Hibernate라는 구현체가 실제로는 구현해주기 때문
public interface AccountRepository extends JpaRepository<Account, Integer> {
}
