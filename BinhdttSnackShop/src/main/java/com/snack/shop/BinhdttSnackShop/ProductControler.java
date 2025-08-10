package com.snack.shop.BinhdttSnackShop;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:5173/")
public class ProductControler {
    private final ProductService productService;
    public ProductControler(ProductService productService) {
        this.productService = productService;
    }
    @GetMapping
    public List<Product> findAll() {
        return productService.findAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable String id) {
        return productService.getById(id)
                .map(p -> ResponseEntity.ok(p))
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
    public Product create(@RequestBody Product product) {
        return productService.save(product);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable String id, @RequestBody Product product) {
        return productService.getById(id)
                .map(existing -> {
                    product.setId(id);
                    return ResponseEntity.ok(productService.save(product));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Product> delete(@PathVariable String id) {
        if (productService.getById(id).isPresent()) {
            productService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
