package elice04_pikacharger.pikacharger.domain.image.dto;

import org.springframework.web.multipart.MultipartFile;

public record ImageResult(
        MultipartFile image
) {
}
