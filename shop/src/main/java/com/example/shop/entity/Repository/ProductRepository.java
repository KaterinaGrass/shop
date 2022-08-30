package com.example.shop.entity.Repository;

import com.example.shop.entity.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {

    @Query("select p from Product p")
    Page<Product> pageProduct(Pageable pageable);

 //   @Query("select p from Product p where p.description like %?1% or p.title %?1%")
   // Page<Product> searchProduct(String keyWord, Pageable pageable);
    @Query("select p from Product p where p.active = true and p.deleted = false")
    List<Product> getAllProducts ();
}
