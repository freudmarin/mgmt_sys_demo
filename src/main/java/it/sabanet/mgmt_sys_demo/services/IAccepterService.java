package it.sabanet.mgmt_sys_demo.services;

import it.sabanet.mgmt_sys_demo.dto.RepairRequest;

public interface IAccepterService {
    void accept(RepairRequest request);
}
