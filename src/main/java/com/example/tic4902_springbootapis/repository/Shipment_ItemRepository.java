package com.example.tic4902_springbootapis.repository;

import com.example.tic4902_springbootapis.models.Shipment_Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface Shipment_ItemRepository extends JpaRepository<Shipment_Item, Long> {
}