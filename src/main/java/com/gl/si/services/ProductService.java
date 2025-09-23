package com.gl.si.services;

import com.gl.si.dto.ProductRequest;
import com.gl.si.events.ProductCreatedEvent;
import com.gl.si.exceptions.ValidationException;
import com.gl.si.model.Product;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Slf4j
public class ProductService {

    private final Validator validator;

    private final ApplicationEventPublisher eventPublisher;

    public ProductService(Validator validator, ApplicationEventPublisher eventPublisher) {
        this.validator = validator;
        this.eventPublisher = eventPublisher;
    }

    public Product createProduct(ProductRequest productDto) {
        validate(productDto);
        log.info("Creating product: {}", productDto);
        Product product = new Product();
        product.setName(productDto.name());
        product.setDescription(productDto.description());
        product.setPrice(productDto.price());
        product.setQuantity(productDto.quantity());
        eventPublisher.publishEvent(new ProductCreatedEvent(this,product));
        log.info("Product created and published event: {}", product);
        return product;
    }

    private void validate(Object object){
        Set<ConstraintViolation<Object>> violations = validator.validate(object);
        if (!violations.isEmpty()) {
            throw new ValidationException("Invalid product data", violations);
        }
    }
}
