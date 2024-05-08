package elice04_pikacharger.pikacharger.domain.common.controller;

import elice04_pikacharger.pikacharger.domain.common.Role;
import elice04_pikacharger.pikacharger.domain.common.RoleService;
import elice04_pikacharger.pikacharger.domain.common.dto.result.RoleResult;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/role")
@Tag(name = "(유저 권한)", description = "유저 권한")
public class RoleController {
    private final RoleService roleService;

    @GetMapping()
    public ResponseEntity<List<RoleResult>> getAllRoles() {
        return new ResponseEntity<>(roleService.findAll(), HttpStatus.OK);
    }
}
