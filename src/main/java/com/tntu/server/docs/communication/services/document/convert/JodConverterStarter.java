package com.tntu.server.docs.communication.services.document.convert;

import com.tntu.server.docs.communication.options.ConverterOptions;
import org.jodconverter.local.office.LocalOfficeManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class JodConverterStarter {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ConverterOptions converterOptions;

    @PostConstruct
    public void init() {
        try {
            LocalOfficeManager.builder()
                    .officeHome(converterOptions.getOfficeLocation())
                    .install()
                    .build()
                    .start();
        } catch (Exception e) {
            log.error("Can not load .doc files converter", e);
        }
    }
}
