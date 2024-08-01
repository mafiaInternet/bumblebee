package com.example.bumblebee.model.entity;

import com.example.bumblebee.request.PaymentDetails;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String orderId;
    @OneToOne
    private User user;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(name = "order_items")
    private List<OrderItem> orderItems = new ArrayList<>();
    //    private Set<OrderItem> orderItems=new HashSet<>();
    private LocalDateTime orderDate;
    private LocalDateTime deliveryDate;
    @OneToOne
    private Address address = new Address();

    @Embedded
    private PaymentDetails paymentDetails = new PaymentDetails();

    private double totalPrice;
    private double totalDiscountedPrice;
    private double discount;
    private String orderStatus;
    private int totalItem;
    private LocalDateTime createAt;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderId='" + orderId + '\'' +
                ", user=" + user +
                ", orderItems=" + orderItems +
                ", orderDate=" + orderDate +
                ", deliveryDate=" + deliveryDate +
                ", address=" + address +
                ", paymentDetails=" + paymentDetails +
                ", totalPrice=" + totalPrice +
                ", totalDiscountedPrice=" + totalDiscountedPrice +
                ", discount=" + discount +
                ", orderStatus='" + orderStatus + '\'' +
                ", totalItem=" + totalItem +
                ", createAt=" + createAt +
                '}';
    }
}
