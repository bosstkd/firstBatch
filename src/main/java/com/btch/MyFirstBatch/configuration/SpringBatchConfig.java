package com.btch.MyFirstBatch.configuration;

import com.btch.MyFirstBatch.entities.BankTransaction;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private ItemReader<BankTransaction> bankTransactionItemReader;
    @Autowired
    private ItemWriter<BankTransaction> bankTransactionItemWriter;
    // ItemProcessor<InputType, OutputType>  in this example the input and the output are same type
    @Autowired
    private ItemProcessor<BankTransaction, BankTransaction> bankTransactionItemProcessor;

    // Methode de configuration du job
    @Bean
    public Job bankJob() {
        // on peux spécifier le nom du step comme on veux ici j'ai choisi step-load-data
        Step step1 = stepBuilderFactory.get("step-load-data")
                // si on ne spécifie pas le type input output sur le chunk on aura une erreur sur le processor
                .<BankTransaction, BankTransaction>chunk(100)
                .reader(bankTransactionItemReader)
                .processor(bankTransactionItemProcessor)
                .writer(bankTransactionItemWriter)
                .build();
        // on peux spécifier le nom du job comme on veux ici j'ai choisi bank-data-loader-job
        return jobBuilderFactory.get("bank-data-loader-job")
                .start(step1).build();
    }


//************************Configuration pour que le ItemReader fonctionne********************************
    // pour que cette configuration fonctionne il faut définir le itemReader, itemProcessor et itemWriter
    // on peut définir plusieurs type de itemReader ici FlatFile car on a un fichier plat (.csv)


    @Bean
    public FlatFileItemReader<BankTransaction> fileItemReader(@Value("${chemin.fichier}") Resource resource){
        FlatFileItemReader<BankTransaction> flatFileItemReader = new FlatFileItemReader<>();
        // Ajouter un nom
        flatFileItemReader.setName("FFIR1");
        // Premiere ligne a eviter car elle contient l'entete
        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setResource(resource);
        flatFileItemReader.setLineMapper(lineMapper());
        return flatFileItemReader;
    }


    // Mapper de notre fichier plat data.csv
    @Bean
    public LineMapper<BankTransaction> lineMapper(){
        DefaultLineMapper<BankTransaction> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        // les noms il faut que ça soit les memes que sur notre BankTransaction.java
        lineTokenizer.setNames("id","accountId","strTransactionDate","transactionType","amount");
        lineMapper.setLineTokenizer(lineTokenizer);
        BeanWrapperFieldSetMapper<BankTransaction> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        // les fieldSetMapper doit etre la class BankTransaction.class pour que les names soit mapper
        fieldSetMapper.setTargetType(BankTransaction.class);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }

}
