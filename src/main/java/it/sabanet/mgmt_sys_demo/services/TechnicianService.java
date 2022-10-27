package it.sabanet.mgmt_sys_demo.services;

import it.sabanet.mgmt_sys_demo.models.Repair;
import it.sabanet.mgmt_sys_demo.models.RepairStatus;
import it.sabanet.mgmt_sys_demo.repositories.IRepairRepository;
import it.sabanet.mgmt_sys_demo.repositories.ITechnicianRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TechnicianService implements ITechnicianService {

    private final IRepairRepository repairRepository;
    private final ITechnicianRepository technicianRepository;

    @Override
    public List<Repair> viewPendingRepairs() {
        return repairRepository.findByStatus(RepairStatus.PENDING);
    }

    @Override
    public List<Repair> viewAcceptedRepairs() {
        // get the technician from the security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Long techId = Long.valueOf(user.getUsername());
        return repairRepository.findByStatusAndTechnicianId(RepairStatus.IN_PROGRESS, techId);
    }

    @Override
    public void acceptRepair(Long repairId) {
        Repair repair = repairRepository.findById(repairId).get();
        repair.setStatus(RepairStatus.IN_PROGRESS);
        // get the technician from the security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Long techId = Long.valueOf(user.getUsername());
        repair.setTechnician(technicianRepository.findById(techId).get());
        repairRepository.save(repair);
    }

    @Override
    public void rejectRepair(Long repairId, String reason) {
        Repair repair = repairRepository.findById(repairId).get();
        repair.setStatus(RepairStatus.CANCELED);
        repair.setRepairNotes(reason);
        repairRepository.save(repair);
    }

    @Override
    public void completeRepair(Long repairId, double price) {
        Repair repair = repairRepository.findById(repairId).get();
        repair.setStatus(RepairStatus.COMPLETED);
        if (new Date().after(repair.getWarrantyExpireDate())) {
            repair.setPrice(price);
        }
        repairRepository.save(repair);
    }
}
