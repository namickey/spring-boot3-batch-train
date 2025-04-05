package com.example.demo.core.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.stereotype.Component;

/**
 * ログ出力する
 */
@Component
public class LogChunkListener implements ChunkListener {

    private final Logger logger = LoggerFactory.getLogger(LogChunkListener.class);

    @Override
    public void afterChunk(@SuppressWarnings("null") ChunkContext context) {
        StepExecution stepExecution = context.getStepContext().getStepExecution();
        long inputCount = stepExecution.getReadCount() + stepExecution.getReadSkipCount();
        long skipCount = stepExecution.getSkipCount();
        long commitCount = stepExecution.getCommitCount();
        logger.info("AfterChunk:input={}, skip={}, commit={}", inputCount, skipCount, commitCount);
    }
}
