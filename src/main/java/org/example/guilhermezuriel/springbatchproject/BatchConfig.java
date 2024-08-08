package org.example.guilhermezuriel.springbatchproject;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
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
    public Job imprimeOla(){
        return new JobBuilder("imprimeOla", jobRepository).start(imprimeOlaStep()).incrementer(new RunIdIncrementer()).build();
    }

    public Step imprimeOlaStep(){
        return new StepBuilder("imprimeOla", jobRepository).tasklet(imprimeOlaTasklet("Guilherme"), transactionManager).build();
    }


    public Tasklet imprimeOlaTasklet(String nome){
        return new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                    System.out.printf("Ola %s", nome);
                    return RepeatStatus.FINISHED;
            }
        };
    }
}
