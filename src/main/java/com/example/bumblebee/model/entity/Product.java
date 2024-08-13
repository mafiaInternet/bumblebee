package com.example.bumblebee.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.*;

import java.time.LocalDateTime;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Product implements Comparable<Product>  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
@Column(length = 2048)
    private List<String> listImageUrl = new ArrayList<>();
    private String description;
    private int price;
    private int discountedPrice;
    private int discountPersent;
    private int totalQuantity;
    private int totalSold;
    @OneToMany
    private List<Color> colors;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Review> reviews=new ArrayList<>();


    @Column(name = "num_ratings")
    private int numRatings;
@ManyToOne
    private Category category;

    private LocalDateTime createAt;

    @Override
    public int compareTo(Product o) {
        return 0;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", listImageUrl=" + listImageUrl +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", discountedPrice=" + discountedPrice +
                ", discountPersent=" + discountPersent +
                ", totalQuantity=" + totalQuantity +
                ", colors=" + colors +
                ", reviews=" + reviews +
                ", numRatings=" + numRatings +
                ", category=" + category +
                ", createAt=" + createAt +
                '}';
    }
}
