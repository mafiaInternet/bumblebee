package com.example.bumblebee.model.entity;

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
@Table(name ="address")

public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firtname;
    private String lastName;
    private String mobile;
    private String city;
    private String state;

    private Long user;

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", firtname='" + firtname + '\'' +
                ", lastName='" + lastName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", user=" + user +
                '}';
    }
}
