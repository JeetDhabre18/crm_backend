package com.crm.backend.services;

import com.crm.backend.dto.CustomerDto;
import com.crm.backend.model.Customer;
import com.crm.backend.model.User;
import com.crm.backend.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class  CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;




//    public Customer addCustomerBySalesRepresentative(CustomerDto customerDto, int salesRepId) {
//        Customer customer=customerDto.addCustomer();
//        User creator = userServic
////        customer.setCreatedBy(salesRepId);
//        customer.setAssignedTo(salesRepId);
//        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
//         return customerRepository.save(customer);
//    }

    public Customer addCustomerBySalesRepresentative(CustomerDto customerDto, int salesRepId) {
        Customer customer = customerDto.addCustomer();

        // Fetch the user (sales representative)
        User creator = userService.getUserById(salesRepId);
                //.orElseThrow(() -> new IllegalArgumentException("Sales representative not found"));


        customer.setCreatedBy(creator);
        customer.setAssignedTo(creator.getId());

        // Encode the password
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));

        return customerRepository.save(customer);
    }

    public Customer addCustomerByAdmin(CustomerDto customerDto, int adminId, int salesRepId) {
        Customer customer = customerDto.addCustomer();

        User admin = userService.getUserById(adminId);

// Fetch the sales representative to whom the customer will be assigned
//        User salesRep = userService.getUserById(salesRepId);
//        if (salesRep == null || !salesRep.getRole().equals(Role.SALES_REPRESENTATIVE)) {
//            throw new IllegalArgumentException("Sales representative not found or invalid role");
//        }

        // Set createdBy as admin and assignedTo as sales representative
        customer.setCreatedBy(admin);
        customer.setAssignedTo(salesRepId);

        // Encode the password
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));

        return customerRepository.save(customer);
    }

    public List<Customer> getCustomersBySalesRepId(Integer salesRepid) {
        return customerRepository.findByCreatedBy(salesRepid);
    }

    public List<Customer> getCustomersByAdminId(int adminId) {
        return customerRepository.findByCreatedByAdmin(adminId);
    }
}
