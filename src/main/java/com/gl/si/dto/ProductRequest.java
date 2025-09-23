package com.gl.si.dto;

import jakarta.validation.constraints.*;

public record ProductRequest(
        @NotNull(message = "Name is required")
                @NotBlank(message = "Name is required")
        String name,
                             String description,
                             @NotNull(message = "Price is required")
                                    @DecimalMin(value = "10.0", message = "Price must be greater than 10")
                             Double price,
                             @NotNull(message = "Quantity is required")
                             @Min(value = 5, message = "Quantity must be greater than 5")
                                     @Max(value = 100, message = "Quantity must be less than 100")
                             int quantity) {
}
