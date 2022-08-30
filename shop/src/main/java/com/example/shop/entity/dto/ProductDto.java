package com.example.shop.entity.dto;

import com.example.shop.entity.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private Integer id;
    private String title;
    private double coastPrice;
    private double salePrise;
    private int quantity;
    private String description;
    private String image;
    private boolean deleted;
    private boolean active;
    private Category category;
}
