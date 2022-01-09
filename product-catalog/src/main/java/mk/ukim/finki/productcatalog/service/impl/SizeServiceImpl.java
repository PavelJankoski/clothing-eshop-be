package mk.ukim.finki.productcatalog.service.impl;

import mk.ukim.finki.productcatalog.domain.dtos.request.CreateSizeDto;
import mk.ukim.finki.productcatalog.domain.models.Size;
import mk.ukim.finki.productcatalog.repository.SizeRepository;
import mk.ukim.finki.productcatalog.service.SizeService;
import org.springframework.stereotype.Service;

@Service
public class SizeServiceImpl implements SizeService {
    private final SizeRepository sizeRepository;

    public SizeServiceImpl(SizeRepository sizeRepository) {
        this.sizeRepository = sizeRepository;
    }

    @Override
    public Size insert(CreateSizeDto dto) {
        return this.sizeRepository.save(new Size(dto.getSize(), dto.getQuantity()));
    }
}
