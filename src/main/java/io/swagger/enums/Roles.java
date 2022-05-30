package io.swagger.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Roles implements GrantedAuthority {
    CUSTOMER,
    EMPLOYEE,
    BANK,
    DISABLED;

    @Override
    public String getAuthority() {
        return name();
    }
}
