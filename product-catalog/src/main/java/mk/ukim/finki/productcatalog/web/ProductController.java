package mk.ukim.finki.productcatalog.web;

import mk.ukim.finki.productcatalog.domain.dtos.request.CreateProductDto;
import mk.ukim.finki.productcatalog.domain.dtos.response.GetProductDto;
import mk.ukim.finki.productcatalog.domain.models.Product;
import mk.ukim.finki.productcatalog.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/products")
@CrossOrigin(value = "*")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "/{categoryId}")
    public ResponseEntity<List<GetProductDto>> getProductsForCategory(@RequestHeader(name = "Authorization", required = false, defaultValue = "") String token, @PathVariable Long categoryId, @RequestParam(required = false, defaultValue = "0") String userId) {
        return ResponseEntity.ok(this.productService.findProductsByCategory(categoryId, Long.parseLong(userId), token));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Product> createProduct(@RequestBody CreateProductDto dto) {
        return ResponseEntity.ok(this.productService.insert(dto));
    }

    @PutMapping(value = "/{productId}/uploadImages")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public HttpStatus uploadImagesForProduct(@PathVariable Long productId, @RequestParam MultipartFile[] images) throws IOException {
        this.productService.uploadImagesForProduct(images, productId);
        return HttpStatus.OK;
    }
}
