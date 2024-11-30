package com.innovisor.quickpos.address;

public class AddressMapper {
    public static AddressResponse toResponse(Address address) {
        return AddressResponse.builder()
                .id(address.getId())
                .type(address.getType())
                .code(address.getCode())
                .khmerName(address.getKhmerName())
                .englishName(address.getEnglishName())
                .parentId(address.getParentId())
                .build();
    }
}
