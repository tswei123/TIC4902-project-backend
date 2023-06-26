package com.example.tic4902_springbootapis.repository;

import com.example.tic4902_springbootapis.models.Cart_Service;
import com.example.tic4902_springbootapis.models.Shipment_Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface Shipment_ServiceRepository extends JpaRepository<Shipment_Service, Long> {

    @Query(value = "SELECT * from Shipment_Service where email = :emailaddress" , nativeQuery = true)
    List<Shipment_Service> findIndividualShipment(@Param("emailaddress") String emailaddress);

    @Query(value = "SELECT * from Shipment_Service where ordernumber = :ordernumber" , nativeQuery = true)
    List<Shipment_Service> getShipmentDetail(String ordernumber);
}

