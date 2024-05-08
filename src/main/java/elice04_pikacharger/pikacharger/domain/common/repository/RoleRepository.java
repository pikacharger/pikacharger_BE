package elice04_pikacharger.pikacharger.domain.common.repository;

import elice04_pikacharger.pikacharger.domain.common.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);
}
