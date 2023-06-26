package com.example.tic4902_springbootapis.models;

import com.example.tic4902_springbootapis.models.Cart_Item;
import com.example.tic4902_springbootapis.models.Cart_Price;
import com.fasterxml.jackson.annotation.JsonFormat;
import net.bytebuddy.implementation.bind.MethodDelegationBinder;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Shipment_Service")
public class Shipment_Service {
    @NotNull
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    //@Column(unique = true)
    private String servicename;

    @NotBlank
    @Size(max = 300)
    private String servicedesc;

    @Id
    private String ordernumber;

    @NotNull
    private double deliveryprice;

    @NotNull
    private double returnprice;

    @NotNull
    private double depositprice;

    @NotNull
    private double totalprice;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String rentfrom;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String rentto;

    @NotNull
    private String email;

    @NotNull
    private String username;

    @NotNull
    private String address;

    @NotNull
    private int contactnumber;

    @NotNull
    private String shipmentstatus;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name="ordernumber", insertable=false, updatable=false, nullable = false)
    private Set<Shipment_Price> shipment_prices;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name="ordernumber", insertable=false, updatable=false, nullable = false)
    private Set<Shipment_Item> shipment_items;


    public Shipment_Service() {
    }

    public Shipment_Service(String servicename, String servicedesc, double deliveryprice, double returnprice, double depositprice) {
        this.servicename = servicename;
        this.servicedesc = servicedesc;
        this.deliveryprice = deliveryprice;
        this.returnprice = returnprice;
        this.depositprice = depositprice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServicename() {
        return servicename;
    }

    public void setServicename(String servicename) {
        this.servicename = servicename;
    }

    public String getServicedesc() {
        return servicedesc;
    }

    public void setServicedesc(String servicedesc) {
        this.servicedesc = servicedesc;
    }

    public double getDeliveryprice() {
        return deliveryprice;
    }

    public void setDeliveryprice(double deliveryprice) {
        this.deliveryprice = deliveryprice;
    }

    public double getReturnprice() {
        return returnprice;
    }

    public void setReturnprice(double returnprice) {
        this.returnprice = returnprice;
    }

    public double getDepositprice() {
        return depositprice;
    }

    public void setDepositprice(double depositprice) {
        this.depositprice = depositprice;
    }

    public double getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(double totalprice) {
        this.totalprice = totalprice;
    }

    public String getRentfrom() {
        return rentfrom;
    }

    public void setRentfrom(String rentfrom) {
        this.rentfrom = rentfrom;
    }

    public String getRentto() {
        return rentto;
    }

    public void setRentto(String rentto) {
        this.rentto = rentto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getContactnumber() {
        return contactnumber;
    }

    public void setContactnumber(int contactnumber) {
        this.contactnumber = contactnumber;
    }

    public String getShipmentstatus() {
        return shipmentstatus;
    }

    public void setShipmentstatus(String shipmentstatus) {
        this.shipmentstatus = shipmentstatus;
    }

    public Set<Shipment_Price> getShipment_prices() {
        return shipment_prices;
    }

    public void setShipment_prices(Set<Shipment_Price> shipment_prices) {
        this.shipment_prices = shipment_prices;
    }

    public Set<Shipment_Item> getShipment_items() {
        return shipment_items;
    }

    public void setShipment_items(Set<Shipment_Item> shipment_items) {
        this.shipment_items = shipment_items;
    }

    public String getOrdernumber() {
        return ordernumber;
    }

    public void setOrdernumber(String ordernumber) {
        this.ordernumber = ordernumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}