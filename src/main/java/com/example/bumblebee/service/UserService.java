package com.example.bumblebee.service;

import com.example.bumblebee.exception.UserException;
import com.example.bumblebee.model.entity.Address;
import com.example.bumblebee.model.entity.User;
import com.example.bumblebee.request.AddressRequest;

public interface UserService {

    User findUserById(Long userId) throws UserException;

    public User findUserProfileByJwt(String jwt) throws UserException;

    User findUserAdmin(String jwt) throws Exception;

    User findUserByEmail(String email);

    Address addAddress(User user, AddressRequest req) throws UserException;

    String deleteAddress(User user, long addressId);

    Address updateAddress(User user, int addressId, AddressRequest req)throws UserException;
}
