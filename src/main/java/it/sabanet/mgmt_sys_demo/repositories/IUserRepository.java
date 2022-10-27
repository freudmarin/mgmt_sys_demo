package it.sabanet.mgmt_sys_demo.repositories;

import it.sabanet.mgmt_sys_demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
