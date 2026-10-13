package com.example.wonit.repository;

import com.example.wonit.dto.TransactionResponse;
import com.example.wonit.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllTransactionByCategory(String category);

    List<Transaction> findAllTransactionByCategoryOrMemo(String category, String memo);

    List<Transaction> findByAmountBetween(long minAmount, long maxAmount);
}
