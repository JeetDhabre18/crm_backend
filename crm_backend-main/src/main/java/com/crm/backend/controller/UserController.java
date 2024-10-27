package com.crm.backend.controller;

import com.crm.backend.dto.CustomerDto;
import com.crm.backend.dto.UserDto;
import com.crm.backend.model.Customer;
import com.crm.backend.model.User;
import com.crm.backend.services.CustomerService;
import com.crm.backend.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CustomerService customerService;

    @PostMapping("createAdmin")
    public ResponseEntity<User> createAdmin(@RequestBody UserDto userDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int adminId = ((User) authentication.getPrincipal()).getId(); // Assuming you have a method to get the ID

        User newAdmin =  userService.addAdmin(userDto, adminId);
        return ResponseEntity.ok(newAdmin);
    }

    @PostMapping("create_sales-representative")

    public ResponseEntity<User> createSalesRepresentative(@RequestBody @Valid UserDto userDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int adminId = ((User) authentication.getPrincipal()).getId();
        User createdSalesRep = userService.addSalesRepresentative(userDto,adminId);
        return ResponseEntity.ok(createdSalesRep);
    }


    @PostMapping("admin/createCustomer")
    public ResponseEntity<Customer> createCustomerByAdmin(@RequestBody CustomerDto customerDto,  @RequestParam String salesRepEmail) {

        // Retrieve the authenticated admin's ID
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int adminId = ((User) authentication.getPrincipal()).getId();
        User salesRep=(User)userService.loadUserByUsername(salesRepEmail);
        int salesRepId=salesRep.getId();

        // Pass the email instead of ID
        Customer createdCustomer = customerService.addCustomerByAdmin(customerDto, adminId, salesRepId);
        return ResponseEntity.ok(createdCustomer);
    }

    @PostMapping("sales_representative/createCustomer")
    public ResponseEntity<Customer> createCustomerBySalesRepresentative(@RequestBody CustomerDto customerDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int salesRepId = ((User) authentication.getPrincipal()).getId();
        Customer createdCustomer = customerService.addCustomerBySalesRepresentative(customerDto,salesRepId);
        return ResponseEntity.ok(createdCustomer);
    }

    @GetMapping("allSalesReps")
    public ResponseEntity<List<String>> getAllSalesRepresentative() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User user = (User) authentication.getPrincipal(); // Cast to your User class
        int adminId = user.getId(); // Get the admin ID from the authenticated user

        List<User> salesReps = userService.getAllSalesRepresentative(adminId);
        List<String> salesRepEmails = salesReps.stream()
                .map(User::getEmail) // Assuming User has a getEmail method
                .collect(Collectors.toList());
        return ResponseEntity.ok(salesRepEmails);
    }




}


