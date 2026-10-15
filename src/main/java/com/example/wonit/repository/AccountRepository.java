// JPA의 규칙대로 우리가 메서드를 설계해두면 Hibernate라는 통역기가 JDBC를 통해 db에 맞는 SQL문으로 변경해서 데이터를 가져옵니다.

package com.example.wonit.repository;

import com.example.wonit.dto.AccountResponse;
import com.example.wonit.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// JPA의 규칙대로 우리가 메서드를 설계해두면 Hibernate라는 통역기가 db에 맞는 SQL문으로 변경해서 데이터를 가져옵니다.
// interface 여도 괜찮은 이유: JPA를 Hibernate라는 구현체가 실제로는 구현해주기 때문
@Repository                                       // <엔티티명, 엔티티의 pk 컬럼의 자료형>
public interface AccountRepository extends JpaRepository<Account, Integer> { // Integer: PK
    Optional<Account> findByAccountNo(String accountNo);

    List<Account> findByBalanceBetween(long minBalance, long maxBalance);

    // PK를 사용한 단건 조회 또는 모든 건 조회가 아닌 경우 작성만 해주면 됨
    List<Account> findAllByOrderByBalanceAsc();

    List<Account> findByStatusContains(String status);

    List<Account> findByAccountTypeLike(String accountType);

    boolean existsByAccountNo(String accountNo);

    AccountResponse deleteByAccountNo(String accountNo);
}
