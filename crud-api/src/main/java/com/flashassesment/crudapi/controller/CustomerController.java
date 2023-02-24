package com.flashassesment.crudapi.controller;

import com.flashassesment.crudapi.exception.ResourceNotFoundException;
import com.flashassesment.crudapi.model.Customer;
import com.flashassesment.crudapi.repository.CustomerRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {
    private CustomerRepository customerRepository;

    @GetMapping("/customers")
    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable(value = "id") Integer customerId) throws ResourceNotFoundException {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer with ID" + customerId + " not found"));
        return ResponseEntity.ok(customer);
    }

    @PostMapping("/customers")
    public Customer createNewCustomer(@Valid @RequestBody Customer customer){
        return customerRepository.save(customer);
    }

    @DeleteMapping("/customers/{id}")
    public void deleteCustomerById (@PathVariable Integer customerId)throws ResourceNotFoundException {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer with ID" + customerId + " not found"));
        customerRepository.delete(customer);
    }

    @PutMapping("customers/{id}")
    public ResponseEntity<Customer> updateCustomerDetails(@PathVariable (value = "id") Integer customerId, @PathVariable @RequestBody Customer customerDetails)
            throws ResourceNotFoundException {
                Customer customer = customerRepository.findById(customerId)
                        .orElseThrow(() -> new ResourceNotFoundException("Customer with ID" + customerId + " not found"));

                customer.setBirthDate(customerDetails.getBirthDate());
                customer.setGender(customerDetails.getGender());
                customer.setEmail(customerDetails.getEmail());
                customer.setName(customerDetails.getName());

                final Customer updatedCustomer = customerRepository.save(customer);
                return ResponseEntity.ok(updatedCustomer);
    }

}
