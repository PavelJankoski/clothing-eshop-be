package mk.ukim.finki.productcatalog.service;

import mk.ukim.finki.productcatalog.domain.models.Image;
import mk.ukim.finki.productcatalog.domain.models.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface ImageService {
    String uploadForUrl(MultipartFile multipartFile) throws IOException;
    void uploadForImage(MultipartFile image, Product product) throws IOException;
}
