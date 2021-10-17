package mk.ukim.finki.usermanagement.service.impl;

import mk.ukim.finki.usermanagement.domain.models.Image;
import mk.ukim.finki.usermanagement.repository.ImageRepository;
import mk.ukim.finki.usermanagement.service.ImageService;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;

    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public Image save(String url) {
        return imageRepository.save(new Image(url));
    }
}
