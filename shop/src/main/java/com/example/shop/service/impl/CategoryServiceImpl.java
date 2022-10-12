package com.example.shop.service.impl;

import com.example.shop.entity.repository.CategoryRepository;
import com.example.shop.entity.model.Category;
import com.example.shop.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private  final CategoryRepository categoryRepository;

    @Override
    public List<Category> findByAll() {
        return categoryRepository.findAll();
    }

    @Override // конструктор в энтиту
    public Category save(Category category) {
        try {
            Category categorySave = new Category(category.getTitle());
            return categoryRepository.save(categorySave);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Category findById(Integer id) {
        return categoryRepository.findById(id).get();
    }

    @Override
    @Transactional
    public Category update(Category category) {
        Category updateCategory = null;
        try {
            updateCategory = categoryRepository.findById(category.getId()).get();
            updateCategory.setTitle(category.getTitle());
            updateCategory.setActive(category.isActive());
            updateCategory.setDeleted(category.isDeleted());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categoryRepository.save(updateCategory);
    }

    @Override
    public void deleteById(Integer id) {
        Category category = categoryRepository.getById(id);
        category.setDeleted(true);
        category.setActive(false);
        categoryRepository.save(category);

    }

    @Override
    public void enabledById(Integer id) {
        Category category = categoryRepository.getById(id);
        category.setActive(true);
        category.setDeleted(false);
        categoryRepository.save(category);
    }

    @Override
    public List<Category> findAllByActivated() {
        return categoryRepository.findAllByActivated();
    }

}
