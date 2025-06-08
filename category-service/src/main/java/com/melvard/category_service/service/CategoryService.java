package com.melvard.category_service.service;

import com.melvard.category_service.dtos.CategoryResponseDTO;
import com.melvard.category_service.dtos.CreateCategoryRequestDTO;

import java.util.List;

public interface CategoryService {
    CategoryResponseDTO createCategory(CreateCategoryRequestDTO requestDTO);
    List<CategoryResponseDTO> getAllCategories();
    CategoryResponseDTO getById(Long id);
    CategoryResponseDTO updateCategory(Long id, CreateCategoryRequestDTO requestDTO);
    void deleteCategory(Long id);
}
