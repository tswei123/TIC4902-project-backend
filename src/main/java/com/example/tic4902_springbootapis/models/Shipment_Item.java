package com.example.tic4902_springbootapis.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Shipment_Item")
public class Shipment_Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    //@Column(unique = true)
    private String itemname;

    @NotNull
    private double price;

    @NotNull
    private Long serviceid;

    //@ManyToOne
    //@JoinColumn(name="serviceid", nullable = false, insertable = false, updatable = false)
    //private Cart_Service cart_service;

    @NotNull
    private String ordernumber;

    public Shipment_Item() {
    }

    public Shipment_Item(Long serviceid, String itemname, double price) {
        this.serviceid = serviceid;
        this.itemname = itemname;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getServiceid() {
        return serviceid;
    }

    public void setServiceid(Long serviceid) {
        this.serviceid = serviceid;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getOrdernumber() {
        return ordernumber;
    }

    public void setOrdernumber(String ordernumber) {
        this.ordernumber = ordernumber;
    }
}

