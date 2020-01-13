package com.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class StartServer {
    private static final String SWAGGER_UI = "http://localhost:8080/courses-management/swagger-ui.html";

    public static void main(String[] args) throws IOException {
        SpringApplication.run(StartServer.class, args);
        openHomePage();
    }

    private static void openHomePage() throws IOException {
        Runtime rt = Runtime.getRuntime();
        rt.exec("rundll32 url.dll,FileProtocolHandler" + SWAGGER_UI);
    }
}
