package com.innovisor.quickpos.supplier;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierRequest {

    private String fullName;

    private String phone;

    private String AddressCode;
}
