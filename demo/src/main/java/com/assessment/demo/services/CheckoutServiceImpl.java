package com.assessment.demo.services;

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

    public CheckoutServiceImpl(CustomerRepository customerRepository,
                               CartRepository cartRepository) {
        this.customerRepository = customerRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {

        // Get cart items from purchase object
        Set<CartItem> purchaseItems = purchase.getCartItems();

        // Check if cart is empty or null
        if (purchaseItems == null || purchaseItems.isEmpty()) {
            return new PurchaseResponse("ERROR: There are no vacations in your cart. Please add at least one item before checking out.");
        }

        // Create a new cart instance (ignore incoming cart from Purchase)
        Cart newCart = new Cart();
        newCart.setStatus(StatusType.ordered);

        // Generate tracking number
        String orderTrackingNumber = generateOrderTrackingNumber();
        newCart.setOrderTrackingNumber(orderTrackingNumber);

        // Attach items to the new cart
        for (CartItem item : purchaseItems) {
            newCart.add(item);
        }

        // Associate the cart to the customer
        Customer customer = purchase.getCustomer();
        customer.add(newCart);

        // Save the customer (cascade will save the cart and items)
        customerRepository.save(customer);

        return new PurchaseResponse(orderTrackingNumber);
    }

    private String generateOrderTrackingNumber() {
        return UUID.randomUUID().toString();
    }
}
