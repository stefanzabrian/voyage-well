package com.dev.voyagewell.service.user;

import com.dev.voyagewell.controller.dto.register.RegisterDto;
import com.dev.voyagewell.controller.dto.user.UserProfileDto;
import com.dev.voyagewell.model.user.Client;
import com.dev.voyagewell.model.user.Role;
import com.dev.voyagewell.model.user.User;
import com.dev.voyagewell.repository.user.UserRepository;
import com.dev.voyagewell.service.mail.MailService;
import com.dev.voyagewell.service.user.client.ClientService;
import com.dev.voyagewell.configuration.utils.exception.ResourceNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ClientService clientService;
    private final MailService mailService;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, ClientService clientService, MailService mailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.clientService = clientService;
        this.mailService = mailService;
    }

    @Override
    public void validateString(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " must not be empty or blank!");
        }
    }

    @Override
    public void profileUpdate(String email, UserProfileDto profileDto) throws ResourceNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("user don't exists"));

        Client client = user.getClient();

        user.setFirstName(profileDto.getFirstName());
        user.setLastName(profileDto.getLastName());
        user.setNickName(profileDto.getNickName());
        user.setEmail(profileDto.getEmail());
        client.setBioInfo(profileDto.getBioInfo());
        client.setPhoneNumber(profileDto.getPhoneNumber());
        client.setProfilePictureUrl(profileDto.getAvatarUrl());

        userRepository.save(user);
        clientService.save(client);
    }

    @Override
    public String getAvatarUrl(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            return userRepository.findByEmail(email).get().getClient().getProfilePictureUrl();
        }
        return "";
    }

    @Override
    public String getNickName(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            return userRepository.findByEmail(email).get().getNickName();
        }
        return "";
    }

    @Override
    public void updatePassword(String email, String password) throws ResourceNotFoundException, MessagingException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User don't exists"));
        try {
            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update new password");
        }
        try {
            mailService.sendEmail(
                    "security@voyage-well.com",
                    email,
                    "Password updated successfully!",
                    "Password for account with username " + email + " updated successfully!"
            );
        } catch (MessagingException e) {
            throw new MessagingException("Failed to send confirmation mail");
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
            clientService.delete(userToBeDeleted.getClient());
        } else {
            throw new ResourceNotFoundException("User not found");
        }
    }

    @Override
    public UserProfileDto getProfileDtoByEmail(String email) throws ResourceNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User don't exists"));
        Client client = user.getClient();
        if (client == null) {
            throw new ResourceNotFoundException("Client don't exists");
        }

        UserProfileDto profileDto = new UserProfileDto();
        profileDto.setFirstName(user.getFirstName());
        profileDto.setLastName(user.getLastName());
        profileDto.setNickName(user.getNickName());
        profileDto.setEmail(user.getEmail());
        profileDto.setBioInfo(client.getBioInfo());
        profileDto.setPhoneNumber(client.getPhoneNumber());
        profileDto.setAvatarUrl(client.getProfilePictureUrl());
        return profileDto;
    }
}

