package com.innovisor.quickpos.customer;

import com.innovisor.quickpos.exception.NotFoundException;
import jakarta.validation.constraints.Positive;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    private Customer findById(Integer id){
        return customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer not found with id: %d".formatted(id)));
    }

    public List<CustomerResponse> findAllCustomers(Integer pages, Integer limit) {
        Pageable pageable = PageRequest.of(pages, limit);
        return customerRepository.findAll(pageable)
                .getContent()
                .stream()
                .map(CustomerMapper::toResponse)
                .toList();
    }

    public CustomerResponse findCustomerById(Integer id) {
        return CustomerMapper.toResponse(findById(id));
    }

    public List<CustomerResponse> findCustomerByName(String name) {
        return customerRepository.findByFullNameContainsIgnoreCase(name)
                .stream()
                .map(CustomerMapper::toResponse)
                .toList();
    }

    public CustomerResponse createCustomer(CustomerRequest customerRequest) {
        var customer = customerRepository.save(CustomerMapper.toEntity(customerRequest));
        return CustomerMapper.toResponse(customer);
    }

    public Boolean updateCustomerById(Integer id, CustomerRequest customerRequest) {
        var customer = findById(id);

        CustomerMapper.toEntity(customer, customerRequest);

        customerRepository.save(customer);
        return true;
    }

    public void deleteCustomerById(@Positive @NonNull Integer id) {
        var customer = findById(id);

        customerRepository.delete(customer);
    }
}
