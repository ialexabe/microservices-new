package com.leons.productservice.service;

import com.leons.productservice.dto.ProductRequest;
import com.leons.productservice.dto.ProductResponse;
import com.leons.productservice.model.Product;
import com.leons.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    public void createProduct(ProductRequest productRequest){

        Product product = Product.builder().
                name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice()).build();

        productRepository.save(product);

        log.info("product {} is saved",product.getId());

    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream().map(this::maptoProductResponse).toList();
    }

    private ProductResponse maptoProductResponse(Product product){ return ProductResponse.builder().id(product.getId()
    ).price(product.getPrice()).description(product.getDescription()).name(product.getName()).build();
    }
}
