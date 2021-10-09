package mk.ukim.finki.productcatalog.service;

import mk.ukim.finki.productcatalog.domain.dtos.request.CreateBrandDto;
import mk.ukim.finki.productcatalog.domain.dtos.response.GetBrandDto;
import mk.ukim.finki.productcatalog.domain.models.Brand;

import java.util.List;

public interface BrandService {
    Brand insert(CreateBrandDto dto);

    Brand edit(Long id);

    Brand findBrandById(Long brandId);

    List<GetBrandDto> findAllBrands();
}
