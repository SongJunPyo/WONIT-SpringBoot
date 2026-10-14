package com.example.wonit.controller;

import com.example.wonit.dto.AccountResponse;
import com.example.wonit.dto.TransactionResponse;
import com.example.wonit.model.Account;
import com.example.wonit.model.Transaction;
import com.example.wonit.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions") // localhost:8080/api/accounts
@RequiredArgsConstructor
public class TransactionController {

    @Autowired
    private final TransactionService transactionService;

//    @GetMapping("/select")
//    public List<TransactionResponse> getTransactionByCategory(@RequestParam(value = "category") String category) {
//        return transactionService.findAllTransactionByCategory(category).stream().map(TransactionResponse::from).toList();
//        // transactions/select?category=식비
//    }
//
//    @GetMapping("/select")
//    public List<TransactionResponse> getTransactionByCategoryOrMemo(@RequestParam(value = "category") String category, @RequestParam(value = "memo") String memo){
//        return transactionService.findAllTransactionByCategoryOrMemo(category, memo).stream().map(TransactionResponse::from).toList();
//    }

    @GetMapping("/select")
    public List<TransactionResponse> getTransactionByCategoryOrMemo(
            @RequestParam("category") String category,
            @RequestParam(value = "memo", defaultValue = "") String memo) {

        List<Transaction> transactions = memo.isBlank()
                ? transactionService.findAllTransactionByCategory(category)
                : transactionService.findAllTransactionByCategoryOrMemo(category, memo);

        return transactions.stream()
                .map(TransactionResponse::from)
                .toList();
    }

    /*
    @GetMapping("/select")
    public List<TransactionResponse> select(
        @RequestParam(required = false) String category,
        @RequestParam(required = false) String memo
    ) {
        boolean hasCategory = StringUtils.hasText(category);
        boolean hasMemo = StringUtils.hasText(memo);

        if (!hasCategory && !hasMemo) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "category 또는 memo를 입력해야 합니다."
            );
        }

        List<?> results = hasMemo
                ? transactionService.getTransactionByCategoryOrMemo(
                hasCategory ? category.trim() : null, memo.trim())
                : transactionService.getTransactionByCategory(category.trim());

        return results.stream()
                .map(Transaction.class::cast)
                .map(TransactionResponse::from)
                .toList();
    }
     */

    @GetMapping("/select2")
    public List<TransactionResponse> getTransactionByAmount(@RequestParam long minAmount, @RequestParam long maxAmount) {
        return transactionService.findAllTransactionByAmount(minAmount, maxAmount).stream().map(TransactionResponse::from).toList();
    }

}

/*
    # 1. h2 db로 되돌린다
    # 2. controller/service/dto/repository/entity에 transaction어쩌구 class를 만들고 어노테이션을 붙인다
    # 3. 위의 요구사항을 AI한테 전달하고 API 설계를 작성한다 (변수명 등은 고정)
    # 4. 구현
    # 5. h2 db에 샘플 코드 insert 한다. (data.sql에서 transaction 테이블에 insert 하는 구문 주석 풀기)
    # 6. Swagger나 Boomerang으로 테스팅 확인한다.
    # 7. AI에게 REST API의 규칙을 따르는지, 쿼리는 효율적인지, 불필요한 코드는 없는지 등등 점검 요청
 */