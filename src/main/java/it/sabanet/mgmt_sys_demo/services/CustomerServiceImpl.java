package it.sabanet.mgmt_sys_demo.services;

import it.sabanet.mgmt_sys_demo.models.Customer;
import it.sabanet.mgmt_sys_demo.models.Repair;
import it.sabanet.mgmt_sys_demo.repositories.IRepairRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    IRepairRepository repairRepository;

    @Autowired
    public CustomerServiceImpl(IRepairRepository repairRepository) {
        this.repairRepository = repairRepository;
    }

    @Override
    public String getRepairStatus(String caseNumber) {
       return repairRepository.findRepairStatusByCaseNumber(caseNumber);
    }

    @Override
    public void pay(String caseNumber, double money) {
        Repair repair = repairRepository.findByCaseNumber(caseNumber);
        Double price = repair.getPrice();
        if (money - repair.getPrice() >= 0) {
            repair.setPrice(0.00);
            log.info("We owe you: " + (money - price) + " money");
        } else {
            log.warn("Please, put the correct amount of money");
        }
        repairRepository.save(repair);
    }
}
