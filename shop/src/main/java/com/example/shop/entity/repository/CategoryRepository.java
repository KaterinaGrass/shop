package com.example.shop.entity.repository;


import com.example.shop.entity.dto.CategoryDto;
import com.example.shop.entity.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query("select c from Category c where c.active = true and c.deleted = false")
    List<Category> findAllByActivated();

    @Query("select new com.example.shop.entity.dto.CategoryDto(c.id, c.title, count(p.category.id)) from Category c inner" +
            " join Product p on p.category.id = c.id " +
            " where c.active = true and c.deleted = false group by c.id")
    List<CategoryDto> getCategoryAndProduct();

}



