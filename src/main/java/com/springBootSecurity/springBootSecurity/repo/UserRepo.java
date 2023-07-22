package com.springBootSecurity.springBootSecurity.repo;

import com.springBootSecurity.springBootSecurity.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<AppUser, Integer> {
    Optional<AppUser> findByUsername(String username);
}
