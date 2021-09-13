package com.btch.MyFirstBatch.configuration;

import com.btch.MyFirstBatch.entities.BankTransaction;
import org.springframework.batch.item.ItemProcessor;

public class BankTransactionItemAnaliticsProcessor implements ItemProcessor<BankTransaction, BankTransaction> {

   private double totalDebit;
   private double totalCredit;
    @Override
    public BankTransaction process(BankTransaction item) throws Exception {
        if(item.getTransactionType().equals("D")){
            totalDebit+=item.getAmount();
        }else if(item.getTransactionType().equals("C")){
            totalCredit+=item.getAmount();
        }
        return item;
    }

    public double getTotalDebit() {
        return totalDebit;
    }

    public double getTotalCredit() {
        return totalCredit;
    }
}
