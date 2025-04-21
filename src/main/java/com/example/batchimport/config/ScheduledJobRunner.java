package com.example.batchimport.config;

import com.example.batchimport.entity.Person;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ScheduledJobRunner {

    private final JobLauncher jobLauncher;
    private final Job importCsvJob;

    public ScheduledJobRunner(JobLauncher jobLauncher, @Qualifier("importCsvJob") Job importCsvJob) {
        this.jobLauncher = jobLauncher;
        this.importCsvJob = importCsvJob;
    }

    @Scheduled(cron = "0 */5 * * * *") // CHAQUE 5 minutes
    public void runJob() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("run.id", UUID.randomUUID().toString())
                .addString("fileName", "data_2024.csv")
                .toJobParameters();

        jobLauncher.run(importCsvJob, jobParameters);
    }
}
