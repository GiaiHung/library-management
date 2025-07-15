package com.giaihung.userservice.service.impl;

import com.giaihung.userservice.dto.CreateUserRequestDTO;
import com.giaihung.userservice.dto.UserResponseDTO;
import com.giaihung.userservice.dto.identity.ClientTokenExchangePayload;
import com.giaihung.userservice.dto.identity.CreateUserPayload;
import com.giaihung.userservice.dto.identity.Credential;
import com.giaihung.userservice.entity.User;
import com.giaihung.userservice.repository.IdentityClient;
import com.giaihung.userservice.repository.UserRepository;

import com.giaihung.userservice.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements IUserService {
    @Value("${idp.client-id}")
    private String clientId;

    @Value(("${idp.client-secret}"))
    private String clientSecret;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IdentityClient identityClient;

    @Override
    public UserResponseDTO createUser(CreateUserRequestDTO dto) {
        var token = identityClient.exchangeClientToken(
                ClientTokenExchangePayload.builder()
                        .grant_type("client_credentials")
                        .scope("openid")
                        .client_id(clientId)
                        .client_secret(clientSecret)
                        .build()
        );
        log.info("Client token exchange: {}", token);

        // Call keycloak to create user
        var createUserResponse = identityClient.createUser(
                CreateUserPayload.builder()
                        .username(dto.getUsername())
                        .enabled(true)
                        .email(dto.getEmail())
                        .emailVerified(true)
                        .firstName(dto.getFirstName())
                        .lastName(dto.getLastName())
                        .credentials(
                                List.of(
                                        Credential.builder()
                                                .type("password")
                                                .value(dto.getPassword())
                                                .temporary(false)
                                                .build()
                                )
                        )
                        .build(),
                "Bearer " + token.getAccessToken()
        );
        String userId = extractUserId(createUserResponse);

        User user = new User();
        user.setUserId(userId);
        user.setEmail(dto.getEmail());
        user.setUsername(dto.getUsername());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setDob(dto.getDob());
        user.setName(dto.getName());

        user = userRepository.save(user);
        return toDTO(user);
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return toDTO(user);
    }

    @Override
    public UserResponseDTO updateUser(Long id, CreateUserRequestDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        user.setEmail(dto.getEmail());
        user.setUsername(dto.getUsername());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setDob(dto.getDob());
        user.setName(dto.getName());

        return toDTO(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private UserResponseDTO toDTO(User user) {
        return UserResponseDTO.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .dob(user.getDob())
                .name(user.getName())
                .id(user.getId())
                .build();
    }

    private String extractUserId(ResponseEntity<?> response) {
        // Sample url: http://localhost:8180/admin/realms/library-management/users/c3a3bd06-736c-4858-aba4-14f7e5a40c6a
        List<String> locations = response.getHeaders().get("Location");
        if(locations == null || locations.isEmpty()) {
            throw new IllegalStateException("Location header is missing in the resposne");
        }
        String location = locations.get(0);
        String[] splittedLocationString = location.split("/");
        return splittedLocationString[splittedLocationString.length - 1];
    }
}
