package com.example.tic4902_springbootapis.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "price", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"serviceid", "days"})
})

public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long serviceid;

    @NotNull
    private double price;

    @NotNull
    private Integer days;

//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    //@JoinColumn(name="id", referencedColumnName="serviceid", insertable=false, updatable=false)
//    @JoinColumn(name="serviceid", insertable=false, updatable=false, nullable = false)
//    private Service service;

    public Price() {
    }

    public Price(Long serviceid, double price, Integer days) {
        this.serviceid = serviceid;
        this.price = price;
        this.days = days;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }
}
