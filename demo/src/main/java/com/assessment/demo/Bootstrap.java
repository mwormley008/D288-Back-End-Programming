package com.assessment.demo;

import com.assessment.demo.dao.CustomerRepository;
import com.assessment.demo.dao.DivisionRepository;
import com.assessment.demo.entities.Customer;
import com.assessment.demo.entities.Division;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Bootstrap implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final DivisionRepository divisionRepository;

    @Override
    public void run(String... args) throws Exception {

        long customerCount = customerRepository.count();

        if (customerCount <= 1) {
            log.info("Bootstrapping sample customers...");

            Division johnDiv = divisionRepository.findById(4L).orElse(null);
            Division emilyDiv = divisionRepository.findById(38L).orElse(null);
            Division arthurDiv = divisionRepository.findById(101L).orElse(null);
            Division claraDiv = divisionRepository.findById(46L).orElse(null);
            Division milesDiv = divisionRepository.findById(102L).orElse(null);

            customerRepository.save(new Customer("John", "Baker", "715 Elm Street", "53703", "(608)555-1198", johnDiv));
            customerRepository.save(new Customer("Emily", "Lawson", "94 Oceanview Drive", "94109", "(415)555-2930", emilyDiv));
            customerRepository.save(new Customer("Arthur", "Kipling", "11 Bedford Row", "WC1R 4BU", "+44 20 7946 0420", arthurDiv));
            customerRepository.save(new Customer("Clara", "Novak", "18 Cedar Lane", "10025", "(212)555-8389", claraDiv));
            customerRepository.save(new Customer("Miles", "Ramirez", "49 Sunset Blvd", "90028", "(323)555-4812", milesDiv));

            log.info("Sample customers added successfully.");
        } else {
            log.info("Customers already exist. Skipping bootstrap.");
        }

        log.info("Total customers in DB: {}", customerRepository.count());
    }
}
