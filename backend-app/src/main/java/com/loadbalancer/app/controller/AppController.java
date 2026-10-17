package com.loadbalancer.app.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
public class AppController {

    @Value("${server.port:8080}")
    private int serverPort;

    @Value("${app.instance-id:unknown}")
    private String instanceId;

    /**
     * Root endpoint — proves which backend instance handled the request.
     */
    @GetMapping("/")
    public Map<String, Object> index() {
        return Map.of(
                "message", "Hello from backend instance!",
                "instanceId", instanceId,
                "port", serverPort,
                "timestamp", LocalDateTime.now().toString()
        );
    }

    /**
     * Simulates a lightweight workload so you can observe
     * round-robin distribution across the three instances.
     */
    @GetMapping("/api/info")
    public Map<String, Object> info() {
        return Map.of(
                "instanceId", instanceId,
                "port", serverPort,
                "status", "UP",
                "javaVersion", System.getProperty("java.version"),
                "availableProcessors", Runtime.getRuntime().availableProcessors(),
                "freeMemoryMB", Runtime.getRuntime().freeMemory() / (1024 * 1024),
                "timestamp", LocalDateTime.now().toString()
        );
    }
}
