package com.springboot.Config;

import com.springboot.Model.User.Users;
import com.springboot.Repository.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class CustomEmployeeService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Cacheable(value = "public", key = "#email")
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users users = userRepository.findByUserId(email);
        if(ObjectUtils.isEmpty(users)){
            throw new UsernameNotFoundException("Employee with email: "+ email + " not found");
        }
        return new CustomeEmployee(users);
    }
}
