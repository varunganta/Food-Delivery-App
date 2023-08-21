package com.example.demo.customer;

import com.example.demo.appuser.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
@CrossOrigin("http://localhost:3000")
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<AppUser>> getAllCustomers() {
        List<AppUser> customers = customerService.getCustomers();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<AppUser> getCustomerById(@PathVariable("id") Long id) {
        AppUser customer = customerService.getCustomerById(id);
        if (customer != null) {
            return ResponseEntity.ok(customer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AppUser> updateCustomer(@PathVariable Long id, @RequestBody AppUser updatedCustomer) {
        AppUser updated = customerService.updateCustomer(id, updatedCustomer);

        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
