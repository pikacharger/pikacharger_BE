package elice04_pikacharger.pikacharger.domain.charger.publicdata;

import jakarta.annotation.PostConstruct;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Configuration
public class SQLReader {

    @Value("classpath:sql/chargercompanyprice.sql")
    private Resource resourceFile;

    @Bean
    public ItemReader<String> sqlFileReader() {
        return new ItemReader<String>() {
            private List<String> sqlStatements;
            private Iterator<String> iterator;

            @PostConstruct
            public void init() throws IOException {
                try {
                    byte[] bytes = resourceFile.getInputStream().readAllBytes();
                    String sqlContent = new String(bytes, StandardCharsets.UTF_8);
                    sqlStatements = Arrays.asList(sqlContent.split(";"));
                    iterator = sqlStatements.iterator();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException("SQL 파일을 읽는 중 오류가 발생했습니다.", e);
                }
            }

            @Override
            public String read() throws Exception{
                return iterator.hasNext() ? iterator.next() : null;
            }
        };
    }

}
