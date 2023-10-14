package com.springmicroservices.springmicroservices.controller;


import com.springmicroservices.springmicroservices.dto.ProductRequest;
import com.springmicroservices.springmicroservices.dto.ProductResponse;
import com.springmicroservices.springmicroservices.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequest productRequest){
        productService.createProduct(productRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts(){
        return productService.getAllProducts();
    }
}
