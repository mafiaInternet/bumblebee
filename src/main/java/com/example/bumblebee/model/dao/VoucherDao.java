package com.example.bumblebee.model.dao;

import com.example.bumblebee.model.entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoucherDao extends JpaRepository<Voucher, Integer> {
}
