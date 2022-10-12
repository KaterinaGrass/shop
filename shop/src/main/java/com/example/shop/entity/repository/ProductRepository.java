package com.example.shop.entity.repository;

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

    @Query("select p from Product p where p.active = true and p.deleted = false")
    List<Product> getAllProducts ();

    @Query(value = "select * from product p where p.deleted = false and p.active = true order by rand() asc limit 4",
            nativeQuery = true)
    List<Product> listViewProducts();

    @Query(value = "select * from product p inner join category c on category_id = p.category_id where p.category_id = ?1",
            nativeQuery = true)
    List<Product> getRelatedProducts(Integer categoryId);

    @Query("select p from Product p where p.active = true and p.deleted = false order by p.coastPrice desc")
    List<Product> filterHighPrice();

    @Query("select p from Product p where p.active = true and p.deleted = false order by p.coastPrice")
    List<Product> filterLowPrice();

}
