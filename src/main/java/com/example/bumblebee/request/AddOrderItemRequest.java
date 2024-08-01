package com.example.bumblebee.request;

import com.example.bumblebee.model.entity.CartItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AddOrderItemRequest {
    private AddItemRequest addItemRequest;
    private CartItem[] cartItems;
}
