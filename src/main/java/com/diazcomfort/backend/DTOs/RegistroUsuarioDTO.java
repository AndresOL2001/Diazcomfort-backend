package com.diazcomfort.backend.DTOs;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistroUsuarioDTO {
 
    @NotBlank
    private String email;
    @NotBlank
    private String password;
     @NotBlank
    private String name;
}

