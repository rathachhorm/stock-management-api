package com.innovisor.quickpos.address;


import com.innovisor.quickpos.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;

    public List<AddressResponse> findAddressByParentId(Long parentId) {
        return addressRepository.findByParentId(parentId)
                .stream()
                .map(AddressMapper::toResponse)
                .toList();
    }

    public DetailsAddressResponse findFullAddressByCode(String code, String language) {
        var address = addressRepository.findByCode(code);

        List<String> details = new ArrayList<>();

        details.add(populateAddress(address, language));
        if(address.getParentId() == 0) {
            return DetailsAddressResponse.builder().value(String.join(getAddressDelimiter(language), details)).build();
        }

        var addr1 = addressRepository
                .findById(address.getParentId())
                .orElseThrow(() -> new NotFoundException("Can not find address with Id %s".formatted(code)));
        details.add(populateAddress(addr1, language));
        if(addr1.getParentId() == 0) {
            return DetailsAddressResponse.builder().value(String.join(getAddressDelimiter(language), details)).build();
        }

        var addr2 = addressRepository
                .findById(addr1.getParentId())
                .orElseThrow(() -> new NotFoundException("Can not find address with Id %s".formatted(code)));
        details.add(populateAddress(addr2, language));
        if(addr2.getParentId() == 0) {
            return DetailsAddressResponse.builder().value(String.join(getAddressDelimiter(language), details)).build();
        }

        var addr3 = addressRepository
                .findById(addr2.getParentId())
                .orElseThrow(() -> new NotFoundException("Can not find address with Id %s".formatted(code)));
        details.add(populateAddress(addr3, language));

        return DetailsAddressResponse.builder().value(String.join(getAddressDelimiter(language), details)).build();
    }

    private String getAddressDelimiter(String language) {
        switch (language) {
            case "kh" : return " ";
            case "en" : return ", ";
        }
        return "";
    }

    private String populateAddress(Address address, String language) {
        switch (language) {
            case "kh": return getAddressTypeAsString(address.getKhmerName(), address.getType(), language);
            case "en": return getAddressTypeAsString(address.getEnglishName(), address.getType(), language);
        }
        return null;
    }

    private String getAddressTypeAsString(String name, AddressType type, String language) {

        switch (language) {
            case "kh" :
                if(name.equals("រាជធានីភ្នំពេញ")) return name;

                switch (type) {
                    case VILLAGE : return "ភូមិ%s".formatted(name);
                    case COMMUNE : return "ឃុំ%s".formatted(name);
                    case SANGKAT : return "សង្កាត់%s".formatted(name);
                    case DISTRICT : return "ស្រុក%s".formatted(name);
                    case KHAN : return "ខណ្ឌ%s".formatted(name);
                    case CITY : return "ក្រុង%s".formatted(name);
                    case PROVINCE : return "ខេត្ត%s".formatted(name);
                }
                break;
            case "en" :
                if(name.equals("Phnom Penh")) return name;

                switch (type) {
                    case VILLAGE : return "%s village".formatted(name);
                    case COMMUNE : return "%s commune".formatted(name);
                    case SANGKAT : return "sangkat %s".formatted(name);
                    case DISTRICT : return "%s district".formatted(name);
                    case KHAN : return "khan %s".formatted(name);
                    case CITY : return "%s city".formatted(name);
                    case PROVINCE : return "%s Province".formatted(name);
                }
                break;
            default: throw new NotFoundException("Language is not support");

        }
        return null;
    }

    public List<AddressResponse> findAddressByChildId(Long childId) {
        return addressRepository.findById(childId)
                .stream()
                .map(AddressMapper::toResponse)
                .toList();
    }
}
