package com.example.springbatch.job;

import com.example.springbatch.entity.Pay;
import com.example.springbatch.repository.PayRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class _5_ItemReader {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final PayRepository payRepository;

    private static final int chunkSize = 10;

    @Bean
    public Job jpaItemReaderJob() {
        return jobBuilderFactory.get("jpaItemReaderJob")
                .start(jpaItemReaderStep())
                .build();
    }

    @Bean
    public Step jpaItemReaderStep() {
        return stepBuilderFactory.get("jpaItemReaderStep")
                .<Pay,Pay>chunk(chunkSize)
                .reader(jpaItemReader())
                .writer(jpaItemWriter())
                .build();
    }

    @Bean
    public ListItemReader<Pay> jpaItemReader() {
        List<Pay> all = payRepository.findAll();

        return new ListItemReader<>(all);

    }

    private ItemWriter<Pay> jpaItemWriter() {
        return list -> {
            for (Pay pay: list) {
                log.info("Current Pay = {}",pay);
            }
        };
    }

}
