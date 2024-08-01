package com.example.bumblebee.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Color {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String imageUrl;
    private String name;
    @OneToMany
    private List<Size> sizes;

}
