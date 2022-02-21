package com.tntu.server.docs.communication.models.requests.sections;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class CreateSectionRequest {

    @NotBlank
    @Length(max = 120)
    @Pattern(regexp = "^[а-яА-Яa-zA-Z0-9 .()і]", message = "Invalid section name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
