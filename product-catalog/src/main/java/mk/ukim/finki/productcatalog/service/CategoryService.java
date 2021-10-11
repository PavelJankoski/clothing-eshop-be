package mk.ukim.finki.productcatalog.service;

import mk.ukim.finki.productcatalog.domain.dtos.request.CreateCategoryDto;
import mk.ukim.finki.productcatalog.domain.dtos.response.GetCategoryDto;
import mk.ukim.finki.productcatalog.domain.models.Category;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CategoryService {
    List<GetCategoryDto> findAllCategories();
    Category findById(Long id);
    Category insert(CreateCategoryDto dto) throws IOException;
}
