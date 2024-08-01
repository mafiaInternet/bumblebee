package com.example.bumblebee.controller;

import com.example.bumblebee.config.JwtProvider;
import com.example.bumblebee.exception.UserException;
import com.example.bumblebee.model.dao.UserDao;
import com.example.bumblebee.model.entity.User;
import com.example.bumblebee.request.LoginRequest;
import com.example.bumblebee.service.Impl.CustomeUserServiceImpl;

//import org.apache.commons.collections4.bag.HashBag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
//import java.util.Base64;

//import org.apache.commons.collections.*;

//import org.apache.commons.collections4.functors.;
@RestController
@RequestMapping("/auth")

public class AuthController {

    @Autowired
    private UserDao userDao;

    private JwtProvider jwtProvider;
    private PasswordEncoder passwordEncoder;

    private CustomeUserServiceImpl customeUserService;
    public AuthController(UserDao userDao, CustomeUserServiceImpl customeUserService, PasswordEncoder passwordEncoder, JwtProvider jwtProvider){
        this.userDao = userDao;
        this.customeUserService = customeUserService;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse>createUserHandler(@RequestBody User user) throws Exception {
        String email=user.getEmail();
        String password=user.getPassword();
        String name = user.getName();
        String mobile = user.getMobile();

        User isEmailExist= userDao.findByEmail(email);
        if(isEmailExist!=null){
            throw new UserException("Email is Already Userd anothor accoung");
        }

        User createUser=new User();
        createUser.setEmail(email);
        createUser.setPassword(passwordEncoder.encode(password));
        createUser.setName(name);
        createUser.setMobile(mobile);
        createUser.setCreateAt(LocalDateTime.now());
        User savedUser =userDao.save(createUser);
        Authentication authentication=new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);

        AuthResponse authResponse=new AuthResponse(token, "Sign up success");

        return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse>loginUserHandler(@RequestBody LoginRequest loginRequest) throws Exception {
        String username=loginRequest.getEmail();
        String password=loginRequest.getPassword();

        Authentication authentication= authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        System.out.println("auth"+authentication.toString());
        String token = jwtProvider.generateToken(authentication);

        AuthResponse authResponse=new AuthResponse(token, "Sign in success");

        return new ResponseEntity<>(authResponse, HttpStatus.ACCEPTED);
    }

    @PostMapping("/admin/login")
    public ResponseEntity<AuthResponse> loginAdminHandler(@RequestBody LoginRequest loginRequest) throws Exception{
        String username=loginRequest.getEmail();
        String password=loginRequest.getPassword();

        Authentication authentication= authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        System.out.println("auth"+authentication.toString());
        String token = jwtProvider.generateToken(authentication);

        AuthResponse authResponse=new AuthResponse(token, "Sign in success");

        return new ResponseEntity<>(authResponse, HttpStatus.ACCEPTED);
    }

//    @PostMapping("/login")
//    public ResponseEntity<AuthResponse>loginUserHandler(@RequestBody LoginRequest loginRequest) throws Exception {
//        String username=loginRequest.getEmail();
//        String password=loginRequest.getPassword();
//
//        Authentication authentication= authenticate(username, password);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        System.out.println("auth"+authentication.toString());
//        System.out.println("vclll"+ authentication);
//        String token = jwtProvider.generateToken(authentication);
//
//        AuthResponse authResponse=new AuthResponse(token, "Sign in success");
//
//        return new ResponseEntity<>(authResponse, HttpStatus.ACCEPTED);
//    }





    @PostMapping("/logout")
    public ResponseEntity<AuthResponse> logoutUserHandler(){

        AuthResponse authResponse = new AuthResponse(null, "Logout success");

        return new ResponseEntity<>(authResponse, HttpStatus.ACCEPTED);
    }



    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customeUserService.loadUserByUsername(username);
        if(userDetails==null){
            throw new BadCredentialsException("Invalid Username...");
        }

        if(!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException("Invalid password...");
        }


        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
