package com.tntu.server.docs.communication.models.requests.sections;

import static com.tntu.server.docs.communication.models.Validation.MAX_LENGTH;
import static com.tntu.server.docs.communication.models.Validation.NAME_PATTERN;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public class CreateSectionRequest {

    @NotBlank
    @Length(max = MAX_LENGTH)
    @Pattern(regexp = NAME_PATTERN, message = "Invalid section name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
