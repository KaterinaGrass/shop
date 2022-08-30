package com.example.shop.service;

import com.example.shop.entity.dto.ProductDto;
import com.example.shop.entity.model.Category;
import com.example.shop.entity.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
/* Admin*/
    List<ProductDto> findByAll();
    Product save (MultipartFile imageProduct,ProductDto productDto);
    Product update ( MultipartFile imageProduct, ProductDto productDto);
    void deleteById (Integer id);
    void enabledById (Integer id);
    ProductDto getById (Integer id);

    Page<Product> pageProduct(int pageNo);

/*User*/
    List<Product> getAllProducts();




}
