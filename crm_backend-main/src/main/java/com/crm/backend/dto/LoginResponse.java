package com.crm.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class LoginResponse {
    private String email;
    private List<String> roles;
    private String jwtToken;
}
