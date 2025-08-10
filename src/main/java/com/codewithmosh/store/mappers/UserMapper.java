package com.codewithmosh.store.mappers;

import com.codewithmosh.store.dtos.UserDto;
import com.codewithmosh.store.entities.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {
//    @Mapping(target = "email",ignore = true)
    UserDto userToUserDto(User user);

    @Mapping(target = "id", ignore = true)
    User fromUserDto(UserDto userDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User updateUserDto(UserDto userDto, @MappingTarget User entity);
}