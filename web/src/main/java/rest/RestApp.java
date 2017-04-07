package rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class RestApp extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(RestApp.class, args);
    }
}
