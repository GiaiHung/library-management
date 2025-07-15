package com.giaihung.userservice.repository;

import com.giaihung.userservice.dto.identity.ClientTokenExchangeDTO;
import com.giaihung.userservice.dto.identity.ClientTokenExchangePayload;
import com.giaihung.userservice.dto.identity.CreateUserPayload;
import feign.QueryMap;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "identity-client", url = "${idp.url}")
public interface IdentityClient {
    @PostMapping(value = "/realms/${idp.realm}/protocol/openid-connect/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    ClientTokenExchangeDTO exchangeClientToken(@QueryMap() ClientTokenExchangePayload payload);

    @PostMapping(value = "/admin/realms/${idp.realm}/users", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> createUser(@RequestBody() CreateUserPayload payload, @RequestHeader("authorization") String token);
}
