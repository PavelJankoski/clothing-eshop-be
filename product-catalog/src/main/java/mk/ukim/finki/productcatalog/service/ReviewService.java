package mk.ukim.finki.productcatalog.service;

import mk.ukim.finki.productcatalog.domain.dtos.request.CreateReviewDto;
import mk.ukim.finki.productcatalog.domain.dtos.response.GetReviewDto;
import mk.ukim.finki.productcatalog.domain.models.Review;

import java.util.List;

public interface ReviewService {
    List<GetReviewDto> findReviewsForProduct(Long productId);
    List<GetReviewDto> findReviewsByRating(Long productId, Float rating);
    Review addReviewToProduct(CreateReviewDto dto);
    Boolean didUserReviewProduct(Long productId, Long userId);
}
