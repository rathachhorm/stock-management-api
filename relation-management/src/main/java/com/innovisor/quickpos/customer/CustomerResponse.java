package com.innovisor.quickpos.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {
    private Integer id;
    private String fullName;
    private CustomerType type;
    private String phone;
    private String AddressCode;
    private CustomerStatus status;
    private String remark;
}
