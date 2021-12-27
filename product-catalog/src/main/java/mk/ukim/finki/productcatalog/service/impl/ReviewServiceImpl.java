package mk.ukim.finki.productcatalog.service.impl;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.productcatalog.domain.dtos.request.CreateReviewDto;
import mk.ukim.finki.productcatalog.domain.dtos.response.GetReviewDto;
import mk.ukim.finki.productcatalog.domain.dtos.response.GetUserInfoDto;
import mk.ukim.finki.productcatalog.domain.mappers.ReviewToGetReviewDtoMapper;
import mk.ukim.finki.productcatalog.domain.models.Product;
import mk.ukim.finki.productcatalog.domain.models.Review;
import mk.ukim.finki.productcatalog.repository.ReviewRepository;
import mk.ukim.finki.productcatalog.service.ProductService;
import mk.ukim.finki.productcatalog.service.ReviewService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final RestTemplate restTemplate;
    private final ReviewToGetReviewDtoMapper reviewToGetReviewDtoMapper;
    private final ProductService productService;

    @Override
    public List<GetReviewDto> findReviewsForProduct(Long productId) {
        List<Review> reviews = this.reviewRepository.findAllByProductIdAndIsDeletedFalseOrderByReviewedOnDesc(productId);
        return getReviewsWithUserInfo(reviews);
    }

    @Override
    public List<GetReviewDto> findReviewsByRating(Long productId, Float rating) {
        List<Review> reviews = this.reviewRepository.findAllByProductIdAndRatingAndIsDeletedFalseOrderByReviewedOnDesc(productId, rating);
        return getReviewsWithUserInfo(reviews);
    }

    @Override
    public Review addReviewToProduct(CreateReviewDto dto) {
        Product product = this.productService.findProductById(dto.getProductId());
        return this.reviewRepository.save(
                new Review(dto.getRating(), dto.getReview(), dto.getUserId(), product)
        );
    }

    private List<GetReviewDto> getReviewsWithUserInfo(List<Review> reviews) {
        List<GetReviewDto> mappedReviews = new ArrayList<>();
        reviews.forEach(r -> {
            GetReviewDto reviewDto = this.reviewToGetReviewDtoMapper.toGetReview(r);
            GetUserInfoDto userInfoDto = this.restTemplate
                    .getForObject(String.format("http://USERS-API-GATEWAY/users/%s", r.getUserId()),
                            GetUserInfoDto.class);
            if(userInfoDto != null) {
                reviewDto = this.reviewToGetReviewDtoMapper.updateUserInfo(reviewDto, userInfoDto);
                mappedReviews.add(reviewDto);
            }
        });
        return mappedReviews;
    }

}
