package elice04_pikacharger.pikacharger.domain.charger.publicdata;

import elice04_pikacharger.pikacharger.domain.charger.dto.payload.PublicChargerDataDto;
import elice04_pikacharger.pikacharger.domain.chargertype.dto.payload.PublicChargerTypeDataDto;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CustomFieldSetMapper implements FieldSetMapper<PublicChargerDataDto> {
    public static final int COORDINATE_PAIRS_COUNT = 2; // 좌표 쌍의 개수 2개

    @Override
    public PublicChargerDataDto mapFieldSet(FieldSet fieldSet) throws BindException {
        PublicChargerDataDto publicChargerDataDto = new PublicChargerDataDto();

        String mapLocation = fieldSet.readString("mapLocation");
        String[] locationParts = mapLocation.split(",");
        if (locationParts.length == COORDINATE_PAIRS_COUNT) {
            double latitude = Double.parseDouble(locationParts[0].trim());
            double longitude = Double.parseDouble(locationParts[1].trim());
            publicChargerDataDto.setLatitude(latitude);
            publicChargerDataDto.setLongitude(longitude);
        }

        String[] types = fieldSet.readString("chargerType").split("\\+");
        List<PublicChargerTypeDataDto> publicChargerTypeDataDtoList = Arrays.stream(types)
                .map(type -> new PublicChargerTypeDataDto(type.trim()))
                .collect(Collectors.toList());
        publicChargerDataDto.setPublicChargerTypeDataDtoList(publicChargerTypeDataDtoList);

        publicChargerDataDto.setChargerLocation(fieldSet.readString("chargerLocation"));
        publicChargerDataDto.setChargerName(fieldSet.readString("chargerName"));
        publicChargerDataDto.setChargingSpeed(fieldSet.readString("chargingSpeed"));
        publicChargerDataDto.setCompanyName(fieldSet.readString("companyName"));
        publicChargerDataDto.setChargerStatus(fieldSet.readString("chargerStatus"));

        return publicChargerDataDto;
    }
}
