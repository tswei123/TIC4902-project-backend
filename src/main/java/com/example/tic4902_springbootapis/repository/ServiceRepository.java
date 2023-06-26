package com.example.tic4902_springbootapis.repository;

import com.example.tic4902_springbootapis.models.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.tic4902_springbootapis.models.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {
    //@Query(value = "SELECT * from Service where id = :id" , nativeQuery = true)
    //List<Service> findIndividualService(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("delete from Item where serviceid = :serviceid")
    void deleteItem(@Param("serviceid") Long serviceid);

    @Modifying
    @Transactional
    @Query("delete from Price where serviceid = :serviceid")
    void deletePrice(@Param("serviceid") Long serviceid);
}
