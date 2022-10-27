package it.sabanet.mgmt_sys_demo.controllers;

import it.sabanet.mgmt_sys_demo.dto.RepairRequest;
import it.sabanet.mgmt_sys_demo.services.AccepterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/acceptance")
public class AcceptanceController {
    @Autowired
    private AccepterService accepterService;

    @PostMapping("/acceptProduct")
    public void accept(@RequestBody RepairRequest repairRequest) {
        accepterService.accept(repairRequest);
    }
}
