package com.voc.videoDU.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Value("${spring.data.cassandra.keyspace-name}")
    private String keyspace;

    @Value("${spring.data.cassandra.contact-points}")
    private String contact_points;

    @Value("${spring.data.cassandra.password}")
    private String password;

    @Value("${spring.data.cassandra.username}")
    private String username;

    @Value("${spring.data.cassandra.port}")
    private int port;

    @GetMapping(value = "/hello")
    public String sayHello() {
        return "Say hello";
    }
}
