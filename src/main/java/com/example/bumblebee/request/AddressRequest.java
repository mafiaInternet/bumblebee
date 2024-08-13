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
    private String name;
    private String mobile;
    private String province;
    private String district;
    private String ward;
    private String description;
    private String state;

}
