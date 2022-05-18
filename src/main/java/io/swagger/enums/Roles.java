package io.swagger.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Roles implements GrantedAuthority {
    CUSTOMER,
    EMPLOYEE,
    DISABLED;

    @Override
    public String getAuthority() {
        return name();
    }
}
