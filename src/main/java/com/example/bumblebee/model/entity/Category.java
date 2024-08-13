package com.example.bumblebee.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "category")
public class Category {
    @Id
    private Integer id;

 private String nameId;
    private String name;

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", nameId='" + nameId + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
