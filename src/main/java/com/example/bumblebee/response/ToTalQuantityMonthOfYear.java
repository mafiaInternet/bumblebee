package com.example.bumblebee.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class ToTalQuantityMonthOfYear {
    private int year;
    private int month;
    private int quantity;
}
