package com.melvard.category_service.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCategoryRequestDTO {
    @NotBlank(message = "Category name cannot be empty")
    private String name;

    private Long parentId; // Optional, can be null if it's a top-level category
}
