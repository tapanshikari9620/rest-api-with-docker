package com.api.docker.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DeckerProjectApplication {

	public static void main(String[] args) {
        SpringApplication.run(DeckerProjectApplication.class, args);
    }

    @GetMapping("/")
    public String home() {
        return "Hello from Spring Boot Docker Demo 🚀";
    }

    @GetMapping("/devops")
    public String devops() {
        return "Jenkins + Docker CI/CD working ✅";
    }
    @GetMapping("/tapan")
    public String devops1() {
        return "Jenkins + Docker CI/CD working complete project ✅";
    }
}