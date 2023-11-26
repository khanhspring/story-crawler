package com.storyscawler.infrastructure.property;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Getter
@Setter
@Validated
public class BasicAuthUser {

    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotEmpty
    private List<String> roles;
}
