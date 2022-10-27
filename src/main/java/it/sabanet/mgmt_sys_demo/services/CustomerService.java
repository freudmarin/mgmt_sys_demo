package it.sabanet.mgmt_sys_demo.services;

public interface CustomerService {
    String getRepairStatus(String caseNumber);
    void pay(String caseNumber, double money);
}
