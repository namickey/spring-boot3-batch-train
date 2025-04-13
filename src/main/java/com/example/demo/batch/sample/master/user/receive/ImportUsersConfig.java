package com.example.demo.batch.sample.master.user.receive;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.demo.common.entity.Users;
import com.example.demo.common.mapper.UsersMapper;
import com.example.demo.core.exception.CustomSkipPolicy;
import com.example.demo.core.listener.LogChunkListener;
import com.example.demo.core.listener.LogJobListener;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ImportUsersConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final LogChunkListener logChunkListener;
    private final LogJobListener logJobListener;

    private final ImportUsersProcessor userImportProcessor;
    private final ImportUsersWriter userImportWriter;
    private final UsersMapper usersMapper;
    
    @Bean
    public FlatFileItemReader<ImportUsersItem> reader() {
        
        FlatFileItemReader<ImportUsersItem> reader = new FlatFileItemReader<>();

        // ヘッダー行をスキップ
        reader.setLinesToSkip(1);
        reader.setResource(new FileSystemResource("input-data/user.csv"));
        reader.setLineMapper(new DefaultLineMapper<ImportUsersItem>() {
            {
                setLineTokenizer(new DelimitedLineTokenizer() {
                    {
                        setNames("id", "name", "department", "createdAt");
                    }
                });
                setFieldSetMapper(new BeanWrapperFieldSetMapper<ImportUsersItem>() {
                    {
                        setTargetType(ImportUsersItem.class);
                    }
                });
            }
        });
        return reader;
    };

    @Bean
    public Tasklet trancateTasklet() {
        return (contribution, chunkContext) -> {
            usersMapper.truncate();
            return null;
        };
    }

    /**
     * ジョブ
     */
    @Bean
    public Job importUsersJob() {
        return new JobBuilder("importUsersJob", jobRepository)
                .start(importUsersStep1())
                .next(importUsersStep2())
                .listener(logJobListener) // ログ出力（ジョブ単位）
                .build();
    }

    /**
     * ステップ１
     */
    @Bean
    public Step importUsersStep1() {
        return new StepBuilder("importUsersStep1", jobRepository)
                .tasklet(trancateTasklet(), platformTransactionManager)
                .allowStartIfComplete(true)
                .build();
    }

    /**
     * ステップ２
     */
    @Bean
    public Step importUsersStep2() {
        return new StepBuilder("importUsersStep2", jobRepository)
                .<ImportUsersItem, Users>chunk(10, platformTransactionManager)
                .reader(reader())
                .processor(userImportProcessor)
                .writer(userImportWriter)
                .allowStartIfComplete(true) // true:何度でも再実行可能。false:一度だけ実行可能。
                .faultTolerant()  // 耐障害性設定。例外等が発生しても処理を継続する。
                .skipPolicy(new CustomSkipPolicy()) // カスタムスキップポリシーを設定。特定例外はスキップする。
                .listener(logChunkListener) // ログ出力（チャンク単位）
                .build();
    }
}
