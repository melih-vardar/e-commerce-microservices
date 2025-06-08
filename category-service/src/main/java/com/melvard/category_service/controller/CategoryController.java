package com.melvard.category_service.controller;

import com.melvard.category_service.dtos.CategoryResponseDTO;
import com.melvard.category_service.dtos.CreateCategoryRequestDTO;
import com.melvard.category_service.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

     private final CategoryService categoryService;

     @GetMapping("/{id}")
     @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
     public ResponseEntity<CategoryResponseDTO> getCategoryById(@PathVariable Long id) {
         return ResponseEntity.ok(categoryService.getById(id));
     }

     @GetMapping
     @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
     public ResponseEntity<List<CategoryResponseDTO>> getAllCategories() {
         return ResponseEntity.ok(categoryService.getAllCategories());
     }

     @PostMapping
     @PreAuthorize("hasRole('ADMIN')")
     public ResponseEntity<CategoryResponseDTO> createCategory(@Valid @RequestBody CreateCategoryRequestDTO categoryRequestDTO) {
         return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategory(categoryRequestDTO));
     }

     @PutMapping("/{id}")
     @PreAuthorize("hasRole('ADMIN')")
     public ResponseEntity<CategoryResponseDTO> updateCategory(@PathVariable Long id, @Valid @RequestBody CreateCategoryRequestDTO categoryRequestDTO) {
         return ResponseEntity.ok(categoryService.updateCategory(id, categoryRequestDTO));
     }

     @DeleteMapping("/{id}")
     @PreAuthorize("hasRole('ADMIN')")
     public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
         categoryService.deleteCategory(id);
         return ResponseEntity.noContent().build();
     }

     @GetMapping("/test")
        public String test() {
            return "Category Service is working";
        }
}
