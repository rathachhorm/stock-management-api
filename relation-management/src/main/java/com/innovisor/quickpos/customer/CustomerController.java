package com.innovisor.quickpos.customer;

import com.innovisor.quickpos.response.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("customers")
@RestController
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService service;

    @GetMapping
    public ResponseEntity<?> getAllCustomers(
            @RequestParam(name = "page", defaultValue = "1") @Positive Integer pages,
            @RequestParam(name = "limit", defaultValue = "10") @Positive Integer limit
    ){
        return ApiResponse.body(
                HttpStatus.OK,
                "Get All customers",
                service.findAllCustomers(pages - 1, limit)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable(name = "id") @Positive Integer id){
        return ApiResponse.body(
                HttpStatus.OK,
                "Get customer with Id: %d".formatted(id),
                service.findCustomerById(id)
        );
    }

    @GetMapping("/filter")
    public ResponseEntity<?> getCustomerByName(
            @RequestParam("name") String name
    ){
        return ApiResponse.body(
                HttpStatus.OK,
                "Filter customer with name: %s".formatted(name),
                service.findCustomerByName(name));
    }

    @PostMapping
    public ResponseEntity<?> createCustomer(@RequestBody @Valid CustomerRequest customerRequest) {
        return ApiResponse.body(
                HttpStatus.CREATED,
                "Created customer successfully",
                service.createCustomer(customerRequest)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomerById(
            @PathVariable(name = "id") @Positive @NonNull Integer id,
            @RequestBody @Valid CustomerRequest customerRequest
    ) {
        return ApiResponse.body(
                HttpStatus.NO_CONTENT,
                "Updated customer with Id: %d successfully".formatted(id),
                service.updateCustomerById(id, customerRequest)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomerById(@PathVariable(name = "id") @Positive @NonNull Integer id) {
        service.deleteCustomerById(id);
        return ApiResponse.body(
                HttpStatus.NO_CONTENT,
                "Deleted customer with Id: %d successfully".formatted(id),
                null);
    }

}
