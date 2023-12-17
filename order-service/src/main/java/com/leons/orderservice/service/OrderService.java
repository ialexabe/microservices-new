package com.leons.orderservice.service;

import com.leons.orderservice.dto.InventoryResponse;
import com.leons.orderservice.dto.OrderLineItemsDto;
import com.leons.orderservice.dto.OrderRequest;
import com.leons.orderservice.model.Order;
import com.leons.orderservice.model.OrderLineItems;
import com.leons.orderservice.config.WebClientConfig;
import com.leons.orderservice.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClientConfig webClient;



    public void placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setOrderLineItemsList(orderRequest.getOrderLineItemsList().stream().map(this::mapToDto).toList());

        List<String> skuCodes = order.getOrderLineItemsList().stream().map(OrderLineItems::getSkuCode).toList();

        Boolean result = isStockAvailable(skuCodes);

        if(result){
           orderRepository.save(order);
       }
       else throw new IllegalArgumentException("Product out of stock");
    }

    private Boolean isStockAvailable(List<String> skuCodes) {

        InventoryResponse[] inventoryResponses  =webClient.webClient().get()
                .uri("http://localhost:8082/api/inventory",uriBuilder -> uriBuilder.queryParam("skuCode",skuCodes).build())
                .retrieve().
                bodyToMono(InventoryResponse[].class).
                block();

        boolean allProductsAreAvailable = Arrays.stream(inventoryResponses).allMatch(InventoryResponse::isInStock);


        return allProductsAreAvailable;
    }

    private OrderLineItems mapToDto (OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }

}
