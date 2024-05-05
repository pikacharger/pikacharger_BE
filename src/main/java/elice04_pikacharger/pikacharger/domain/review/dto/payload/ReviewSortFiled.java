package elice04_pikacharger.pikacharger.domain.review.dto.payload;

import lombok.Getter;

@Getter
public enum ReviewSortFiled {
    CREATED_DATE("createDate"),;
    private final String value;

    ReviewSortFiled(String value) {
        this.value = value;
    }

}
