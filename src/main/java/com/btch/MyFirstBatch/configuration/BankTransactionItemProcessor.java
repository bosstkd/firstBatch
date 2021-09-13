package com.btch.MyFirstBatch.configuration;

import com.btch.MyFirstBatch.entities.BankTransaction;
import org.springframework.batch.item.ItemProcessor;

import java.text.SimpleDateFormat;

//Configuration par un component dans le contexte Spring pour que le ItemProcessor fonctionne
public class BankTransactionItemProcessor implements ItemProcessor<BankTransaction, BankTransaction> {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy-HH:mm");

    @Override
    public BankTransaction process(BankTransaction item) throws Exception {
        // Le seul traitement qu'on va faire sur notre exemple c'est de formater la date pour la sauvegarder sous forme Date
        item.setTransactionDate(dateFormat.parse(item.getStrTransactionDate()));
        return item;
    }

}

