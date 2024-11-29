package org.example.collabo_creator_boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//Creat time, mod time db에 업데이트 위해 필요한 설정
@EnableJpaAuditing
@SpringBootApplication
@EnableJpaAuditing
public class CollaboCreatorBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(CollaboCreatorBootApplication.class, args);
    }

}
