package com.challenge.todo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.challenge.todo.model.Role;
import com.challenge.todo.model.User;
import com.challenge.todo.repository.RoleRepository;
import com.challenge.todo.repository.UserRepository;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Classe service utilizada como meio de campo com os repositories de usuários
 * Serve para proteger os dados por meio de manipulações dos mesmos nas consultas e inserções
 *
 * @author Carlos França
 * @version	1.0
*/

@Service
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User saveUser(User user) {
        
        Role userRole = roleRepository.findByRoleName("ADMIN");
        
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        System.out.println(user.getPassword());
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        user.setActive(true);
        
        return userRepository.save(user);
    }

}