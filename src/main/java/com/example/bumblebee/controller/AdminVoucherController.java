package com.example.bumblebee.controller;

import com.example.bumblebee.model.dao.UserDao;
import com.example.bumblebee.model.dao.VoucherDao;
import com.example.bumblebee.model.entity.User;
import com.example.bumblebee.model.entity.Voucher;
import com.example.bumblebee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/admin/voucher/")
public class AdminVoucherController {
    @Autowired
    private VoucherDao voucherDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserService userService;
    @GetMapping("")
    public ResponseEntity<List<Voucher>> getVoucherAll(){
            List<Voucher> vouchers = voucherDao.findAll();
            return new ResponseEntity<>(vouchers, HttpStatus.ACCEPTED);
    }

    @GetMapping("{id}")
    public ResponseEntity<Voucher> findVoucherById(@PathVariable int id){
        Voucher voucher = new Voucher();
        for(Voucher item: voucherDao.findAll()){
            if(item.getId() == id){
                voucher = item;
            }
        }

        return new ResponseEntity<>(voucher, HttpStatus.ACCEPTED);
    }

    @PostMapping("create")
    public ResponseEntity<Voucher> createVoucher(@RequestHeader("Authorization") String jwt, @RequestBody Voucher res) throws Exception {
        User user = userService.findUserAdmin(jwt);
        Voucher voucher = voucherDao.save(res);
        Object objectToDelete = new Object();
        if(res.getEnd() != null){
            Timer timer = new Timer();
            TimerTask deleteTask = new TimerTask() {
                @Override
                public void run() {
                    voucherDao.deleteById(voucher.getId());
                }
            };

            timer.schedule(deleteTask, voucher.getEnd());
        }




        return new ResponseEntity<>(voucher, HttpStatus.CREATED);
    }

//    @PutMapping("{id}/edit")
//    public ResponseEntity<ApiResponse> handleEditVoucher(@PathVariable int id, @RequestBody Voucher res){
//        Voucher item = new Voucher();
//        for(Voucher voucher: voucherDao.findAll()){
//            if(voucher.getId() == id){
//                voucher.setName(res.getName());
//                voucher.setProduct(res.getProduct());
//                voucher.setStart(res.getStart());
//                voucher.setEnd(String.valueOf(res.getEnd());
//                voucher.setDescription(res.getDescription());
//                voucher.setName(res.getName());
//                voucher.setDiscountedPersent(res.getDiscountedPersent());
//                voucher.setDiscountedPrice(res.getDiscountedPrice());
//                voucher.setFreeShipped(res.getFreeShipped());
//                voucher.setTotalOrderedCustomer(res.getTotalOrderedCustomer());
//                voucher.setType(res.getType());
//                if(res.getType().equals("loyalCustomers")){
//                    voucher.setUsers(userDao.findAll());
//                }
//            }
//        }
//
//        ApiResponse mes = new ApiResponse();
//        mes.setStatus(true);
//        mes.setMessage("Change voucher success!!!");
//        return new ResponseEntity<>(mes, HttpStatus.OK);
//    }
}
