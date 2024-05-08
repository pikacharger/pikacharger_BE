package elice04_pikacharger.pikacharger.domain.charger.entity;

import lombok.Getter;

@Getter
public enum ChargerRole {
    PUBLICCHARGE("공공"),
    PERSONALCHARGER("개인");

    private final String message;

    ChargerRole(String message) {
        this.message = message;
    }

}
