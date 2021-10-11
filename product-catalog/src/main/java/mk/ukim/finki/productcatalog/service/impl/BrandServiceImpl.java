package mk.ukim.finki.productcatalog.service.impl;

import mk.ukim.finki.productcatalog.domain.dtos.request.CreateBrandDto;
import mk.ukim.finki.productcatalog.domain.dtos.request.EditBrandDto;
import mk.ukim.finki.productcatalog.domain.dtos.response.GetBrandDto;
import mk.ukim.finki.productcatalog.domain.exceptions.BrandNotFoundException;
import mk.ukim.finki.productcatalog.domain.mappers.BrandToGetBrandDtoMapper;
import mk.ukim.finki.productcatalog.domain.models.Brand;
import mk.ukim.finki.productcatalog.repository.BrandRepository;
import mk.ukim.finki.productcatalog.service.BrandService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;
    private final BrandToGetBrandDtoMapper brandMapper;

    public BrandServiceImpl(BrandRepository brandRepository, BrandToGetBrandDtoMapper brandMapper) {
        this.brandRepository = brandRepository;
        this.brandMapper = brandMapper;
    }

    @Override
    public Brand insert(CreateBrandDto dto) {
        return this.brandRepository.save(new Brand(dto.getName()));
    }

    @Override
    public Brand edit(Long id, EditBrandDto dto) {
        Brand brand = this.findBrandById(id);
        brand.setName(dto.getName());
        return this.brandRepository.save(brand);
    }

    @Override
    public Brand findBrandById(Long brandId) {
        return this.brandRepository.findBrandByIdAndIsDeletedFalse(brandId).orElseThrow(() -> new BrandNotFoundException(brandId.toString()));
    }

    @Override
    public List<GetBrandDto> findAllBrands() {
        List<Brand> brands = this.brandRepository.findAllByIsDeletedFalse();
        return brandMapper.toGetBrandDtoList(brands);
    }
}
