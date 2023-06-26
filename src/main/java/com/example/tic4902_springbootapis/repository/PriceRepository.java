package com.example.tic4902_springbootapis.repository;

import com.example.tic4902_springbootapis.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.tic4902_springbootapis.models.Price;

import java.util.List;
@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {
    List<Price> findByServiceidAndDays(Long serviceid, Integer days);
}
