package com.example.mainserivice.repository;

import com.example.mainserivice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

}



