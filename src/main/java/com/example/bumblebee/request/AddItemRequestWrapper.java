package com.example.bumblebee.request;

import com.example.bumblebee.model.entity.Cart;
import com.example.bumblebee.model.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddItemRequestWrapper {
    private User user;
    private AddItemRequest req;
    private Cart cart;
}
