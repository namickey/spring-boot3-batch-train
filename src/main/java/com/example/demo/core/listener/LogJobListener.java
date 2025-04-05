package com.example.demo.core.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.StepExecution;
import org.springframework.stereotype.Component;

@Component
public class LogJobListener implements JobExecutionListener {

    private final Logger logger = LoggerFactory.getLogger(LogJobListener.class);

    @Override
    public void afterJob(@SuppressWarnings("null") JobExecution jobExecution) {
        int inputCount = 0;
        int skipCount = 0;

        for (StepExecution stepExecution : jobExecution.getStepExecutions()) {
            inputCount += stepExecution.getReadCount() + stepExecution.getReadSkipCount();
            skipCount += stepExecution.getSkipCount();
        }

        logger.info("AfterJob:input={}, skip={}", inputCount, skipCount);
    }
}
