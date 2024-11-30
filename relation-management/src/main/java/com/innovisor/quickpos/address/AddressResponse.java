package com.innovisor.quickpos.address;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponse {
    private Long id;

    private AddressType type;

    private String code;

    private String khmerName;

    private String englishName;

    private Long parentId;
}
