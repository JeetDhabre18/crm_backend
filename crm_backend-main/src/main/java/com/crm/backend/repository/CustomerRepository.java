package com.crm.backend.repository;

import com.crm.backend.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    @Query("SELECT c FROM Customer c WHERE c.createdBy.id = :salesRepid")
    List<Customer> findByCreatedBy(Integer salesRepid);

    @Query("SELECT c FROM Customer c WHERE c.createdBy.id = :adminId")

    List<Customer> findByCreatedByAdmin(Integer adminId);
}

