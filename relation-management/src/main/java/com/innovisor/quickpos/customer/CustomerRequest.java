package com.innovisor.quickpos.customer;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequest {
    @Column(name = "fullname", length = 100)
    private String fullName;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 10)
    private CustomerType type;

    @Column(name = "phone", length = 25)
    private String phone;

    @Column(name = "address_code", length = 8)
    private String addressCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 10)
    private CustomerStatus status;

    private String remark;
}
