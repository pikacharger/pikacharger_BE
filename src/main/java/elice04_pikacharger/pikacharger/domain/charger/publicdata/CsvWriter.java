package elice04_pikacharger.pikacharger.domain.charger.publicdata;

import elice04_pikacharger.pikacharger.domain.charger.dto.payload.PublicChargerDataDto;
import elice04_pikacharger.pikacharger.domain.charger.entity.Charger;
import elice04_pikacharger.pikacharger.domain.charger.repository.ChargerRepository;
import elice04_pikacharger.pikacharger.domain.chargertype.dto.payload.PublicChargerTypeDataDto;
import elice04_pikacharger.pikacharger.domain.chargertype.entity.ChargerType;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class CsvWriter implements ItemWriter<PublicChargerDataDto> {

    private final ChargerRepository chargerRepository;

    @Override
    public void write(Chunk<? extends PublicChargerDataDto> chunk) throws Exception {
        Chunk<Charger> chargerList = new Chunk<>();

        chunk.forEach(publicChargerDataDto -> {
            Charger charger = publicChargerDataDto.toEntity();
            for (PublicChargerTypeDataDto chargerTypes : publicChargerDataDto.getPublicChargerTypeDataDtoList()){
                ChargerType chargerType = ChargerType.builder()
                        .type(chargerTypes.getType())
                        .charger(charger)
                        .build();
                charger.getChargerTypes().add(chargerType);
            }

            chargerList.add(charger);
        });

        chargerRepository.saveAll(chargerList);
    }
}
