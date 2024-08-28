package com.example.bumblebee.controller;

import com.example.bumblebee.config.JwtProvider;
import com.example.bumblebee.exception.UserException;
import com.example.bumblebee.model.dao.AddressDao;
import com.example.bumblebee.model.dao.UserDao;
import com.example.bumblebee.model.entity.Address;
import com.example.bumblebee.model.entity.User;
import com.example.bumblebee.request.AddressRequest;
import com.example.bumblebee.response.ApiResponse;
import com.example.bumblebee.service.Impl.CustomeUserServiceImpl;
import com.example.bumblebee.service.UserService;
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

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private AddressDao addressDao;
    private CustomeUserServiceImpl customeUserService;
    private UserDao userDao;
    private JwtProvider jwtProvider;
    public UserController(UserService userService, PasswordEncoder passwordEncoder, CustomeUserServiceImpl customeUserService, UserDao userDao, JwtProvider jwtProvider, AddressDao addressDao){
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.customeUserService = customeUserService;
        this.userDao = userDao;
        this.jwtProvider = jwtProvider;
        this.addressDao = addressDao;
    }
    @GetMapping("/profile")
    public ResponseEntity<User> getUserProfileHandler(@RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);

        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
    }

    @GetMapping("/admin")
    public ResponseEntity<User> getAdminHandler(@RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserAdmin(jwt);

        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) throws UserException{
        User user = userService.findUserById(userId);
        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
    }

    @PutMapping("/user/update")
    public ResponseEntity<User> putUser(@RequestHeader("Authorization") String jwt, @RequestBody User user) throws Exception {
        User updateUser = userService.findUserProfileByJwt(jwt);
        if (updateUser.getId() != user.getId()){
            throw new Exception("Bạn không có quyền");
        }
        updateUser.setName(user.getName());
        updateUser.setMobile(user.getMobile());
        updateUser.setEmail(user.getEmail());
        updateUser.setBirthday(user.getBirthday());
        if (user.getPassword() != null){
            updateUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return new ResponseEntity<>(updateUser, HttpStatus.ACCEPTED);
    }

    @PostMapping("/user/address/add")
    public ResponseEntity<Address> addAddress(@RequestHeader("Authorization") String jwt, @RequestBody AddressRequest req) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Address address =userService.addAddress(user, req);

        return new ResponseEntity<>(address, HttpStatus.CREATED);
    }

    @PostMapping("/user/update")
    public ResponseEntity<AuthResponse>createUserHandler(@RequestHeader("Authorization") String jwt, @RequestBody User user) throws Exception {
        String email=user.getEmail();
        String password=user.getPassword();
        String name = user.getName();
        String mobile = user.getMobile();

        User isEmailExist= userDao.findByEmail(email);
        if(isEmailExist!=null){
            throw new UserException("Email is Already Userd anothor accoung");
        }

        User userEdit = userService.findUserProfileByJwt(jwt);
        if(!userEdit.getRole().equals("ADMIN")){
            System.out.println(userEdit.getRole());
            userEdit.setEmail(email);
            userEdit.setPassword(passwordEncoder.encode(password));
            userEdit.setName(name);
            userEdit.setMobile(mobile);
        }else {
            System.out.println("admin");
            userEdit = userService.findUserById(user.getId());
            userEdit.setEmail(email);
            userEdit.setPassword(passwordEncoder.encode(password));
            userEdit.setName(name);
            userEdit.setMobile(mobile);
            userEdit.setRole(user.getRole());
        }

        User savedUser =userDao.save(userEdit);
        Authentication authentication=new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);

        AuthResponse authResponse=new AuthResponse(token, "Sign up success");

        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }


    @PutMapping("/user/address/{addressId}/update")
    public ResponseEntity<Address> updateAddress(@RequestHeader("Authorization") String jwt, @PathVariable Integer addressId, @RequestBody AddressRequest req) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        System.out.println("update");
        Address address =userService.updateAddress(user,addressId, req);
        System.out.println(req.toString());
        return new ResponseEntity<>(address, HttpStatus.OK);
    }

    @DeleteMapping("/user/address/{addressId}/delete")
    public ResponseEntity<ApiResponse> deleteAddress(@RequestHeader("Authorization") String jwt, @PathVariable Integer addressId) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatus(true);
        apiResponse.setMessage(userService.deleteAddress(user,addressId));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/user/address/")
    public ResponseEntity<List<Address>> getAllAddress(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        List<Address> addressList = user.getAddress();
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatus(true);
        apiResponse.setMessage("Success!!!");
        return new ResponseEntity<>(addressList, HttpStatus.ACCEPTED);
    }

    @PutMapping("/user/address/{addressId}/default")
    public ResponseEntity<ApiResponse> updateAddressDefault(@RequestHeader("Authorization") String jwt,@PathVariable long addressId) throws UserException{
        User user = userService.findUserProfileByJwt(jwt);
        for (Address address : addressDao.findAll()){
            if(address.getState() == null){
                continue;
            }
            if(address.getState().equals("Mặc định")){
                address.setState("");
                addressDao.save(address);
            }

        }
        for (Address address : addressDao.findAll()){
            if(address.getId() == addressId){
                address.setState("Mặc định");
                addressDao.save(address);
            }
        }
        ApiResponse res = new ApiResponse();
        res.setMessage("Update address default success !!!");
        res.setStatus(true);
        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
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
