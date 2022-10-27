package it.sabanet.mgmt_sys_demo.models;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    ROLE_TECHNICIAN,
    ROLE_ACCEPTANCE,
    ROLE_ADMIN;
    ;

    @Override
    public String getAuthority() {
        return name();
    }

}
