package it.sabanet.mgmt_sys_demo.repositories;

import it.sabanet.mgmt_sys_demo.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ICustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);
}
