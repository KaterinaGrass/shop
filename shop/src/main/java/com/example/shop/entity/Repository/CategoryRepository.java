package com.example.shop.entity.Repository;

import com.example.shop.entity.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
  //  @Query ("select c from Category c where c.is.activated = true and c.is.deleted = false")
   // List<Category> findAllByActive();


}
