package com.example.shop.controller;

import com.example.shop.service.CategoryService;
import com.example.shop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class ProductControllerTest {

    @Mock
    private ProductService productService;
    @Mock
    private CategoryService categoryService;
    @InjectMocks
    ProductController productController;


    @Test
    void products() {

    }
}