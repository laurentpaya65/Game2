package org.epita.game2.exposition;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@ComponentScan(basePackages = {"org.epita.game2"})
@EnableWebSecurity
public class Run {
    public static void main(String[] args) {
        SpringApplication.run(Run.class,args);
    }
}
