package com.innovisor.quickpos.customer;

public final class CustomerMapper {
    public static CustomerResponse toResponse(Customer customer){
        return new CustomerResponse(
            customer.getId(),
            customer.getFullName(),
            customer.getType(),
            customer.getPhone(),
            customer.getAddressCode(),
            customer.getStatus(),
            customer.getRemark()
        );
    }


    public static Customer toEntity(CustomerRequest customerRequest) {
        return Customer.builder()
                .fullName(customerRequest.getFullName())
                .type(customerRequest.getType())
                .phone(customerRequest.getPhone())
                .addressCode(customerRequest.getAddressCode())
                .status(customerRequest.getStatus())
                .remark(customerRequest.getRemark())
                .build();
    }

    public static Customer toEntity(Customer customer, CustomerRequest customerRequest) {

        if(customerRequest.getFullName() != null)
            customer.setFullName(customerRequest.getFullName());
        if(customerRequest.getType() != null)
            customer.setType(customerRequest.getType());
        if(customerRequest.getPhone() != null)
            customer.setPhone(customerRequest.getPhone());
        if(customerRequest.getAddressCode() != null)
            customer.setAddressCode(customerRequest.getAddressCode());
        if(customerRequest.getStatus() != null)
            customerRequest.setStatus(customerRequest.getStatus());

        customerRequest.setRemark(customerRequest.getRemark());

        return customer;
    }
}
