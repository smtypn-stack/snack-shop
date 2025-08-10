package com.snack.shop.BinhdttSnackShop;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository repo;
    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }
    public List<Product> findAll() {
        return this.repo.findAll();
    }
    public Optional<Product> getById(String id) {
        return this.repo.findById(id);
    }
    public Product save(Product product) {
        return this.repo.save(product);
    }
    public void delete(String id) {
        this.repo.deleteById(id);
    }

}
