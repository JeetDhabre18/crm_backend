package com.crm.backend.dto;

import lombok.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {


    private String email;

    private String password;
}
