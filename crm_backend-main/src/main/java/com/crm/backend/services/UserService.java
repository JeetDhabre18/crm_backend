package com.crm.backend.services;

import com.crm.backend.dto.UserDto;
import com.crm.backend.model.Role;
import com.crm.backend.model.User;
import com.crm.backend.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.crm.backend.model.Role.SALES_REPRESENTATIVE;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${Sales-representative.authority}")
    private String salesRepresentativeAuthority;

    @Value("${Admin.authority}")
    private String adminAuthority;



    public User addAdmin(UserDto userDto,int adminId) {
        User user = userDto.toUser();
        user.setAuthorities(adminAuthority);
        user.setRole(Role.ADMIN);
        user.setCreatedBy(adminId);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User addSalesRepresentative(UserDto userDto,int adminId) {
        User user = userDto.toUser();
        user.setAuthorities(salesRepresentativeAuthority);
        user.setRole(SALES_REPRESENTATIVE);
        user.setCreatedBy(adminId);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }



    public List<User> getAllSalesRepresentative(int adminId){
        return userRepository.findByRoleAndCreatedBy(SALES_REPRESENTATIVE, adminId);
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
//        return new org.springframework.security.core.userdetails.User(
//                user.getEmail(),
//                user.getPassword(),
//                user.getAuthorities()
//        );
        return user;
    }

    public User getUserById(int id) {
        return userRepository.findById(id);
    }


}
