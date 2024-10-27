package com.crm.backend.dto;

import com.crm.backend.model.User;
import com.crm.backend.model.UserStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserDto {

    @NotBlank(message = "User name should not be blank")
    private String firstName;

    @NotBlank(message = "User last name should not be blank")
    private String lastName;

    @NotBlank(message = "User email should not be blank")
    private String email;

    @NotBlank(message = "User password should not be blank")
    private String password;

    @NotBlank(message = "User phone number should not be blank")
    private String phoneNo;


    public User toUser() {
        return User.builder()
                .firstName(this.firstName)
                .lastName(this.lastName)
                .email(this.email)
                .password(this.password) // Make sure to hash the password later
                .phoneNo(this.phoneNo)
                .userStatus(UserStatus.ACTIVE) // Set default status as needed
                .build();
    }

}
