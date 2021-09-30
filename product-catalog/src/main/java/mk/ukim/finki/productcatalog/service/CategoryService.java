package mk.ukim.finki.productcatalog.service;

import mk.ukim.finki.productcatalog.domain.dtos.request.CreateCategoryDto;
import mk.ukim.finki.productcatalog.domain.models.Category;
import org.springframework.web.multipart.MultipartFile;

public interface CategoryService {
    Category findById(Long id);
    Category insert(CreateCategoryDto dto);
}
