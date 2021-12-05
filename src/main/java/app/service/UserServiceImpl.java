package app.service;

import app.configuration.security.UserRole;
import app.model.Role;
import app.model.User;
import app.payload.request.UserRequest;
import app.repository.RoleRepository;
import app.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public void saveUser(UserRequest request) {
        if (repository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("User exists");
        }
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByUserRole(UserRole.ROLE_REGULAR));

        User user = new User(request.getUsername(), passwordEncoder.encode(request.getPassword()), roles);
        repository.save(user);
    }
}
