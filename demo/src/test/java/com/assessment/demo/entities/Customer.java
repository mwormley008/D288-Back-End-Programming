package com.assessment.demo.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;

    // Matches Angular model: firstName
    @Column(name = "first_name", nullable = false)
    private String firstName;

    // Matches Angular model: lastName
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "address", nullable = false)
    private String address;

    // Angular model uses postalCode
    @Column(name = "postal_code", nullable = false)
    private String postalCode;

    @Column(name = "phone", nullable = false)
    private String phone;

    // In DB but NOT in Angular model
    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;

    @Column(name = "last_update", nullable = false)
    private LocalDateTime lastUpdate;

    // Many customers belong to one division
    @ManyToOne
    @JoinColumn(name = "division_id", nullable = false)
    private Division division;

    // One customer can have many carts
    @OneToMany(mappedBy = "customer")
    private Set<Cart> carts;

}
