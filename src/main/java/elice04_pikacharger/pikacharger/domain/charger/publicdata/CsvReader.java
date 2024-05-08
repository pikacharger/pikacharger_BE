package elice04_pikacharger.pikacharger.domain.charger.publicdata;

import elice04_pikacharger.pikacharger.domain.charger.dto.payload.PublicChargerDataDto;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class CsvReader {

    @Value("${charger.csv-path}")
    private String chargerCsv;

    @Bean
    public FlatFileItemReader<PublicChargerDataDto> csvChargerReader() {
        FlatFileItemReader<PublicChargerDataDto> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(new ClassPathResource(chargerCsv));
        flatFileItemReader.setEncoding("UTF-8");

        DefaultLineMapper<PublicChargerDataDto> defaultLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();

        delimitedLineTokenizer.setIncludedFields(3, 4, 7, 10, 12, 13, 16);
        delimitedLineTokenizer.setNames("chargerLocation","chargerName","chargingSpeed", "companyName","chargerType","chargerStatus","mapLocation");
        defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);

        defaultLineMapper.setFieldSetMapper(new CustomFieldSetMapper());

        flatFileItemReader.setLineMapper(defaultLineMapper);

        return flatFileItemReader;
    }
}
