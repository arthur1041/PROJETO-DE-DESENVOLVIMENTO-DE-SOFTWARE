package com.challenge.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.challenge.todo.model.User;


/**
 * Classe repository que funciona como camada de acesso aos dados para usuários
 *
 * @author Carlos França
 * @version	1.0
*/
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    User findByEmail(String email);
    
}