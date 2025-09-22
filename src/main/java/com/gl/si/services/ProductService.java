package com.gl.si.services;

import com.gl.si.dto.ProductRequest;
import com.gl.si.events.ProductCreatedEvent;
import com.gl.si.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProductService {

    private final ApplicationEventPublisher eventPublisher;

    public ProductService(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public Product createProduct(ProductRequest productDto) {
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
}
