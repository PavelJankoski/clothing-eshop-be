package mk.ukim.finki.productcatalog.service.impl;

import mk.ukim.finki.productcatalog.domain.dtos.request.CreateCategoryDto;
import mk.ukim.finki.productcatalog.domain.enums.CategoryType;
import mk.ukim.finki.productcatalog.domain.exceptions.CategoryNotFoundException;
import mk.ukim.finki.productcatalog.domain.models.Category;
import mk.ukim.finki.productcatalog.domain.models.Image;
import mk.ukim.finki.productcatalog.repository.CategoryRepository;
import mk.ukim.finki.productcatalog.service.CategoryService;
import mk.ukim.finki.productcatalog.service.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ImageService imageService;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ImageService imageService) {
        this.categoryRepository = categoryRepository;
        this.imageService = imageService;
    }

    @Override
    public Category findById(Long id) {
        return this.categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id.toString()));
    }

    @Override
    public Category insert(CreateCategoryDto dto) throws IOException {
        String imgUrl = this.imageService.upload(dto.getImage());
        Category c = new Category(CategoryType.valueOf(dto.getType()), dto.getName(), imgUrl);
        return this.categoryRepository.save(c);
    }
}
