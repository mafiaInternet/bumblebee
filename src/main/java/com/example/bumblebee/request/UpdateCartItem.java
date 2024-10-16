package com.example.bumblebee.request;

import lombok.*;

@Setter
@Getter
@Data
public class UpdateCartItem {
    private int quantity;
    private boolean status;

}
