package com.crm.backend.model;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "Customer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

//    @Column(nullable = false)
//    private int createdBy;

    @Column(nullable = false, length = 15) // Changed to String for phone number
    private String phoneNo;

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;


    @Column(nullable = false)
    private int assignedTo;


    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;




    @CreationTimestamp
    private Date createdOn;

    @UpdateTimestamp
    private Date updatedOn;
}
