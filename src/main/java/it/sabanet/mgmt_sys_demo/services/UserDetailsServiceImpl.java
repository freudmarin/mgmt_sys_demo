package it.sabanet.mgmt_sys_demo.services;

import it.sabanet.mgmt_sys_demo.dto.UserDto;
import it.sabanet.mgmt_sys_demo.exception.ResourceNotFoundException;
import it.sabanet.mgmt_sys_demo.models.User;
import it.sabanet.mgmt_sys_demo.repositories.IUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserService {

    private final IUserRepository userRepository;

    private ModelMapper mapper = new ModelMapper();

    public UserDetailsServiceImpl(@Qualifier("IUserRepository") IUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(user -> mapToDTO(user)).collect(Collectors.toList());
    }

    @Override
    public User findByUsername(String username) throws ResourceNotFoundException {

        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException(String.format("User with the username %s does not exist!", username))
                );
    }


    public UserDto addUser(UserDto userToBeAddedDto) {
        User user = new User();
        user.setUsername(userToBeAddedDto.getUsername());
        user.setPassword(passwordEncoder.encode(userToBeAddedDto.getPassword()));
        user.setFullName(userToBeAddedDto.getFullName());
        user.setRole(userToBeAddedDto.getRole());
        User savedUser = userRepository.save(user);
        return mapToDTO(savedUser);
    }

    public ResponseEntity<UserDto> getUserById(long id) {
        Optional<User> userData = userRepository.findById(id);
        if (userData.isPresent()) {
            return new ResponseEntity<>(mapToDTO(userData.get()), HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("User not found");
        }
    }

    public ResponseEntity<UserDto> updateUserById(long id, UserDto userDto) {
        Optional<User> userData = userRepository.findById(id);
        if (userData.isPresent()) {
            User _user = userData.get();
            _user.setRole(userDto.getRole());
            _user.setUsername(userDto.getUsername());
            _user.setFullName(userDto.getFullName());
            _user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            User updatedUser = userRepository.save(_user);

            return new ResponseEntity<>(mapToDTO(updatedUser), HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("User not found");
        }
    }

    public ResponseEntity<HttpStatus> deleteUserById(long id) {
        try {
            userRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            throw new ResourceNotFoundException("User not found");
        }
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Load user for authentication spring security");
        try {
            User user = findByUsername(username);

            String role = user.getRole().getAuthority();
            List<GrantedAuthority> grantedAuthorities = AuthorityUtils.createAuthorityList("ROLE_" + role);

            return new org.springframework.security.core.userdetails.User(
                    Long.toString(user.getId()),
                    user.getPassword(),
                    grantedAuthorities);

        } catch (ResourceNotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage(), e);
        }

    }

    private Collection<? extends GrantedAuthority> getAuthorities(String role_user) {
        return Collections.singletonList(new SimpleGrantedAuthority(role_user));
    }

    private final PasswordEncoder passwordEncoder;

    private UserDto mapToDTO(User user) {
        UserDto userDto = mapper.map(user, UserDto.class);
        return userDto;
    }

    private User mapToEntity(UserDto userDto) {
        User user = mapper.map(userDto, User.class);
        return user;
    }
}
