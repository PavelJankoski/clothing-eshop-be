package mk.ukim.finki.productcatalog.web;

import mk.ukim.finki.productcatalog.domain.dtos.request.CreateCategoryDto;
import mk.ukim.finki.productcatalog.domain.models.Category;
import mk.ukim.finki.productcatalog.service.CategoryService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/api/categories")
@CrossOrigin(value = "*")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Category> createCategory(@RequestPart String typeId, @RequestPart String name, @RequestPart MultipartFile image) {
        CreateCategoryDto dto = new CreateCategoryDto(Integer.parseInt(typeId), name, image);
        return ResponseEntity.ok(this.categoryService.insert(dto));
    }
}
