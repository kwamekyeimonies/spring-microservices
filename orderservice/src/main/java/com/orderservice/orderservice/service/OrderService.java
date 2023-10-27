package com.orderservice.orderservice.service;

import brave.Span;
import brave.Tracer;
import com.orderservice.orderservice.dto.InventoryResponse;
import com.orderservice.orderservice.dto.OrderLineItemsDto;
import com.orderservice.orderservice.dto.OrderRequest;
import com.orderservice.orderservice.event.OrderPlacedEvent;
import com.orderservice.orderservice.model.Order;
import com.orderservice.orderservice.model.OrderLineItems;
import com.orderservice.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;
    private final Tracer tracer;
    private KafkaTemplate<String,OrderPlacedEvent> kafkaTemplate;

    public String placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItemsList = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();

        order.setOrderLineItemsList(orderLineItemsList);
        List<String> skuCodes = order.getOrderLineItemsList().stream()
                .map(OrderLineItems::getSkuCode)
                .toList();

//        Making synchronous Call in spring
        Span inventoryServiceLookup = tracer.nextSpan().name("InventoryServiceLookup");
        try(Tracer.SpanInScope spanInScope =  tracer.withSpanInScope(inventoryServiceLookup.start())){
            InventoryResponse[] inventoryResponses =  webClientBuilder.build().get()
                    .uri("http://inventoryservice/api/v1/inventory",
                            uriBuilder -> uriBuilder.queryParam("skuCode",skuCodes).build())
                    .retrieve()
                    .bodyToMono(InventoryResponse[].class)
                    .block();

            assert inventoryResponses != null;
            boolean allProductsInStock = Arrays.stream(inventoryResponses).allMatch(InventoryResponse::isInStock);

            if(allProductsInStock){
                orderRepository.save(order);
                kafkaTemplate.send("notification",new OrderPlacedEvent(order.getOrderNumber()));
                String msg = "Order placed successfully";

                return msg;
            }else{
                throw new IllegalArgumentException("Product is not in stock,please try again later");
            }
        }finally {
            inventoryServiceLookup.finish();
        }


    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());

        return orderLineItems;
    }
}
