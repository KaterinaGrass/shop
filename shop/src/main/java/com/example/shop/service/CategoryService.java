package com.example.shop.service;


import com.example.shop.entity.dto.CategoryDto;
import com.example.shop.entity.model.Category;

import java.util.List;

public interface CategoryService {

List<Category> findByAll();
Category save (Category category);
Category findById (Integer id);
Category update (Category category);
void deleteById (Integer id);
void enabledById (Integer id);
List<Category> findAllByActivated();

List<CategoryDto> getCategoryAndProduct();

}
