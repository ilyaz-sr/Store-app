package com.codewithmosh.store.controllers;

import com.codewithmosh.store.dtos.AddressDto;
import com.codewithmosh.store.entities.Address;
import com.codewithmosh.store.entities.User;
import com.codewithmosh.store.mappers.AddressMapper;
import com.codewithmosh.store.repositories.AddressRepository;
import com.codewithmosh.store.repositories.UserRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@AllArgsConstructor
@RequestMapping("/address")
@Tag(name = "Address")
public class AddressController {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final UserRepository userRepository;

    @PostMapping("/getalladdresses")
    public List<AddressDto> getalladdress(){

        return addressRepository.findAll().stream().map(addressMapper::addtoAddressDto).toList();
    }

    @PostMapping("/getaddressbyid")
    public AddressDto getAddress(@RequestBody Map<String, Long> request) {
        Long id = request.get("id");
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found for id: " + id));
        return addressMapper.addtoAddressDto(address);
    }

    @PostMapping("/setaddress")
    public ResponseEntity<AddressDto> setAddress(@Valid @RequestBody AddressDto addressDto) {
        try {
            Address newAddress = addressMapper.fromAddressDto(addressDto);

            User user = userRepository.findById(addressDto.getUser_id())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            newAddress.setUser(user);

            Address savedAddress = addressRepository.save(newAddress);

            AddressDto savedAddressDto = addressMapper.addtoAddressDto(savedAddress);

            return new ResponseEntity<>(savedAddressDto, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/editaddress")
    public ResponseEntity<AddressDto> editaddress(@RequestBody AddressDto updates) {
        Long id = updates.getId();
        try {

            Address wantedaddress = addressRepository.findById(id).orElseThrow();

            addressMapper.updateAddressDto(updates, wantedaddress);

            Address updatedAddress = addressRepository.save(wantedaddress);
            return ResponseEntity.ok(addressMapper.addtoAddressDto(updatedAddress));
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/deleteaddress")
    public ResponseEntity<String> deleteaddress(@RequestBody Map<String,Long> request) {
        Long id = request.get("id");
        boolean isdeleted = addressRepository.existsById(id);

        switch (isdeleted ? 1 : 0){
            case 1:
                addressRepository.deleteById(id);
                return  ResponseEntity.ok("Address with ID " + id + " deleted successfully.");
            case 0:
            default:
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
