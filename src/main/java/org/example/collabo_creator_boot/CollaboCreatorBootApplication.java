package org.example.collabo_creator_boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CollaboCreatorBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(CollaboCreatorBootApplication.class, args);
    }

}
