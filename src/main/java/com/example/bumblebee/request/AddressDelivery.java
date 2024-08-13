package com.example.bumblebee.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AddressDelivery {
    private String name;
    private String mobile;
    private String province;
    private String district;
    private String ward;
    private String description;

}
