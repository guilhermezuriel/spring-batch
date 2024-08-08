package org.example.guilhermezuriel.springbatchproject;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@RequiredArgsConstructor
@Configuration
public class BatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    @Bean
    Job imprimeOla(){
        return new JobBuilder("imprimeOla", jobRepository).start(imprimeOlaStep()).build();
    }

    Step imprimeOlaStep(){
        return new StepBuilder("imprimeOla", jobRepository).tasklet((StepContribution contribution, ChunkContext chunkContext) -> {
                System.out.println("Ola Mundo");
                return RepeatStatus.FINISHED;
            }, transactionManager).build();
    }
}
