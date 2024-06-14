package elice04_pikacharger.pikacharger.domain.common;

import elice04_pikacharger.pikacharger.domain.common.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import elice04_pikacharger.pikacharger.domain.common.dto.RoleResult;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public List<RoleResult> findAll() {
        return roleRepository.findAll().stream().map(r->RoleResult.builder().id(r.getId()).name(r.getName()).build()).toList();
    }
}
