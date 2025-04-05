package com.example.demo.core.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.autoconfigure.batch.JobExecutionEvent;
import org.springframework.context.ApplicationListener;

public class BatchExitCodeGenerator implements ExitCodeGenerator, ApplicationListener<JobExecutionEvent> {

    private final List<JobExecution> executions = new ArrayList<>();

    @Override
    public void onApplicationEvent(@SuppressWarnings("null") JobExecutionEvent event) {
        executions.add(event.getJobExecution());
    }
    
    @Override
    public int getExitCode() {
        for (JobExecution execution : executions) {
            for (StepExecution stepExecution : execution.getStepExecutions()) {
                if (!stepExecution.getFailureExceptions().isEmpty()) {
                    return 1;
                }
            }
        }
        return 0;
    }
}
