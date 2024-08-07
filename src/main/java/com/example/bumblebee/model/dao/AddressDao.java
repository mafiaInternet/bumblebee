package com.example.bumblebee.model.dao;

import com.example.bumblebee.model.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressDao extends JpaRepository<Address, Long> {

}
