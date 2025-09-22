package com.gl.si.events;

import com.gl.si.model.Product;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ProductCreatedEvent extends ApplicationEvent {
    private Product product;
    public ProductCreatedEvent(Object source,Product product) {
        super(source);
        this.product = product;
    }
}
