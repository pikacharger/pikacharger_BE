package elice04_pikacharger.pikacharger.domain.charger.service;

import elice04_pikacharger.pikacharger.domain.charger.dto.*;
import elice04_pikacharger.pikacharger.domain.charger.dto.payload.ChargerCreateDto;
import elice04_pikacharger.pikacharger.domain.charger.dto.payload.ChargerUpdateDto;
import elice04_pikacharger.pikacharger.domain.charger.entity.Charger;
import elice04_pikacharger.pikacharger.domain.charger.geocoding.GeocodingAPI;
import elice04_pikacharger.pikacharger.domain.charger.repository.ChargerRepository;
import elice04_pikacharger.pikacharger.domain.chargertype.dto.payload.ChargerTypeDto;
import elice04_pikacharger.pikacharger.domain.chargertype.entity.ChargerType;
import elice04_pikacharger.pikacharger.domain.favorite.repository.FavoriteRepository;
import elice04_pikacharger.pikacharger.domain.image.domain.ChargerImage;
import elice04_pikacharger.pikacharger.domain.image.service.S3UploaderService;
import elice04_pikacharger.pikacharger.domain.user.entity.User;
import elice04_pikacharger.pikacharger.domain.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class ChargerServiceImpl implements ChargerService {

    private final ChargerRepository chargerRepository;
    private final UserRepository userRepository;
    private final GeocodingAPI geocodingAPI;
    private final FavoriteRepository favoriteRepository;
    private final S3UploaderService s3UploaderService;


    @Transactional
    @Override
    public ChargerResponseDto createCharger(ChargerCreateDto chargerCreateDto, List<MultipartFile> multipartFiles, Long userId) throws IOException{
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("유저가 존재하지 않습니다."));

        try{
            List<String> locations = geocodingAPI.coordinatePairs(chargerCreateDto.getChargerLocation());
            chargerCreateDto.setLatitude(Double.parseDouble(locations.get(0))); // 위도
            chargerCreateDto.setLongitude(Double.parseDouble(locations.get(1))); // 경도
        }catch (Exception e){
            throw new RuntimeException("예외 발생"+e.getMessage());
        }

        Charger charger = chargerCreateDto.toEntity(user);
        for (ChargerTypeDto chargerTypeDto : chargerCreateDto.getChargerTypeDtoList()){
            ChargerType chargerType = ChargerType.builder()
                    .type(chargerTypeDto.getType())
                    .charger(charger)
                    .build();
            charger.getChargerTypes().add(chargerType);
        }

        chargerImgUpload(charger, multipartFiles);

        Charger savedCharger = chargerRepository.save(charger);

        return ChargerResponseDto.toDto(savedCharger);
    }

    @Transactional
    @Override
    public ChargerResponseDto updateCharger(ChargerUpdateDto chargerUpdateDto, List<MultipartFile> multipartFiles, Long chargerId, Long userId) throws IOException{
        if (!chargerRepository.existsByIdAndUserId(chargerId, userId)){
            throw new IllegalStateException("충전소 수정권한이 없습니다.");
        }
        Charger charger = getCharger(chargerId);
        try{
            List<String> locations = geocodingAPI.coordinatePairs(chargerUpdateDto.getChargerLocation());
            chargerUpdateDto.setLatitude(Double.parseDouble(locations.get(0))); // 위도
            chargerUpdateDto.setLongitude(Double.parseDouble(locations.get(1))); // 경도
        }catch (Exception e){
            log.debug("좌표를 받아올수 없습니다. 오류 코드: "+ e.getMessage());
        }

        charger.updateCharger(
                chargerUpdateDto.getChargerLocation(),
                chargerUpdateDto.getChargerName(),
                chargerUpdateDto.getChargingSpeed(),
                chargerUpdateDto.getLatitude(),
                chargerUpdateDto.getLongitude(),
                chargerUpdateDto.getContent(),
                chargerUpdateDto.getPersonalPrice()
                );

        charger.getChargerTypes().clear();
        for (ChargerTypeDto chargerTypeDto : chargerUpdateDto.getChargerTypeDtoList()){
            ChargerType chargerType = ChargerType.builder()
                    .type(chargerTypeDto.getType())
                    .charger(charger)
                    .build();
            charger.getChargerTypes().add(chargerType);
        }

        charger.getChargerImages().clear();
        chargerImgUpload(charger, multipartFiles);

        Charger updatedCharger = chargerRepository.save(charger);

        return ChargerResponseDto.toDto(updatedCharger);
    }

    @Override
    public void deleteCharger(Long chargerId, Long userId) {
        if (!chargerRepository.existsByIdAndUserId(chargerId, userId)){
            throw new IllegalStateException("충전소 삭제권한이 없습니다.");
        }
        Charger charger = getCharger(chargerId);
        chargerRepository.delete(charger);
    }

    @Override
    public ChargerDetailResponseDto chargerDetail(Long chargerId, Long userId) {
        Charger charger = chargerRepository.findById(chargerId)
                .orElseThrow(() -> new EntityNotFoundException("충전소가 존재하지 않습니다."));
        boolean favorite = favoriteRepository.existsByChargerIdAndUserId(chargerId, userId);

        return ChargerDetailResponseDto.toDto(charger, favorite);
    }

    @Override
    public ChargerEditResponseDto chargerEditDetail(Long chargerId, Long userId) {
        if (!chargerRepository.existsByIdAndUserId(chargerId, userId)){
            throw new IllegalStateException("충전소 수정권한이 없습니다.");
        }
        Charger charger = getCharger(chargerId);

        return ChargerEditResponseDto.toDto(charger);
    }

    @Override
    public List<GroupedChargerResponseDto> chargerSearch(String location) {
        try{
            List<String> locations = geocodingAPI.coordinatePairs(location);
            List<Charger> chargerList = chargerRepository.findChargersNearby(Double.parseDouble(locations.get(0)),Double.parseDouble(locations.get(1)));
            List<ChargerSearchResponseDto> chargerSearchResponseDtoList = chargerList.stream()
                    .map(ChargerSearchResponseDto::toDto)
                    .toList();

            Map<String, List<ChargerSearchResponseDto>> groupedByLocation = chargerSearchResponseDtoList.stream()
                    .collect(Collectors.groupingBy(dto -> dto.getChargerLocation() + "|" + dto.getChargerName()));

            List<GroupedChargerResponseDto> groupedDtoList = new ArrayList<>();
            for (var entry : groupedByLocation.entrySet()) {
                String[] keyParts = entry.getKey().split("\\|", -1);
                String chargerLocation = keyParts[0];
                String chargerName = keyParts[1];

                GroupedChargerResponseDto groupedChargerResponseDto = new GroupedChargerResponseDto();
                groupedChargerResponseDto.setChargerLocation(chargerLocation);
                groupedChargerResponseDto.setChargerName(chargerName);
                groupedChargerResponseDto.setChargers(entry.getValue());
                groupedDtoList.add(groupedChargerResponseDto);
            }

            return groupedDtoList;
        }catch (Exception e){
            log.debug("좌표를 받아올수 없습니다. 오류 코드: "+ e.getMessage());
        }
        return null;
    }

    @Override
    public List<MyChargerResponseDto> myChargers(Long userId) {
        return chargerRepository.findByUserId(userId).stream()
                .map(MyChargerResponseDto::toDto)
                .collect(Collectors.toList());
    }

    private void chargerImgUpload(Charger charger, List<MultipartFile> multipartFiles) throws IOException {
        List<String> imgPaths = new ArrayList<>();
        if (multipartFiles != null && !multipartFiles.isEmpty()) {
            imgPaths = s3UploaderService.uploadMultipleFiles(multipartFiles, "images");
        }

        for (String imgUrl : imgPaths) {
            ChargerImage image = ChargerImage.builder()
                    .charger(charger)
                    .imageUrl(imgUrl)
                    .build();
            charger.getChargerImages().add(image);
        }
    }

    private Charger getCharger(Long chargerId) {
        return chargerRepository.findById(chargerId)
                .orElseThrow(()->new EntityNotFoundException("충전소가 존재하지 않습니다."));
    }

}
