package com.example.wonit.service;

import com.example.wonit.dto.TransactionResponse;
import com.example.wonit.model.Transaction;
import com.example.wonit.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    @Autowired
    private final TransactionRepository transactionRepository;

    public List<Transaction> findAllTransactionByCategory(String category) {
        return transactionRepository.findAllTransactionByCategory(category);
    }

    public List<Transaction> findAllTransactionByCategoryOrMemo(String category, String memo) {
        return transactionRepository.findAllTransactionByCategoryOrMemo(category, memo);
    }

    public List<Transaction> findAllTransactionByAmount(long minAmount, long maxAmount) {
        return transactionRepository.findByAmountBetween(minAmount, maxAmount);
    }
}
