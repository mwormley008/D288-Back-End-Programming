package com.assessment.demo.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "carts")
@Getter
@Setter
@NoArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    @Column(name = "order_tracking_number", nullable = false)
    private String orderTrackingNumber;

    @Column(name = "package_price", nullable = false)
    private BigDecimal packagePrice;

    @Column(name = "party_size", nullable = false)
    private int partySize;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusType status = StatusType.pending; // Default to pending

    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;

    @Column(name = "last_update", nullable = false)
    private LocalDateTime lastUpdate;

    // MANY carts belong to ONE customer
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    // ONE cart can contain MANY cartItems
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private Set<CartItem> cartItems = new HashSet<>();

    // Required for Task F — service logic
    public void add(CartItem cartItem) {
        if (cartItem != null) {
            if (cartItems == null) {
                cartItems = new HashSet<>();
            }
            cartItems.add(cartItem);
            cartItem.setCart(this);
        }
    }
}
