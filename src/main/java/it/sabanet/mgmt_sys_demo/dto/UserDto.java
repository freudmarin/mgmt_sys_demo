package it.sabanet.mgmt_sys_demo.dto;

import it.sabanet.mgmt_sys_demo.models.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private String username;
    private String password;
    private String fullName;
    private UserRole role;

}
