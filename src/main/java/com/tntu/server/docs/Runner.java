package com.tntu.server.docs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public final class Runner implements CommandLineRunner {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${version}")
    private String version;

    @Override
    public void run(String... args) {
        logVersion();
    }

    private void logVersion() {
        log.info("Application version: {}", version);
    }
}
