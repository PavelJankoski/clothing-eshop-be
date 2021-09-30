package mk.ukim.finki.productcatalog.service;

import mk.ukim.finki.productcatalog.domain.models.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface ImageService {
    Image insert(MultipartFile image);
    File convertToFile(MultipartFile multipartFile, String fileName) throws IOException;
    String uploadFile(File file, String fileName) throws IOException;
    String getExtension(String fileName);
    Image upload(MultipartFile multipartFile);
}
