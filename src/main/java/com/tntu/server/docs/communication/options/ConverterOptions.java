package com.tntu.server.docs.communication.options;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ConverterOptions {

    @Value("${office.location}")
    private String officeLocation;

    public String getOfficeLocation() {
        return officeLocation;
    }
}
