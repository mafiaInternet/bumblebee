package com.example.bumblebee.service.Impl;

import com.example.bumblebee.model.dao.UserDao;
import com.example.bumblebee.model.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomeUserServiceImpl implements UserDetailsService{

    private UserDao userDao;
    private CustomeUserServiceImpl(UserDao userDao){
        this.userDao = userDao;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userDao.findByEmail(username);
        if(user==null){
            throw new UsernameNotFoundException("user not found with email" + username);
        }

        List<GrantedAuthority> authorities=new ArrayList<>();

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }


}
