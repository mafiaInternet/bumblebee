package com.example.bumblebee.controller;

import com.example.bumblebee.exception.UserException;
import com.example.bumblebee.model.dao.UserDao;
import com.example.bumblebee.model.entity.User;
import com.example.bumblebee.response.ApiResponse;
import com.example.bumblebee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/customers")
public class AdminCustomerController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserDao userDao;

    @GetMapping("/all")
    public ResponseEntity<List<User>> getCustomersAll(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        List<User> userList = userDao.findAll();

        return new ResponseEntity<>(userList, HttpStatus.ACCEPTED);
    }

    @GetMapping("/search")
    public ResponseEntity<User> findUserByEmail(@RequestHeader("Authorization") String jwt, @RequestParam("email") String email) throws UserException {
        User admin = userService.findUserProfileByJwt(jwt);
        User user = userService.findUserByEmail(email);

        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable long userId){
        userDao.deleteById(userId);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("success");
        apiResponse.setStatus(true);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
