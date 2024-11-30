package com.innovisor.quickpos.supplier;

import com.innovisor.quickpos.exception.NotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierService {
    private final SupplierRepository supplierRepository;

    private Supplier findById(Integer id){
        return supplierRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer not found with id: %d".formatted(id)));
    }

    public List<SupplierResponse> findAllSuppliers(
            @Positive Integer pages,
            @Positive Integer limit
    ) {
        Pageable pageable = PageRequest.of(pages, limit);
        return supplierRepository.findAll(pageable)
                .getContent()
                .stream()
                .map(SupplierMapper::toResponse)
                .toList();
    }

    public SupplierResponse findSupplierById(@Positive Integer id) {
        return SupplierMapper.toResponse(findById(id));
    }

    public List<SupplierResponse> findSupplierByName(String name) {
        return supplierRepository.findSupplierByFullNameContainsIgnoreCase(name)
                .stream()
                .map(SupplierMapper::toResponse)
                .toList();
    }

    public SupplierResponse createSupplier(@Valid SupplierRequest supplierRequest) {
        var supplier = supplierRepository.save(SupplierMapper.toEntity(supplierRequest));
        return SupplierMapper.toResponse(supplier);
    }

    public Boolean updateSupplierById(@Positive @NonNull Integer id, SupplierRequest supplierRequest) {
        var supplier = findById(id);

        SupplierMapper.toEntity(supplier, supplierRequest);

        supplierRepository.save(supplier);
        return true;
    }

    public void deleteSupplierById(@Positive @NonNull Integer id) {
        var supplier = findById(id);

        supplierRepository.delete(supplier);
    }
}
