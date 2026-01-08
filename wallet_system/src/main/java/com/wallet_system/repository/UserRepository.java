package com.wallet_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.wallet_system.dao.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserId(String userId);
    
}
