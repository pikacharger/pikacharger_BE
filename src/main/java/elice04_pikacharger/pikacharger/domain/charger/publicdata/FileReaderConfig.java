package elice04_pikacharger.pikacharger.domain.charger.publicdata;

import elice04_pikacharger.pikacharger.domain.charger.dto.payload.PublicChargerDataDto;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class FileReaderConfig {

    private final CsvReader csvReader;
    private final CsvWriter csvWriter;
    private final SQLWriter sqlWriter;
    private final SQLReader sqlReader;

    @Bean
    public Job publicChargerDataLoadJob(JobRepository jobRepository, Step publicChargerDataLoadStep, Step executeSqlScriptStep){
        return new JobBuilder("publicChargerDataLoadJob", jobRepository)
                .start(publicChargerDataLoadStep)
                .next(executeSqlScriptStep)
                .build();
    }

    @Bean
    public Step publicChargerDataLoadStep(JobRepository jobRepository, PlatformTransactionManager ptm){
        return new StepBuilder("publicChargerDataLoadStep", jobRepository)
                .<PublicChargerDataDto, PublicChargerDataDto>chunk(100, ptm)
                .reader(csvReader.csvChargerReader())
                .writer(csvWriter)
//                .allowStartIfComplete(true) // step 재 실행
                .build();
    }

    @Bean
    public Step executeSqlScriptStep(JobRepository jobRepository, PlatformTransactionManager ptm){
        return new StepBuilder("executeSqlScriptStep", jobRepository)
                .<String, String>chunk(1, ptm)
                .reader(sqlReader.sqlFileReader())
                .writer(sqlWriter)
//                .allowStartIfComplete(true) // step 재 실행
                .build();
    }
}
