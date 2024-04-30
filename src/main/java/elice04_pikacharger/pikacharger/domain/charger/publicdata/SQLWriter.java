package elice04_pikacharger.pikacharger.domain.charger.publicdata;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@RequiredArgsConstructor
public class SQLWriter implements ItemWriter<String> {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void write(Chunk<? extends String> chunk) throws Exception {
        chunk.forEach(sql ->{
            jdbcTemplate.execute(sql);
        });
    }
}
