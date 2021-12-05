package app.repository;

import app.configuration.security.UserRole;
import app.model.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByUserRole(UserRole role);
}
