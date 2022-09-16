package com.example.shop.service.impl;

import com.example.shop.entity.Repository.ProductRepository;
import com.example.shop.entity.dto.ProductDto;
import com.example.shop.entity.model.Product;
import com.example.shop.service.ProductService;
import com.example.shop.utils.ImageUpload;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ImageUpload imageUpload;


    @Override
    public List<ProductDto> findByAll() {
        List<ProductDto> productDtoList = new ArrayList<>();
        List<Product> products = productRepository.findAll();
        for (Product product : products) {
            ProductDto productDto = new ProductDto();
            productDto.setId(product.getId());
            productDto.setTitle(product.getTitle());
            productDto.setDescription(product.getDescription());
            productDto.setQuantity(product.getQuantity());
            productDto.setCoastPrice(product.getCoastPrice());
            productDto.setSalePrise(product.getSalePrise());
            productDto.setImage(product.getImage());
            productDto.setActive(product.isActive());
            productDto.setDeleted(product.isDeleted());
            productDto.setCategory(product.getCategory());
            productDtoList.add(productDto);
        }
        return productDtoList;
    }

    @Override
    public Product save(MultipartFile imageProduct, ProductDto productDto) {
        try {
            Product product = new Product();
            if(imageProduct == null){
                product.setImage(null);
            }else{
                if(imageUpload.uploadImage(imageProduct)){
                    System.out.println("Upload successfully");
                }
                product.setImage(Base64.getEncoder().encodeToString(imageProduct.getBytes()));
            }
            product.setTitle(productDto.getTitle());
            product.setDescription(productDto.getDescription());
            product.setCategory(productDto.getCategory());
            product.setCoastPrice(productDto.getCoastPrice());
            product.setQuantity(productDto.getQuantity());
            product.setActive(true);
            product.setDeleted(false);
            return productRepository.save(product);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    @Transactional
    public Product update(MultipartFile imageProduct, ProductDto productDto) {
        try {
            Product product = productRepository.getById(productDto.getId());
            if(imageProduct == null){
                product.setImage(product.getImage());
            }else{
                if(imageUpload.checkExisted(imageProduct) == false){
                    imageUpload.uploadImage(imageProduct);
                }
                product.setImage(Base64.getEncoder().encodeToString(imageProduct.getBytes()));
            }
            product.setTitle(productDto.getTitle());
            product.setDescription(productDto.getDescription());
            product.setSalePrise(productDto.getSalePrise());
            product.setCoastPrice(productDto.getCoastPrice());
            product.setQuantity(productDto.getQuantity());
            product.setCategory(productDto.getCategory());
            return productRepository.save(product);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deleteById(Integer id) {
        Product product = productRepository.getById(id);
        product.setDeleted(true);
        product.setActive(false);
        productRepository.save(product);
    }

    @Override
    public void enabledById(Integer id) {
        Product product = productRepository.getById(id);
        product.setActive(true);
        product.setDeleted(false);
        productRepository.save(product);
    }

    @Override
    public ProductDto getById(Integer id) {
        Product product = productRepository.getById(id);
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setTitle(product.getTitle());
        productDto.setDescription(product.getDescription());
        productDto.setQuantity(product.getQuantity());
        productDto.setCategory(product.getCategory());
        productDto.setCoastPrice(product.getCoastPrice());
        productDto.setSalePrise(product.getSalePrise());
        productDto.setImage(product.getImage());
        productDto.setActive(product.isActive());
        productDto.setDeleted(product.isDeleted());
        return productDto;
    }

    @Override
    public Page<Product> pageProduct(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 4);
        Page<Product> productPage = productRepository.pageProduct(pageable);
        return productPage;
    }



    @Override
    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }

    @Override
    public List<Product> listViewProducts() {
        return productRepository.listViewProducts();
    }

    @Override
    public Product getProductById(Integer id) {
        return productRepository.getById(id);
    }

    @Override
    public List<Product> getRelatedProducts(Integer categoryId) {
        return productRepository.getRelatedProducts(categoryId);
    }

    @Override
    public List<Product> filterHighPrice() {
        return productRepository.filterHighPrice();
    }

    @Override
    public List<Product> filterLowPrice() {
        return productRepository.filterLowPrice();
    }

}
