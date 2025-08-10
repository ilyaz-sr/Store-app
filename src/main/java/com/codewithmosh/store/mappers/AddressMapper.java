package com.codewithmosh.store.mappers;

import com.codewithmosh.store.dtos.AddressDto;
import com.codewithmosh.store.entities.Address;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    @Mapping(source = "user.id", target = "user_id")
    AddressDto addtoAddressDto(Address address);

    @Mapping(source = "user_id", target = "user.id")
    @Mapping(target = "id", ignore = true)
    Address fromAddressDto(AddressDto addressDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Address updateAddressDto(AddressDto addressDto, @MappingTarget Address entity);
}
