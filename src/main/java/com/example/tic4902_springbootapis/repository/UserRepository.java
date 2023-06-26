package com.example.tic4902_springbootapis.repository;

import java.util.List;
import java.util.Optional;

import com.example.tic4902_springbootapis.models.Cart_Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.tic4902_springbootapis.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    @Query(value = "SELECT * from users where email = :emailaddress" , nativeQuery = true)
    List<User> findIndividualUser(@Param("emailaddress") String emailaddress);
}

