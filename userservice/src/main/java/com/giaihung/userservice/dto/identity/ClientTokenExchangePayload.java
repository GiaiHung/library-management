package com.giaihung.userservice.dto.identity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientTokenExchangePayload {
    private String grant_type;
    private String client_id;
    private String client_secret;
    private String scope;
}
