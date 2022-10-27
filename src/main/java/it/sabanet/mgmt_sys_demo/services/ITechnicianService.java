package it.sabanet.mgmt_sys_demo.services;

import it.sabanet.mgmt_sys_demo.models.Repair;

import java.util.List;

public interface ITechnicianService {
    List<Repair> viewPendingRepairs();
    List<Repair> viewAcceptedRepairs();
    void acceptRepair(Long repairId);

    void rejectRepair(Long repairId, String reason);

    void completeRepair(Long repairId, double price);
}
