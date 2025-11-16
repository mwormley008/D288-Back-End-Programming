package com.assessment.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "vacations")
@Getter
@Setter
@NoArgsConstructor
public class Vacation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vacation_id")
    private Long id;

    @Column(name = "vacation_title", nullable = false, length = 255)
    private String vacationTitle;


    @Column(name = "description")
    private String description;

    @Column(name = "travel_fare_price", precision = 10, scale = 2)
    private BigDecimal travelPrice;

    @Column(name = "image_url")
    private String image_url;

    @CreationTimestamp
    @Column(name = "create_date", updatable = false)
    private LocalDateTime createDate;

    @UpdateTimestamp
    @Column(name = "last_update")
    private LocalDateTime lastUpdate;

//    @ManyToOne
//    @JoinColumn(name = "division_id", nullable = false)
//    private Division division;

    @OneToMany(mappedBy = "vacation", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonIgnore
    private Set<Excursion> excursions = new HashSet<>();
}
