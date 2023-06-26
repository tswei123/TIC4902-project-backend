package com.example.tic4902_springbootapis.repository;

import com.example.tic4902_springbootapis.models.Cart_Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Cart_ItemRepository extends JpaRepository<Cart_Item, Long>{
}
