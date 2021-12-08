package mk.ukim.finki.usermanagement.service;

import mk.ukim.finki.usermanagement.domain.models.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    String uploadForUrl(MultipartFile multipartFile) throws IOException;
    Image save(String url);
}
