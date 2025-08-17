package com.codewithmosh.store.controllers;

import com.codewithmosh.store.dtos.UserDto;
import com.codewithmosh.store.entities.Role;
import com.codewithmosh.store.entities.User;
import com.codewithmosh.store.mappers.UserMapper;
import com.codewithmosh.store.repositories.UserRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
@Tag(name = "User")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/getallusers")
    public List<User> getAllUsers() {

        return userRepository.findAll();
    }

    @PostMapping("/getuserbyid")
    public User getUserbyId(@RequestBody Map<String, Long> request) {
        Long id = request.get("id");
        return userRepository.findById(id).orElseThrow();
    }

    @PostMapping("/adduser")
    public ResponseEntity<?> addUser(@Valid @RequestBody UserDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())){
            return ResponseEntity.badRequest().body(Map.of("email", "email already exists"));
        }


        try {
            User newUser = userMapper.fromUserDto(userDto);
            newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
            newUser.setRole(newUser.getRole() != null ? newUser.getRole() : Role.USER);
            User savedUser = userRepository.save(newUser);
            UserDto savedUserDto = userMapper.userToUserDto(savedUser);

            return new ResponseEntity<>(savedUserDto, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/edituser")
    public ResponseEntity<UserDto> editUser(@RequestBody UserDto updates) {
        Long id = updates.getId();
        try {
            User wanteduser = userRepository.findById(id).orElseThrow();

            userMapper.updateUserDto(updates, wanteduser);

            User saveduser = userRepository.save(wanteduser);
            return ResponseEntity.ok(userMapper.userToUserDto(saveduser));

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/deleteuser")
    public ResponseEntity<String> deleteUser(@RequestBody Map<String, Long> request) {

        Long id = request.get("id");
        boolean isdeleted = userRepository.existsById(id);

        switch (isdeleted ? 1 : 0){
            case 1:
                userRepository.deleteById(id);
                return  ResponseEntity.ok("User with ID " + id + " deleted successfully.");
            case 0:
            default:
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

//        if(userRepository.findById(id).isPresent()) {
//            userRepository.deleteById(id);
//            return ResponseEntity.ok("User with ID " + id + " deleted successfully.");
//        }else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
    }
}