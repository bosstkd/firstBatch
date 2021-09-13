package com.btch.MyFirstBatch.configuration;

import com.btch.MyFirstBatch.entities.BankTransaction;
import com.btch.MyFirstBatch.repositories.BankTransactionRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BankTransactionItemWriter implements ItemWriter<BankTransaction> {

    @Autowired
    private BankTransactionRepository bankTransactionRepo;

    @Override
    public void write(List<? extends BankTransaction> items) throws Exception {
        bankTransactionRepo.saveAll(items);
    }

}
