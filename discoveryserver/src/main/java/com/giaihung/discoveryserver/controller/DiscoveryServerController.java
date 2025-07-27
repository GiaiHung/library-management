package com.giaihung.discoveryserver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/discoveryserver")
public class DiscoveryServerController {
    @GetMapping("/health-check")
    public
    ResponseEntity<Map<String,Object>> healthCheck() {
        Map<String, Object> healthStatus = new HashMap<>();
        healthStatus.put("status", "ok");
        healthStatus.put("uptime", Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
        healthStatus.put("timestamp", Instant.now().toString());
        healthStatus.put("message", "Server is running");
        return ResponseEntity.ok(healthStatus);
    }
}
