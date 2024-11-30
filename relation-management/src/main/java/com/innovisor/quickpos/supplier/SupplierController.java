package com.innovisor.quickpos.supplier;

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

@RequestMapping("suppliers")
@RestController
@RequiredArgsConstructor
public class SupplierController {
    private final SupplierService supplierService;

    @GetMapping
    public ResponseEntity<?> getAllSuppliers(
            @RequestParam(name = "page", defaultValue = "1") @Positive Integer pages,
            @RequestParam(name = "limit", defaultValue = "10") @Positive Integer limit
    ){
        return ApiResponse.body(
                HttpStatus.OK,
                "Get All Suppliers",
                supplierService.findAllSuppliers(pages - 1,  limit)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSupplierById(@PathVariable(name = "id") @Positive Integer id){
        return ApiResponse.body(
                HttpStatus.OK,
                "Get supplier with Id: %d successfully".formatted(id),
                supplierService.findSupplierById(id)
        );
    }

    @GetMapping("/filter")
    public ResponseEntity<?> getSupplierByName(
            @RequestParam("name") String name
    ){
        return ApiResponse.body(
                HttpStatus.OK,
                "Filter supplier with name: %s".formatted(name),
                supplierService.findSupplierByName(name)
        );
    }

    @PostMapping
    public ResponseEntity<?> createSupplier(@RequestBody @Valid SupplierRequest supplierRequest) {
        return ApiResponse.body(
                HttpStatus.CREATED,
                "Created supplier successfully",
                supplierService.createSupplier(supplierRequest)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSupplierById(
            @PathVariable(name = "id") @Positive @NonNull Integer id,
            @RequestBody @Valid SupplierRequest supplierRequest
    ) {
        return ApiResponse.body(
                HttpStatus.NO_CONTENT,
                "Updated supplier with Id: %d successfully".formatted(id),
                supplierService.updateSupplierById(id, supplierRequest)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSupplierById(@PathVariable(name = "id") @Positive @NonNull Integer id) {
        supplierService.deleteSupplierById(id);
        return ApiResponse.body(
                HttpStatus.NO_CONTENT,
                "Deleted supplier with Id: %d successfully".formatted(id),
                null);
    }
}
