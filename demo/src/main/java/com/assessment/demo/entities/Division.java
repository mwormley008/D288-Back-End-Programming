package com.assessment.demo.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

    @Column(name = "division", nullable = false) // Ensure this matches your DB column name
    private String divisionName;

    @CreationTimestamp
    @Column(name = "create_date", updatable = false)
    private LocalDateTime createDate;

    @UpdateTimestamp
    @Column(name = "last_update")
    private LocalDateTime lastUpdate;

    // Lazy fetch to avoid circular references; insert/update ignored for proper mapping
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", nullable = false, insertable = false, updatable = false)
    private Country country;

    // Standalone FK column to support JSON deserialization and manual setting
    @Column(name = "country_id")
    private Long countryId;

    // One division can have many customers
    @OneToMany(mappedBy = "division")
    private Set<Customer> customers;

    // Helper to set both the country and FK ID
    public void setCountry(Country country) {
        this.country = country;
        this.countryId = country.getId();
    }
}
