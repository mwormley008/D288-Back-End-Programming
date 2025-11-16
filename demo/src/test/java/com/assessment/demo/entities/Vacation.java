package com.assessment.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Column(name = "vacation_name", nullable = false, length = 255)
    private String vacationName;

    @ManyToOne
    @JoinColumn(name = "division_id", nullable = false)
    private Division division;

    @OneToMany(mappedBy = "vacation", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Excursion> excursions = new HashSet<>();

}
