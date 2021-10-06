package com.example.springbatch.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Slf4j
@Configuration
@RequiredArgsConstructor
public class _3_StepNextConditionalJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job stepNextConditionalJob() {
        return jobBuilderFactory.get("stepNextConditionalJob")
                .start(conditionalJobStep1())
                    .on("FAILED") // FAILED일 경우
                    .to(conditionalJobStep3()) // step3으로 이동
                    .on("*") // step 3의 결과 관계 없이
                    .end() // step3으로 이동하면 Flow가 종료
                .from(conditionalJobStep1()) // step1로부터
                    .on("*") // FAILED 외에 모든 경우
                    .to(conditionalJobStep2()) // step2로 이동
                    .next(conditionalJobStep3()) // step2가 정상종료되면 3으로 이동
                    .on("*") // step3의 결과 관계 없이
                    .end() // step3으로 이동하면 Flow가 종료
                .end() // Job 종료
                .build();
    }

    @Bean
    public Step conditionalJobStep1() {
        return stepBuilderFactory.get("step1")
                .tasklet(((contribution, chunkContext) -> {
                    log.info(">>>>>> this is stepNextConditionalJob step1");
                    /**
                     * ExitStatus를 보고 flow가 진행된다.
                     * **/
                    contribution.setExitStatus(ExitStatus.FAILED);
                    return RepeatStatus.FINISHED;
                })).build();
    }

    @Bean
    public Step conditionalJobStep2() {
        return stepBuilderFactory.get("conditionalJobStep2")
                .tasklet(((contribution, chunkContext) -> {
                    log.info(">>>>>> This is stepNextConditionalJob Step2");
                    return RepeatStatus.FINISHED;
                })).build();
    }

    @Bean
    public Step conditionalJobStep3() {
        return stepBuilderFactory.get("conditionalJobStep3")
                .tasklet(((contribution, chunkContext) -> {
                    log.info(">>>>>> This is stepNextConditionalJob Step3");
                    return RepeatStatus.FINISHED;
                })).build();
    }
}
