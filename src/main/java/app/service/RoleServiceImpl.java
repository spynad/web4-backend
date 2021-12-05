package app.service;

import app.model.Role;
import app.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService{
    private final RoleRepository roleRepository;

    public void saveRole(Role role) {
        roleRepository.save(role);
    }
}
