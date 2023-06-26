package com.example.tic4902_springbootapis.repository;

import com.example.tic4902_springbootapis.models.Cart_Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.tic4902_springbootapis.models.Cart_Service;
import com.example.tic4902_springbootapis.models.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface Cart_ServiceRepository extends JpaRepository<Cart_Service, Long> {
    @Modifying
    @Transactional
    @Query("delete from Cart_Item where ordernumber = :ordernumber")
    void deleteCart(@Param("ordernumber") String ordernumber);

    @Modifying
    @Transactional
    @Query("delete from Cart_Price where ordernumber = :ordernumber")
    void deletePrice(@Param("ordernumber") String ordernumber);
    @Modifying
    @Transactional
    @Query("delete from Cart_Service where ordernumber = :ordernumber")
    void deleteCart_Service(@Param("ordernumber") String ordernumber);

    @Query(value = "SELECT * from Cart_Service where ordernumber = :ordernumber" , nativeQuery = true)
    List<Cart_Service> findCart_Service(String ordernumber);

    @Query(value = "select max(ordernumber) from Cart_Service", nativeQuery = true)
    String  findMaxOrdernumber();

    @Query(value = "SELECT * from Cart_Service where email = :emailaddress and orderstatus = 'pending payment'", nativeQuery = true)
    List<Cart_Service> findIndividualCart(@Param("emailaddress") String emailaddress);

    @Query(value = "SELECT username from users where email = :emailaddress", nativeQuery = true)
    String findUserName(@Param("emailaddress") String emailaddress);

}


