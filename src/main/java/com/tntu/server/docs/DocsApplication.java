package com.tntu.server.docs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"com.tntu.server.docs.*"})
@EnableJpaRepositories(basePackages = {"com.tntu.server.docs.db.repositories.db"})
@EntityScan(basePackages = {"com.tntu.server.docs.db.entities"})
@EnableTransactionManagement
public class DocsApplication {

    public static void main(String[] args) {
        SpringApplication.run(DocsApplication.class, args);
    }

}
