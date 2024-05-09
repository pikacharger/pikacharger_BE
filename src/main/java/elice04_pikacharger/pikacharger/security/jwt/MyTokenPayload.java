package elice04_pikacharger.pikacharger.security.jwt;

import elice04_pikacharger.pikacharger.domain.user.entity.Role;
import io.netty.handler.codec.socksx.v4.Socks4CommandRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyTokenPayload {
    private Long userId;
    private String email;
    private String name;
    private Set<Role> roles = new HashSet<>();



}
