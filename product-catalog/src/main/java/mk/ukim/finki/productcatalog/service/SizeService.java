package mk.ukim.finki.productcatalog.service;

import mk.ukim.finki.productcatalog.domain.dtos.request.CreateSizeDto;
import mk.ukim.finki.productcatalog.domain.models.Size;

public interface SizeService {
    Size findById(Long id);
    Size insert(CreateSizeDto dto);
}
