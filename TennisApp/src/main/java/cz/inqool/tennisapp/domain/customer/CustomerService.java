package cz.inqool.tennisapp.domain.customer;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Optional<Customer> findCustomerByPhoneNumber(String phoneNumber) {
        return customerRepository.findByPhoneNumber(phoneNumber);
    }

    public Customer createCustomer(String name, String phoneNumber) {
        Customer customer = new Customer();
        customer.setName(name);
        customer.setPhoneNumber(phoneNumber);
        return customerRepository.save(customer);
    }

    public Customer findOrCreateCustomer(String name, String phoneNumber) {
        return findCustomerByPhoneNumber(phoneNumber)
                .orElseGet(() -> createCustomer(name, phoneNumber));
    }
}
