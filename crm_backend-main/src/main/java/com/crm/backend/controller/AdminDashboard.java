package com.crm.backend.controller;

import com.crm.backend.model.Customer;
import com.crm.backend.model.User;
import com.crm.backend.services.CustomerService;
import com.crm.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.*;
@RestController
@RequestMapping("/admin/dashboard")
public class AdminDashboard {

    @Autowired
    private UserService userService;
    @Autowired
    private CustomerService customerService;

//    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//    int adminId = ((User) authentication.getPrincipal()).getId();
//    @GetMapping
//    public List<Map<String, Object>> getAllSalesRepSummary() {
//       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//      int adminId = ((User) authentication.getPrincipal()).getId(); // Assuming you have a method to get the ID
//
//        // Fetch all sales representatives
//        List<User> salesReps =  userService.getAllSalesRepresentative(adminId);
//        return salesReps.stream().map(salesRep -> {
//            Map<String, Object> response = new HashMap<>();
//
//            // Fetch total sales and customer details for each sales representative
//
//            List<Customer> customers = customerService.getCustomersBySalesRepId(salesRep.getId());
//
//            // Prepare the response
//            response.put("salesRep", salesRep);
//            response.put("customers", customers);
//
//            return response;
//        }).toList();
//    }
//
//    @GetMapping
//    public List<Customer> getCustomerscreatedByAdmin() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        int adminId = ((User) authentication.getPrincipal()).getId();
//        return customerService.getCustomersByAdminId(adminId);
//    }

    @GetMapping
    public Map<String, Object> getDashboardData() {
        // Retrieve the authenticated admin ID
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int adminId = ((User) authentication.getPrincipal()).getId();

        // Fetch all sales representatives created by this admin
        List<User> salesReps = userService.getAllSalesRepresentative(adminId);

        // Collect sales representatives and their customers
        List<Map<String, Object>> salesRepSummaries = new ArrayList<Map<String, Object>>();
        for (User salesRep : salesReps) {
            Map<String, Object> salesRepData = new HashMap<>();
            List<Customer> customers = customerService.getCustomersBySalesRepId(salesRep.getId());

            salesRepData.put("salesRep", salesRep);
            salesRepData.put("customers", customers);
            salesRepData.put("customerCount", customers.size());

            salesRepSummaries.add(salesRepData);
        }

        // Fetch all customers created directly by the admin
        List<Customer> adminCustomers = customerService.getCustomersByAdminId(adminId);

        // Prepare a single response map
        Map<String, Object> response = new HashMap<>();
        response.put("salesRepSummaries", salesRepSummaries);
        response.put("adminCustomers", adminCustomers);

        return response;
    }
}
