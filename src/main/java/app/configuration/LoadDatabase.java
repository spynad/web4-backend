package app.configuration;

import app.configuration.security.UserRole;
import app.model.Role;
import app.repository.PointRepository;
import app.model.Point;
import app.service.RoleService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
@AllArgsConstructor
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
    private final RoleService roleService;

    @Bean
    CommandLineRunner initDatabase() {
        return args -> {
            Arrays.stream(UserRole.values()).forEach(role ->
                    roleService.saveRole(new Role(role)));
        };
    }
}
