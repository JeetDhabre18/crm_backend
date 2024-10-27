package com.crm.backend.dto;

import com.crm.backend.model.Customer;
import com.crm.backend.model.UserStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CustomerDto {
    @NotBlank(message = "Customer name should not be blank")
    private String firstName;

    @NotBlank(message = "Customer last name should not be blank")
    private String lastName;

    @NotBlank(message = "Customer email should not be blank")
    private String email;

    @NotBlank(message = "Customer password should not be blank")
    private String password;

    @NotBlank(message = "Customer phone number should not be blank")
    private String phoneNo;



    public Customer addCustomer(){
        return Customer.builder()
                .firstName(this.firstName)
                .lastName(this.lastName)
                .email(this.email)
                .password(this.password)
                .phoneNo(this.phoneNo)
                .userStatus(UserStatus.ACTIVE)

                .build();
    }
}
