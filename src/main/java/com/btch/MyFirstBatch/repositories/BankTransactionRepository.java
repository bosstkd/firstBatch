package com.btch.MyFirstBatch.repositories;

import com.btch.MyFirstBatch.entities.BankTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankTransactionRepository extends JpaRepository<BankTransaction, Long> {
}
