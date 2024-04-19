package elice04_pikacharger.pikacharger.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MyTokenPayload {
    private String email;
    private String name;
    private List<String> roles = new ArrayList<>();
}
