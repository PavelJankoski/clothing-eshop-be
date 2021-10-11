package mk.ukim.finki.productcatalog.web;

import mk.ukim.finki.productcatalog.domain.dtos.request.CreateCategoryDto;
import mk.ukim.finki.productcatalog.domain.dtos.response.GetCategoryDto;
import mk.ukim.finki.productcatalog.domain.models.Category;
import mk.ukim.finki.productcatalog.service.CategoryService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/categories")
@CrossOrigin(value = "*")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<GetCategoryDto>> getAllCategories() {
        return ResponseEntity.ok(this.categoryService.findAllCategories());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Category> createCategory(@RequestPart String type, @RequestPart String name, @RequestPart MultipartFile image) throws IOException {
        CreateCategoryDto dto = new CreateCategoryDto(type, name, image);
        return ResponseEntity.ok(this.categoryService.insert(dto));
    }
}
