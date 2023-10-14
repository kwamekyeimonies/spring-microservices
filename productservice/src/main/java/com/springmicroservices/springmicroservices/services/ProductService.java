package com.springmicroservices.springmicroservices.services;


import com.springmicroservices.springmicroservices.dto.ProductRequest;
import com.springmicroservices.springmicroservices.dto.ProductResponse;
import com.springmicroservices.springmicroservices.models.Product;
import com.springmicroservices.springmicroservices.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public void createProduct(ProductRequest productRequest){
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();

        productRepository.save(product);
        log.info("Product "+product.getId()+" saved successfully");
    }

    public List<ProductResponse> getAllProducts(){
        List<Product> products =  productRepository.findAll();
        return products.stream().map(this::mapToProductResponse).toList();
    }

    private ProductResponse mapToProductResponse(Product product){
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
