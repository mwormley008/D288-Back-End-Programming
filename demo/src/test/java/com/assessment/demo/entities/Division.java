package com.assessment.demo.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "divisions")
@Getter
@Setter
@NoArgsConstructor
public class Division {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "division_id")
    private Long id;

    @Column(name = "division", nullable = false)
    private String divisionName;

    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;

    @Column(name = "last_update", nullable = false)
    private LocalDateTime lastUpdate;

    // FK to COUNTRY table
    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    // One division has many customers
    @OneToMany(mappedBy = "division")
    private Set<Customer> customers;

    // Required by D288 instructions
    public void setCountry(Country country) {
        this.country = country;
    }
}
