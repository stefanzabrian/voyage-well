package com.dev.voyagewell.service.user;

import com.dev.voyagewell.controller.dto.register.RegisterDto;
import com.dev.voyagewell.model.user.Client;
import com.dev.voyagewell.model.user.Role;
import com.dev.voyagewell.model.user.User;
import com.dev.voyagewell.repository.user.UserRepository;
import com.dev.voyagewell.service.user.client.ClientService;
import com.dev.voyagewell.utils.exception.ResourceNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ClientService clientService;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, ClientService clientService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.clientService = clientService;
    }

    private void validateString(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " must not be empty or blank!");
        }
    }

    private Collection<GrantedAuthority> mapRolesToAuthorities(List<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid username or password"));
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles())
        );
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void create(RegisterDto newUser) {
        validateString(newUser.getEmail(), "Email");
        validateString(newUser.getPassword(), "Password");
        validateString(newUser.getFirstName(), "First name");
        validateString(newUser.getLastName(), "Last name");
        validateString(newUser.getNickName(), "Nick name");
        if (newUser.isAcceptedConditions()) {
            User user = new User();
            user.setEmail(newUser.getEmail());
            user.setPassword(passwordEncoder.encode(newUser.getPassword()));
            user.setFirstName(newUser.getFirstName());
            user.setLastName(newUser.getLastName());
            user.setNickName(newUser.getNickName());
            user.setRoles(newUser.getRoles());
            user.setTermsAndConditions(newUser.isAcceptedConditions());
            user.setPrivacyPolicy(newUser.isAcceptedConditions());

            Client client = new Client();
            clientService.save(client);
            user.setClient(client);

            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("Must accept Terms & Conditions and Privacy Policy");
        }
    }

    @Override
    public void delete(int id, String authId) throws ResourceNotFoundException {
        if (userRepository.existsById(id)) {
            User userToBeDeleted = userRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
            User userAuth = userRepository.findByEmail(authId).orElseThrow(() -> new IllegalArgumentException("Not a user"));

            // Check if the authenticated user is trying to delete themselves
            if (userAuth.getId() == id) {
                throw new IllegalArgumentException("Not allowed to delete oneself");
            }
            userRepository.delete(userToBeDeleted);

        } else {
            throw new ResourceNotFoundException("User not found");
        }
    }
}

