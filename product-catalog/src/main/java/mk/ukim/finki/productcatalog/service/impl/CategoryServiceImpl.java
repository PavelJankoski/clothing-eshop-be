package mk.ukim.finki.productcatalog.service.impl;

import mk.ukim.finki.productcatalog.domain.dtos.request.CreateCategoryDto;
import mk.ukim.finki.productcatalog.domain.dtos.response.GetCategoryDto;
import mk.ukim.finki.productcatalog.domain.enums.CategoryType;
import mk.ukim.finki.productcatalog.domain.exceptions.CategoryNotFoundException;
import mk.ukim.finki.productcatalog.domain.mappers.CategoryToGetCategoryDtoMapper;
import mk.ukim.finki.productcatalog.domain.models.Category;
import mk.ukim.finki.productcatalog.repository.CategoryRepository;
import mk.ukim.finki.productcatalog.service.CategoryService;
import mk.ukim.finki.productcatalog.service.ImageService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ImageService imageService;
    private final CategoryToGetCategoryDtoMapper mapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ImageService imageService, CategoryToGetCategoryDtoMapper mapper) {
        this.categoryRepository = categoryRepository;
        this.imageService = imageService;
        this.mapper = mapper;
    }

    @Override
    public List<GetCategoryDto> findAllCategories() {
        List<Category> categories = this.categoryRepository.findAllByIsDeletedFalse();

        return this.mapper.toGetBrandDtoList(categories);
    }

    @Override
    public Category findById(Long id) {
        return this.categoryRepository.findCategoryByIdAndIsDeletedFalse(id).orElseThrow(() -> new CategoryNotFoundException(id.toString()));
    }

    @Override
    public Category insert(CreateCategoryDto dto) throws IOException {
        String imgUrl = this.imageService.uploadForUrl(dto.getImage());
        Category c = new Category(CategoryType.valueOf(dto.getType()), dto.getName(), imgUrl);
        return this.categoryRepository.save(c);
    }
}
