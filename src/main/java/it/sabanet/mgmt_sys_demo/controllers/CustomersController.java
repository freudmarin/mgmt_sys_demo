package it.sabanet.mgmt_sys_demo.controllers;

import it.sabanet.mgmt_sys_demo.dto.PayDto;
import it.sabanet.mgmt_sys_demo.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
public class CustomersController {
    private final CustomerService customerService;

    public CustomersController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/getRepairStatus/{caseNumber}")
    public String getRepairStatus(@PathVariable("caseNumber") String caseNumber) {
        return customerService.getRepairStatus(caseNumber);
    }

    @PostMapping("/pay/{caseNumber}")
    public ResponseEntity pay(@PathVariable("caseNumber") String caseNumber, @RequestBody PayDto payDto) {
        customerService.pay(caseNumber, payDto.getMoney());
        return new ResponseEntity(HttpStatus.OK);
    }
}
