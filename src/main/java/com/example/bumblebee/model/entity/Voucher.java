package com.example.bumblebee.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "voucher")
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String description;
    private int discountedPrice;
    private int discountedPersent;
    private String freeShipped;
    private String type;
    private int totalPriceOrdered;
    private int totalOrderedCustomer;
    private String product;
    private LocalDateTime start;
    private Date end;
    private int quantity;
    @JsonIgnore
    @OneToMany(mappedBy = "voucher", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> users = new ArrayList<>();
}
