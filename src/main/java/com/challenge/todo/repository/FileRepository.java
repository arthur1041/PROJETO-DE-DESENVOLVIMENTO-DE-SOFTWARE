package com.challenge.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.challenge.todo.model.File;

@Repository
public interface FileRepository extends JpaRepository<File, Integer> {
    

}
