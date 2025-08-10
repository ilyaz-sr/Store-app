package com.codewithmosh.store.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    @NotBlank(message = "firstName is Required")
    @Size(min = 3, max = 100, message = "firstname must be between 3-100 characters long")
    private String firstName;
    @NotBlank(message = "lasttName is Required")
    @Size(min = 3, max = 100, message = "lasttname must be between 3-100 characters long")
    private String lastName;
    @Email(message = "Email is not in the correct format")
    private String email;
    @NotBlank(message = "a password must be provided")
    @Size(min = 10, message = "password must be atleast 10 characters long")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String role;
//    public UserDto(String firstName, String lastName, String email) {
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.email = email;
//    }
}