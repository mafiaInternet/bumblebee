package com.example.bumblebee.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequest {
    private String firstName;
    private String lastName;
    private String city;
    private String state;
}
