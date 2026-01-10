package com.wallet_system.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wallet_system.dao.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserId(String userId);
    
}
