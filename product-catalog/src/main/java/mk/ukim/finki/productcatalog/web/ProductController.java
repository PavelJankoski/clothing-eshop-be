package mk.ukim.finki.productcatalog.web;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.productcatalog.domain.dtos.request.CreateProductDto;
import mk.ukim.finki.productcatalog.domain.dtos.request.FilterProductsDto;
import mk.ukim.finki.productcatalog.domain.dtos.response.GetProductDto;
import mk.ukim.finki.productcatalog.domain.models.Product;
import mk.ukim.finki.productcatalog.service.ProductService;
import mk.ukim.finki.sharedkernel.domain.dto.response.GetOrderItemDto;
import mk.ukim.finki.sharedkernel.domain.dto.response.GetOrderProductDto;
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
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping(value = "/{categoryId}")
    public ResponseEntity<List<GetProductDto>> getProductsForCategory(@PathVariable Long categoryId, @RequestParam(required = false, defaultValue = "0") String userId) {
        return ResponseEntity.ok(this.productService.findProductsByCategory(categoryId, Long.parseLong(userId)));
    }

    @GetMapping(value = "/price/{productId}")
    public ResponseEntity<Float> getPriceForProduct(@PathVariable Long productId){
        return ResponseEntity.ok(this.productService.findPriceForProduct(productId));
    }

    @PostMapping(value = "/filter")
    public ResponseEntity<List<GetProductDto>> getFilteredProducts(@RequestBody FilterProductsDto dto, @RequestParam(required = false, defaultValue = "0") String userId) {
        return ResponseEntity.ok(this.productService.findFilteredProducts(dto, Long.parseLong(userId)));
    }

    @GetMapping(value = "/search")
    public ResponseEntity<List<GetProductDto>> getSearchedProducts(@RequestParam String searchText, @RequestParam(required = false, defaultValue = "0") String userId) {
        return ResponseEntity.ok(this.productService.findAllSearchedProducts(searchText, Long.parseLong(userId)));
    }

    @GetMapping(value = "/code/{code}")
    public ResponseEntity<GetProductDto> getProductByCode(@PathVariable String code, @RequestParam(required = false, defaultValue = "0") String userId) {
        return ResponseEntity.ok(this.productService.findProductByCode(code, Long.parseLong(userId)));
    }

    @GetMapping(value = "/to-cart-item/{productId}/{sizeId}")
    public ResponseEntity<GetOrderItemDto> getCartItem(@PathVariable Long productId, @PathVariable Long sizeId) {
        return ResponseEntity.ok(this.productService.getCartItem(productId, sizeId));
    }

    @GetMapping(value = "/get-order-product/{productId}/sizes/{sizeId}/users/{userId}")
    public ResponseEntity<GetOrderProductDto> getOrderProduct(
            @PathVariable Long productId,
            @PathVariable Long sizeId,
            @PathVariable Long userId) {
        return ResponseEntity.ok(this.productService.getOrderProduct(productId, sizeId, userId));
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
