package com.example.tic4902_springbootapis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.tic4902_springbootapis.models.Item;
import java.util.List;
@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByServiceidAndItemname(Long serviceid, String itemname);
}
