package com.btch.MyFirstBatch.controller;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class BankRestController {
    private JobLauncher jobLauncher;
    private Job job;
    // injection de dépendance par constructeur
    public BankRestController(JobLauncher jobLauncher, Job job) {
        this.job = job;
        this.jobLauncher = jobLauncher;
    }

    @GetMapping("/startJob")
    public BatchStatus load() throws Exception{
        Map<String, JobParameter> parameters = new HashMap<>();
        parameters.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters = new JobParameters(parameters);
        JobExecution jobExecution=jobLauncher.run(job, jobParameters);
        while(jobExecution.isRunning()) {
            System.out.println(".....");
        }
        return jobExecution.getStatus();
    }
}
