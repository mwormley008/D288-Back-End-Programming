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

        // Get cart items from the purchase object
        Set<CartItem> purchaseItems = purchase.getCartItems();

        // Check if cart is empty or null
        if (purchaseItems == null || purchaseItems.isEmpty()) {
            return new PurchaseResponse("ERROR: There are no excursions in your cart. Please add at least one item before checking out.");
        }

        // Always use the default customer with ID = 1 (frontend isn't selecting a customer)
        Long defaultCustomerId = 1L;
        Customer defaultCustomer = customerRepository.findById(defaultCustomerId)
                .orElseThrow(() -> new IllegalStateException(
                        "Customer with ID 1 not found. Make sure the sample data is loaded properly."));

        // Create a new cart instance
        Cart newCart = new Cart();
        newCart.setStatus(StatusType.ordered);

        // Attach items to the new cart
        for (CartItem item : purchaseItems) {
            newCart.add(item);
        }

        // Generate tracking number
        String trackingNumber = generateOrderTrackingNumber();
        newCart.setOrderTrackingNumber(trackingNumber);

        // Associate the cart with the customer
        defaultCustomer.add(newCart);

        // Save the customer (cascade saves cart and items)
        customerRepository.save(defaultCustomer);

        // Return a successful response
        return new PurchaseResponse(trackingNumber);
    }

    private String generateOrderTrackingNumber() {
        return UUID.randomUUID().toString();
    }
}
