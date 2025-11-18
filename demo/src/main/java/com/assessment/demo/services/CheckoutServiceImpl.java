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

        if (purchaseItems == null || purchaseItems.isEmpty()) {
            return new PurchaseResponse("ERROR: There are no excursions in your cart. Please add at least one item before checking out.");
        }

        // Load default customer
        Long defaultCustomerId = 1L;
        Customer defaultCustomer = customerRepository.findById(defaultCustomerId)
                .orElseThrow(() -> new IllegalStateException("Customer with ID 1 not found"));

        // Create a new cart
        Cart newCart = new Cart();
        newCart.setStatus(StatusType.ordered);

        // 👇 *** FIX: copy package_price coming from Angular ***
        if (purchase.getCart() != null) {
            newCart.setPackage_price(purchase.getCart().getPackage_price());
            newCart.setParty_size(purchase.getCart().getParty_size());
        }

        // Attach items to cart
        for (CartItem item : purchaseItems) {
            newCart.add(item);
        }

        // Generate tracking number
        String trackingNumber = generateOrderTrackingNumber();
        newCart.setOrderTrackingNumber(trackingNumber);

        // Link cart to customer
        defaultCustomer.add(newCart);

        // Save everything
        customerRepository.save(defaultCustomer);

        return new PurchaseResponse(trackingNumber);
    }

    private String generateOrderTrackingNumber() {
        return UUID.randomUUID().toString();
    }
}
