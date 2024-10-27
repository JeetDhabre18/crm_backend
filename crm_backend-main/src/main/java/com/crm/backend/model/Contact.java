package com.crm.backend.model;



import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity

@Table(name = "Contact")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @Enumerated(value = EnumType.STRING)
    private ContactType contactType;


    private  String other;


    @CreationTimestamp
    public Date createdOn;

    @UpdateTimestamp
    public Date updatedOn;





}

