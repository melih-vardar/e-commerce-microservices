package com.melvard.product_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDto {
    @NotBlank(message = "Name cannot be empty")
    private String name;

    private String description;

    private String imageUrl;

    @NotBlank(message = "Price cannot be empty")
    private Double price;

    private String category;
}
