package com.gl.si.events;

import com.gl.si.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProductEventListener {

    @EventListener
    public void handleProductCreatedEvent(ProductCreatedEvent event) {
       Product product = event.getProduct();
       log.info("Event product created: {}", product);
    }
}
