package cz.inqool.tennisapp.domain.customer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void findByPhoneNumberExists(){
        Customer customer = new Customer("tester", "123");
        customerRepository.save(customer);

        Optional<Customer> customerTest = customerRepository.findByPhoneNumber("123");

        assertTrue(customerTest.isPresent());
        Assertions.assertEquals("123", customerTest.get().getPhoneNumber());
    }

    @Test
    void findByPhoneNumberNotExists(){
        Customer customer = new Customer("tester", "123");
        customerRepository.save(customer);

        Optional<Customer> customerTest = customerRepository.findByPhoneNumber("456");

        assertFalse(customerTest.isPresent());
    }
}
