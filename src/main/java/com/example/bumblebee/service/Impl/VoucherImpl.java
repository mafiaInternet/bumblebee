package com.example.bumblebee.service.Impl;

import com.example.bumblebee.model.dao.VoucherDao;
import com.example.bumblebee.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoucherImpl implements VoucherService {
    @Autowired
    private VoucherDao voucherDao;



}
