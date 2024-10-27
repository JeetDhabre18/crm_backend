package com.crm.backend.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "User")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(unique = true)
    private String email;

    @Column(nullable = false )
    private String password;

    @Column(nullable = false, length = 50)
    private String phoneNo;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    private String authorities;

    @Enumerated(value = EnumType.STRING)
    private UserStatus userStatus;

    @Column(nullable = false)
    private int createdBy;


    @CreationTimestamp
    public Date createdOn;

    @UpdateTimestamp
    public Date updatedOn;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (authorities == null || authorities.isEmpty()) {
            return Collections.emptyList(); // Return an empty collection if authorities is null or empty
        }

        String[] auth = authorities.split(",");
        return Arrays.stream(auth)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }


    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

//    public User orElseThrow(Object salesRepresentativeNotFound) {
//    }
}
