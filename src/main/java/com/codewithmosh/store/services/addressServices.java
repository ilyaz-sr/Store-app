package com.codewithmosh.store.services;


import com.codewithmosh.store.dtos.AddressDto;
import com.codewithmosh.store.entities.Address;
import com.codewithmosh.store.mappers.AddressMapper;
import com.codewithmosh.store.repositories.AddressRepository;
import com.codewithmosh.store.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class addressServices {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final AddressMapper addressMapper;

    @Transactional
    public Address saveAddressFromDto(AddressDto dto) {
        Address address = addressMapper.fromAddressDto(dto);

        address.setUser(
                userRepository.findById(dto.getUser_id())
                        .orElseThrow(() -> new RuntimeException("User not found"))
        );

        return addressRepository.save(address);
    }
}
