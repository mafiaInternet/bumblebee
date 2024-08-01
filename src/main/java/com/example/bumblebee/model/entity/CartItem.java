package com.example.bumblebee.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cart_item")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @JsonIgnore
    @ManyToOne
    private Cart cart;
    @OneToOne
    private Product product;
private String imageUrl;
    private String color;
    private String size;
    private int quantity;
    private double price;
    private double discountedPrice;

    private long userId;

    @Override
    public String toString() {
        return "CartItem{" +
                "id=" + id +
                ", cart=" + cart +
                ", product=" + product +
                ", imageUrl='" + imageUrl + '\'' +
                ", color='" + color + '\'' +
                ", size='" + size + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", discountedPrice=" + discountedPrice +
                ", userId=" + userId +
                '}';
    }
}
