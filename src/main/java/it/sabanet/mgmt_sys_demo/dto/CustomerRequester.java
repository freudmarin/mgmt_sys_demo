package it.sabanet.mgmt_sys_demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerRequester {
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String address;
    private String fiscalCode;
}
