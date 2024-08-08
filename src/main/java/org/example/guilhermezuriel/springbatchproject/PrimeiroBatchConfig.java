package org.example.guilhermezuriel.springbatchproject;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.function.FunctionItemProcessor;
import org.springframework.batch.item.support.IteratorItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class PrimeiroBatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    @Bean
    public Job imprimeParJob(){
        return new JobBuilder("imprimirParJob", jobRepository).start(imprimeParStep()).build();
    }

    public Step imprimeParStep(){
        return new StepBuilder("imprimirParStep", jobRepository)
                .<Integer, String>chunk(1, transactionManager)
                .reader(contaAteDezReader())
                .processor(parOuImparProcessor())
                .writer(imprimeWriter())
                .build();
    }

    public IteratorItemReader<Integer> contaAteDezReader(){
        List<Integer> numerosDeUmADez = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
        return new IteratorItemReader<>(numerosDeUmADez.iterator());
    }

    public FunctionItemProcessor<Integer, String> parOuImparProcessor(){
        return new FunctionItemProcessor<>(item -> item % 2 == 0 ? String.format("Item é par: %d", item): String.format("Item é impar: %d", item));
    }

    public ItemWriter<String> imprimeWriter(){
        return itens -> itens.forEach(System.out::println);
    }

}


