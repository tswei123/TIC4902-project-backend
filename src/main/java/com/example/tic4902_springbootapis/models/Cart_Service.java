package com.example.tic4902_springbootapis.models;

import com.example.tic4902_springbootapis.models.Cart_Item;
import com.example.tic4902_springbootapis.models.Cart_Price;
import com.fasterxml.jackson.annotation.JsonFormat;
import net.bytebuddy.implementation.bind.MethodDelegationBinder;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Cart_Service")
public class Cart_Service {

    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
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
    private String orderstatus;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name="ordernumber", insertable=false, updatable=false, nullable = false)
    private Set<Cart_Price> cart_prices;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name="ordernumber", insertable=false, updatable=false, nullable = false)
    private Set<Cart_Item> cart_items;


    public Cart_Service() {
    }

    public Cart_Service(String servicename, String servicedesc, double deliveryprice, double returnprice, double depositprice) {
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

    public String getOrdernumber() {
        return ordernumber;
    }

    public void setOrdernumber(String ordernumber) {
        this.ordernumber = ordernumber;
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

    public String getOrderstatus() {
        return orderstatus;
    }

    public void setOrderstatus(String orderstatus) {
        this.orderstatus = orderstatus;
    }

    public Set<Cart_Price> getCart_prices() {
        return cart_prices;
    }

    public void setCart_prices(Set<Cart_Price> cart_prices) {
        this.cart_prices = cart_prices;
    }

    public Set<Cart_Item> getCart_items() {
        return cart_items;
    }

    public void setCart_items(Set<Cart_Item> cart_items) {
        this.cart_items = cart_items;
    }
}