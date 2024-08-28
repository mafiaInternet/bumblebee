package com.example.bumblebee.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Size {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Integer id;

    private String name;
    private int quantity;
    @JsonIgnore
    @ManyToOne
    private Color color;

    @Override
    public String toString() {
        return "Size{" +
                "name='" + name + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
