package com.codewithmosh.store.services;

import com.codewithmosh.store.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.Collections;

@Service
//@RequiredArgsConstructor
@AllArgsConstructor
public class userServices implements UserDetailsService {

    private final UserRepository userRepository;

//    @Transactional
//    public User addUser(User user) {
//        return userRepository.save(user);
//    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       var user =  userRepository.findByEmail(email).orElseThrow((
        ) -> new UsernameNotFoundException("username not found"));

       return new User(
               user.getEmail(),
               user.getPassword(),
               Collections.emptyList()
       );
    }
}