package com.example.bumblebee.service.Impl;

import com.example.bumblebee.config.JwtProvider;
import com.example.bumblebee.exception.UserException;
import com.example.bumblebee.model.dao.AddressDao;
import com.example.bumblebee.model.dao.UserDao;
import com.example.bumblebee.model.entity.Address;
import com.example.bumblebee.model.entity.User;
import com.example.bumblebee.request.AddressRequest;
import com.example.bumblebee.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private UserDao userDao;
    private JwtProvider jwtProvider;
    private AddressDao addressDao;

    public UserServiceImpl(UserDao userDao, JwtProvider jwtProvider, AddressDao addressDao){
        this.userDao = userDao;
        this.jwtProvider = jwtProvider;
        this.addressDao = addressDao;
    }
    @Override
    public User findUserById(Long userId) throws UserException{
        System.out.println("find id " + userId);
        for(User user: userDao.findAll()){
            if(user.getId() == userId){
                return user;
            }
        }
        return null;
    }


    @Override
    public User findUserProfileByJwt(String jwt) throws UserException {

        String email = jwtProvider.getEmailFromToken(jwt);
        System.out.println(email);
        User user=userDao.findByEmail(email);

        if(user==null){
            throw new UserException("User not found with email - "+email);
        }
        return user;
    }

    @Override
    public User findUserAdmin(String jwt) throws Exception {
        String email = jwtProvider.getEmailFromToken(jwt);
        User user=userDao.findByEmail(email);

        if (user==null){
            throw new UserException("User not found with email - "+email);
        } else if (user.getRole() == null) {
            throw new UserException("User admin not found with email - "+ email);
        }

        return user;
    }

    @Override
    public User findUserByEmail(String email){

        User user = userDao.findByEmail(email);

        return user;
    }

    @Override
    public Address addAddress(User user, AddressRequest req) throws UserException{
        Address address = new Address();
        address.setUser(user.getId());
        address.setName(req.getName());
        address.setMobile(req.getMobile());
        address.setDescription(req.getDescription());
        address.setProvince(req.getProvince());
        address.setDistrict(req.getDistrict());
        address.setWard(req.getWard());
        address.setState(req.getState());
        addressDao.save(address);

        user.getAddress().add(address);
        userDao.save(user);
        return address;
    }



    @Override
    public String deleteAddress(User user, long addressId) {
        addressDao.deleteById(addressId);
        return "Delete address - user id " + user.getId() + "success !!!";
    }


    @Override
    public Address updateAddress(User user, int addressId, AddressRequest req)throws UserException{
        for(Address address:user.getAddress()){
            if(address.getId() == addressId){
                address.setUser(user.getId());
                address.setName(req.getName());
                address.setMobile(req.getMobile());
                address.setDescription(req.getDescription());
                address.setProvince(req.getProvince());
                address.setDistrict(req.getDistrict());
                address.setWard(req.getWard());
                address.setState(req.getState());

                return addressDao.save(address);
            }
        }
        return null;
    }
}
