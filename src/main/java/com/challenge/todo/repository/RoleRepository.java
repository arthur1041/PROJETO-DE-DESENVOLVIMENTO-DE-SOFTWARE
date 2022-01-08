package com.challenge.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.challenge.todo.model.Role;

/**
 * Classe repository que funciona como camada de acesso aos dados para os papeis
 *
 * @author Guilherme Câmara	
 * @version	1.0
*/

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    
    Role findByRoleName(String roleName);

}
