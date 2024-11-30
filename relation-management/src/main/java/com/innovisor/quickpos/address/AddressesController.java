package com.innovisor.quickpos.address;

import com.innovisor.quickpos.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("addresses")
@RequiredArgsConstructor
public class AddressesController {
    private final AddressService addressService;

    @GetMapping("/parent/{child_id}")
    public ResponseEntity<?> getAddressesByChildId(@PathVariable(name = "child_id") Long childId) {
        return ApiResponse.body(
                HttpStatus.OK,
                "Get address with name: %d".formatted(childId),
                addressService.findAddressByChildId(childId)
        );
    }

    @GetMapping("/children/{parent_id}")
    public ResponseEntity<?> getAddressesByParentId(@PathVariable(name = "parent_id") Long parentId) {
        return ApiResponse.body(
                HttpStatus.OK,
                "Get address with name: %d".formatted(parentId),
                addressService.findAddressByParentId(parentId)
        );
    }

    @GetMapping("/details")
    public ResponseEntity<?> getFullAddressByCode(
            @RequestParam(name = "code") String code,
            @RequestParam(name = "language") String language
    ) {
        return ApiResponse.body(
                HttpStatus.OK,
                "Get address with code: %s, language: %s".formatted(code, language),
                addressService.findFullAddressByCode(code, language)
        );
    }

}
