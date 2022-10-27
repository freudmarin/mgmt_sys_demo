package it.sabanet.mgmt_sys_demo.services;


import it.sabanet.mgmt_sys_demo.dto.UserDto;
import it.sabanet.mgmt_sys_demo.exception.ResourceNotFoundException;
import it.sabanet.mgmt_sys_demo.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    /**
     * Gets all User stored into the system
     *
     * @return all users stored into the system
     */
    List<UserDto> getAllUsers();

    /**
     * Find a user by its username
     *
     * @param username the username to search for
     * @return the user with the given username
     * @throws ResourceNotFoundException when the username does not exist
     */
    User findByUsername(String username) throws ResourceNotFoundException;

    UserDto addUser(UserDto user);
    ResponseEntity<UserDto> getUserById(long id);
    ResponseEntity<UserDto> updateUserById(long id, UserDto user);
    ResponseEntity<HttpStatus> deleteUserById(long id);

}
