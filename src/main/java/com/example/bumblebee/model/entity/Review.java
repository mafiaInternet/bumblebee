package com.example.bumblebee.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "desciption")
    private String desciption;
    @Column(name = "rating")
    private float rating;
    private String response;
    private List<String> imageUrls = new ArrayList<>();
    @ManyToOne
    private Product product;
    @OneToOne
    private Order order;
    @ManyToOne
    @JsonIgnore
    private User user;

    private LocalDateTime createdAt;
}
