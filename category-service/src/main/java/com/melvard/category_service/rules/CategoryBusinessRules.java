package com.melvard.category_service.rules;

import com.melvard.category_service.repository.CategoryRepository;
import io.github.melihvardar.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryBusinessRules {

    private final CategoryRepository categoryRepository;

    public void checkIfCategoryExists(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new BusinessException("Category with ID " + id + " does not exist");
        }
    }

    public void checkIfCategoryNameExists(String name) {
        if (categoryRepository.existsByName(name)) {
            throw new BusinessException("Category with name '" + name + "' already exists");
        }
    }

}
