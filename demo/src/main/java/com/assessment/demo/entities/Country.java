package com.assessment.demo.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
@Table(name = "countries")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "country_id")
    private Long id;

    @Column(name = "country")
    private String country_name;  // Match DB/front-end naming conventions

    @Column(name = "create_date")
    @CreationTimestamp
    private Date create_date;  // Auto-populated on insertion

    @Column(name = "last_update")
    @UpdateTimestamp
    private Date last_update; // Auto-updated on save

    @OneToMany(mappedBy = "country")
    private Set<Division> divisions;

    public Country() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Country)) return false;
        Country country = (Country) o;
        return id != null && id.equals(country.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
