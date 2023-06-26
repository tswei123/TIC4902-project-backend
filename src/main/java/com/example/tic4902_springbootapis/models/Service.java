package com.example.tic4902_springbootapis.models;

import net.bytebuddy.implementation.bind.MethodDelegationBinder;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "service")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    @Column(unique = true)
    private String servicename;

    @NotBlank
    @Size(max = 300)
    private String servicedesc;

    @NotNull
    private String imagepath;

    @NotNull
    private double deliveryprice;

    @NotNull
    private double returnprice;

    @NotNull
    private double depositprice;

    @NotNull
    private int quantity;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name="serviceid", insertable=false, updatable=false, nullable = false)
    private Set<Price> prices;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name="serviceid", insertable=false, updatable=false, nullable = false)
    private Set<Item> items;


    public Service() {
    }

    public Service(Long id, String servicename, String servicedesc, String imagepath, double deliveryprice, double returnprice, double depositprice, int quantity, Set<Price> prices, Set<Item> items) {
        this.id = id;
        this.servicename = servicename;
        this.servicedesc = servicedesc;
        this.imagepath = imagepath;
        this.deliveryprice = deliveryprice;
        this.returnprice = returnprice;
        this.depositprice = depositprice;
        this.quantity = quantity;
        this.prices = prices;
        this.items = items;
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

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public Set<Price> getPrices() {
        return prices;
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

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrices(Set<Price> prices) {
        this.prices = prices;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }
}
