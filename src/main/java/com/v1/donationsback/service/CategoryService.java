package com.v1.donationsback.service;

import com.v1.donationsback.models.CategoryModel;
import com.v1.donationsback.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryModel findById(Long id) {
        Optional<CategoryModel> categoryOpt = categoryRepository.findById(id);
        if(!categoryOpt.isPresent() ) return null;

        return categoryOpt.get();
    }
}
