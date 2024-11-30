package com.innovisor.quickpos.supplier;

public class SupplierMapper {
    public static SupplierResponse toResponse(Supplier supplier){
        return new SupplierResponse(
                supplier.getId(),
                supplier.getFullName(),
                supplier.getPhone(),
                supplier.getAddressCode()
        );
    }

    public static Supplier toEntity(SupplierRequest supplierRequest) {
        return Supplier.builder()
                .fullName(supplierRequest.getFullName())
                .phone(supplierRequest.getPhone())
                .addressCode(supplierRequest.getAddressCode())
                .build();
    }

    public static Supplier toEntity(Supplier supplier, SupplierRequest supplierRequest) {

        if(supplierRequest.getFullName() != null)
            supplier.setFullName(supplierRequest.getFullName());
        if(supplierRequest.getPhone() != null)
            supplier.setPhone(supplierRequest.getPhone());
        if(supplierRequest.getAddressCode() != null)
            supplier.setAddressCode(supplierRequest.getAddressCode());

        return supplier;
    }
}
