package com.assessment.demo.services;

import com.assessment.demo.dao.CartItemRepository;
import com.assessment.demo.dao.CartRepository;
import com.assessment.demo.dao.CustomerRepository;
import com.assessment.demo.entities.Cart;
import com.assessment.demo.entities.CartItem;
import com.assessment.demo.entities.Customer;
import com.assessment.demo.entities.StatusType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    private final CustomerRepository customerRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public CheckoutServiceImpl(CustomerRepository customerRepository,
                               CartRepository cartRepository,
                               CartItemRepository cartItemRepository) {
        this.customerRepository = customerRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {

        // get the cart data from the purchase object
        Cart cart = purchase.getCart();

        // validation: make sure cart exists and has at least one item
        if (cart == null || cart.getCartItems() == null || cart.getCartItems().isEmpty()) {
            return new PurchaseResponse("Cart is empty. Please add items before checking out.");
        }

        // generate an order tracking number
        String orderTrackingNumber = generateOrderTrackingNumber();
        cart.setOrderTrackingNumber(orderTrackingNumber);

        // connect each cart item to the cart
        Set<CartItem> cartItems = purchase.getCartItems();
        cartItems.forEach(item -> item.setCart(cart));
        cartItems.forEach(cart::add);

        // update the cart status and save it to the db
        cart.setStatus(StatusType.ordered);
        cartRepository.save(cart);

        // attach the cart to the customer
        Customer customer = purchase.getCustomer();
        customer.add(cart);

        // sometimes this line can prevent the tracking number from showing in the UI
        // customerRepository.save(customer);

        // return the tracking number
        return new PurchaseResponse(orderTrackingNumber);
    }

    private String generateOrderTrackingNumber() {
        // generate a random UUID (version 4)
        return UUID.randomUUID().toString();
    }
}
