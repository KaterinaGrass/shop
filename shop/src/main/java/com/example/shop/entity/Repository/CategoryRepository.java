package com.example.shop.entity.Repository;


import com.example.shop.entity.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
