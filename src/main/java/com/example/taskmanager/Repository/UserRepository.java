package com.example.taskmanager.Repository;

import com.example.taskmanager.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public  interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    boolean existsByEmail(String email);



}
