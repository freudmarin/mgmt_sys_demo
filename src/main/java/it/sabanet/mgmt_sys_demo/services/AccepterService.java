package it.sabanet.mgmt_sys_demo.services;

import it.sabanet.mgmt_sys_demo.dto.RepairRequest;
import it.sabanet.mgmt_sys_demo.models.Customer;
import it.sabanet.mgmt_sys_demo.models.Repair;
import it.sabanet.mgmt_sys_demo.models.RepairStatus;
import it.sabanet.mgmt_sys_demo.repositories.ICustomerRepository;
import it.sabanet.mgmt_sys_demo.repositories.IRepairRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AccepterService implements IAccepterService {

    private final IRepairRepository repairRepository;
    private final ICustomerRepository customerRepository;

    @Override
    @Transactional
    public void accept(RepairRequest request) {
        Optional<Customer> opCustomer = customerRepository.findByEmail(request.getRequester().getEmail());
        Customer customer;
        if (opCustomer.isPresent()) {
            customer = opCustomer.get();
        } else {
            customer = new Customer();
            customer.setName(request.getRequester().getName());
            customer.setSurname(request.getRequester().getSurname());
            customer.setEmail(request.getRequester().getEmail());
            customer.setPhone(request.getRequester().getPhone());
            customer.setAddress(request.getRequester().getAddress());
            customer.setFiscalCode(request.getRequester().getFiscalCode());
            customerRepository.save(customer);
        }
        Repair repair = new Repair();
        repair.setSerialNumber(request.getSerialNumber());
        repair.setBrand(request.getBrand());
        repair.setDateOfPurchase(request.getDateOfPurchase());
        repair.setWarrantyExpireDate(request.getWarrantyExpireDate());
        repair.setCustomer(customer);
        repair.setDescription(request.getDescription());
        repair.setTemplate(request.getTemplate());
        repair.setStatus(RepairStatus.PENDING);
        repair.setCaseNumber(UUID.randomUUID().toString());
        repairRepository.save(repair);
    }

}
