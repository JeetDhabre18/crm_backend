package com.crm.backend.repository;

import com.crm.backend.model.Role;
import com.crm.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {

    User findByEmail(String email);

    User findById(int id);

//    @Query("SELECT u.email FROM User u WHERE u.role = :role AND u.createdBy = :adminId")
//    List<String> findByRoleAndCreatedBy(Role role, int adminId);
    List<User> findByRoleAndCreatedBy(Role role,int adminId);
}
