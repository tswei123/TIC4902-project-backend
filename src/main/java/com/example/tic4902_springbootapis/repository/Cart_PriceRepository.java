package com.example.tic4902_springbootapis.repository;

import com.example.tic4902_springbootapis.models.Cart_Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface Cart_PriceRepository extends JpaRepository<Cart_Price, Long> {
}