package com.codewithmosh.store.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mapstruct.Mapping;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    private String zip;
    private String address;
    private Long user_id;
//
//        public AddressDto(String zip, String address, Long user_id) {
//        this.zip = zip;
//        this.address = address;
//        this.user_id = user_id;
//    }
}
