package com.example.springbatch.job;

import com.example.springbatch.entity.Pay;
import com.example.springbatch.repository.PayRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class _6_ItemWriter {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final PayRepository payRepository;

    private static final int chunkSize = 10;

    @Bean
    public Job jopItemWriterJob() {
        return jobBuilderFactory.get("jpaItemWriterJob")
                .start(jpaItemWriterStep())
                .build();
    }

    @Bean
    public Step jpaItemWriterStep() {
        return stepBuilderFactory.get("jpaItemWriterStep")
                .<Pay,Pay> chunk(chunkSize)
                .reader(customItemWriterReader())
                .processor(customItemWriterProcessor())
                .writer(customItemWriter())
                .build();
    }

    @Bean
    public ListItemReader<Pay> customItemWriterReader() {
        List<Pay> all = payRepository.findAll();

        return new ListItemReader<>(all);
    }

    @Bean
    public ItemProcessor<Pay,Pay> customItemWriterProcessor() {
        return pay -> new Pay(pay.getId(),pay.getAmount(),pay.getTxName(),pay.getLocalDateTime());
    }

    @Bean
    public ItemWriter<Pay> customItemWriter() {
        log.info("ddddddddddd");
        return items -> {
            for (Pay item : items) {
                System.out.println(item);
            }
        };
    }
}
