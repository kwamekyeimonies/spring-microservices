package com.springmicroservices.springmicroservices.repository;

import com.springmicroservices.springmicroservices.models.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product,String> {
}
