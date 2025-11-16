package com.assessment.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "excursions")
@Getter
@Setter
@NoArgsConstructor
public class Excursion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "excursion_id")
    private Long id;

    @Column(name = "excursion_name", nullable = false, length = 255)
    private String excursionName;

    @Column(name = "excursion_description", columnDefinition = "TEXT")
    private String excursionDescription;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "unit_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "available_quantity", nullable = false)
    private int availableQuantity;

    @ManyToOne
    @JoinColumn(name = "vacation_id", nullable = false)
    private Vacation vacation;

    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;

    @Column(name = "last_update", nullable = false)
    private LocalDateTime lastUpdate;

    @OneToMany(mappedBy = "excursion", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<CartItem> cartItems = new HashSet<>();

}
