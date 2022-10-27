package it.sabanet.mgmt_sys_demo.controllers;

import it.sabanet.mgmt_sys_demo.models.RepairStatus;
import it.sabanet.mgmt_sys_demo.repositories.IRepairRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/query")
@Slf4j
public class QueryController {
    final IRepairRepository repairRepository;

    public QueryController(IRepairRepository repairRepository) {
        this.repairRepository = repairRepository;
    }

    @GetMapping(value = "/totalNumberOfCompletedRepairsBetween")
    public Integer totalNumberOfCompletedRepairsOverPeriodOfTime(@RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
                                                                                @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        log.info("Executing query");

        return repairRepository.countRepairByStatusAndUpdatedDateTimeBetween(RepairStatus.COMPLETED, start, end);
    }

    @GetMapping(value = "/totalNumberOfRejectedRepairsBetween")
    public Integer totalNumberOfRejectedRepairsOverPeriodOfTime(@RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
                                                                @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return repairRepository.countRepairByStatusAndUpdatedDateTimeBetween(RepairStatus.CANCELED, start, end);
    }

    @GetMapping(value = "/numberOfRepairsByEachTechnician")
    public List<Object> numberOfRepairsProcessedByEachTechnicianOverPeriodOfTime(@RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
                                                                                 @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return repairRepository.countRepairsByEachTechnicianOverTime(start, end);
    }
}
