package com.assessment.demo.controllers;

import com.assessment.demo.services.Purchase;
import com.assessment.demo.services.PurchaseResponse;
import com.assessment.demo.services.CheckoutService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/checkout")
@CrossOrigin("http://localhost:4200") // Allow requests from Angular frontend
public class CheckoutController {

    private final CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    // POST endpoint to handle purchase requests from Angular
    @PostMapping("/purchase")
    public PurchaseResponse placeOrder(@RequestBody Purchase purchase) {
        // Call the service method to validate and process the purchase
        return checkoutService.placeOrder(purchase);
    }
}
