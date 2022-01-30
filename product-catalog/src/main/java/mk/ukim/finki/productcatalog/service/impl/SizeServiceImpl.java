package mk.ukim.finki.productcatalog.service.impl;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.productcatalog.domain.dtos.request.CreateSizeDto;
import mk.ukim.finki.productcatalog.domain.exceptions.SizeNotFoundException;
import mk.ukim.finki.productcatalog.domain.models.Size;
import mk.ukim.finki.productcatalog.repository.SizeRepository;
import mk.ukim.finki.productcatalog.service.SizeService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SizeServiceImpl implements SizeService {
    private final SizeRepository sizeRepository;

    @Override
    public Size findById(Long id) {
        return this.sizeRepository.findSizeByIdAndIsDeletedFalse(id).orElseThrow(() -> new SizeNotFoundException(id));
    }

    @Override
    public Size insert(CreateSizeDto dto) {
        return this.sizeRepository.save(new Size(dto.getSize()));
    }
}
