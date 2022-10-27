package it.sabanet.mgmt_sys_demo.dto;

import lombok.Getter;

@Getter
public class LoginRequest {
    private String username;
    private String password;
}
