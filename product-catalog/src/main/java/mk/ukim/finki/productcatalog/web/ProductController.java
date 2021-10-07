package mk.ukim.finki.productcatalog.web;

import mk.ukim.finki.productcatalog.domain.dtos.request.CreateProductDto;
import mk.ukim.finki.productcatalog.domain.dtos.response.GetProductDto;
import mk.ukim.finki.productcatalog.domain.models.Product;
import mk.ukim.finki.productcatalog.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/products")
@CrossOrigin(value = "*")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "/{categoryId}")
    public ResponseEntity<List<GetProductDto>> getProductsForCategory(@PathVariable Long categoryId, @RequestParam(required = false, defaultValue = "0") String userId) {
        return ResponseEntity.ok(this.productService.findProductsByCategory(categoryId, Long.parseLong(userId)));
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody CreateProductDto dto) {
        return ResponseEntity.ok(this.productService.insert(dto));
    }
}
