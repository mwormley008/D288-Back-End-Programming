package com.assessment.demo.services;

import com.assessment.demo.entities.Cart;
import com.assessment.demo.entities.CartItem;
import com.assessment.demo.entities.Customer;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class Purchase {

    private Customer customer;
    private Cart cart;
    private Set<CartItem> cartItems;

}
