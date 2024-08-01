package com.example.bumblebee.response;

import com.example.bumblebee.model.entity.Review;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class NumberOfEachTypeOfStartByProduct {
    private int quantity;
    private String star;

    private List<Review> reviews = new ArrayList<>();

    @Override
    public String toString() {
        return "NumberOfEachTypeOfStartByProduct{" +
                "quantity=" + quantity +
                ", star='" + star + '\'' +
                '}';
    }
}
