package app.configuration.security;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    ROLE_ADMIN,
    ROLE_REGULAR;

    @Override
    public String getAuthority() {
        return name();
    }
}
