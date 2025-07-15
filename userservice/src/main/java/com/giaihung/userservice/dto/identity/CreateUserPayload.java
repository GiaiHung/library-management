package com.giaihung.userservice.dto.identity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserPayload {
    private String username;

    private Boolean enabled;

    private String email;

    private Boolean emailVerified;

    private String firstName;

    private String lastName;

    private List<Credential> credentials;
}
