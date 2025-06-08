package com.melvard.category_service.service;

import com.melvard.category_service.dtos.CategoryResponseDTO;
import com.melvard.category_service.dtos.CreateCategoryRequestDTO;
import com.melvard.category_service.entity.Category;
import com.melvard.category_service.repository.CategoryRepository;
import com.melvard.category_service.rules.CategoryBusinessRules;
import io.github.melihvardar.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryBusinessRules categoryBusinessRules;

    @Override
    public CategoryResponseDTO createCategory(CreateCategoryRequestDTO requestDTO) {

        // Check if category with the same name already exists
        categoryBusinessRules.checkIfCategoryNameExists(requestDTO.getName());

        Category category = Category.builder()
                .name(requestDTO.getName())
                .parent(requestDTO.getParentId() != null ?
                        categoryRepository.findById(requestDTO.getParentId())
                                .orElseThrow(() -> new BusinessException("Parent not found"))
                        : null)
                .build();

        Category savedCategory = categoryRepository.save(category);

        return new CategoryResponseDTO(savedCategory.getId(), savedCategory.getName(), savedCategory.getParent().getId());
    }

    @Override
    public List<CategoryResponseDTO> getAllCategories() {

        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(category -> new CategoryResponseDTO(category.getId(), category.getName(),
                        category.getParent() != null ? category.getParent().getId() : null))
                .toList();
    }

    @Override
    public CategoryResponseDTO getById(Long id) {

        categoryBusinessRules.checkIfCategoryExists(id);

        Category category = categoryRepository.findById(id).get();

        return CategoryResponseDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .parentId(category.getParent() != null ? category.getParent().getId() : null)
                .build();
    }

    @Override
    public CategoryResponseDTO updateCategory(Long id, CreateCategoryRequestDTO requestDTO) {

        categoryBusinessRules.checkIfCategoryExists(id);
        categoryBusinessRules.checkIfCategoryNameExists(requestDTO.getName());
        Category category = categoryRepository.findById(id).get();
        category.setName(requestDTO.getName());
        if (requestDTO.getParentId() != null) {
            category.setParent(categoryRepository.findById(requestDTO.getParentId())
                    .orElseThrow(() -> new BusinessException("Parent not found")));
        } else {
            category.setParent(null);
        }
        Category updatedCategory = categoryRepository.save(category);

        return CategoryResponseDTO.builder()
                .id(updatedCategory.getId())
                .name(updatedCategory.getName())
                .parentId(updatedCategory.getParent() != null ? updatedCategory.getParent().getId() : null)
                .build();
    }

    @Override
    public void deleteCategory(Long id) {

        categoryBusinessRules.checkIfCategoryExists(id);
        categoryRepository.deleteById(id);
    }
}

