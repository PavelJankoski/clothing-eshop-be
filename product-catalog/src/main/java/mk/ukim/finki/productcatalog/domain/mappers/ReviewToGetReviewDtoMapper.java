package mk.ukim.finki.productcatalog.domain.mappers;

import mk.ukim.finki.productcatalog.domain.dtos.response.GetProductDto;
import mk.ukim.finki.productcatalog.domain.dtos.response.GetReviewDto;
import mk.ukim.finki.productcatalog.domain.dtos.response.GetUserInfoDto;
import mk.ukim.finki.productcatalog.domain.models.Product;
import mk.ukim.finki.productcatalog.domain.models.Review;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReviewToGetReviewDtoMapper {
    public GetReviewDto updateUserInfo(GetReviewDto reviewDto, GetUserInfoDto userInfoDto) {
        reviewDto.setFullName(String.format("%s %s", userInfoDto.getName(), userInfoDto.getSurname()));
        reviewDto.setImageUrl(userInfoDto.getImage());
        return reviewDto;
    }

    public GetReviewDto toGetReview(Review review) {
        GetReviewDto dto = new GetReviewDto();
        dto.setRating(review.getRating());
        dto.setReview(review.getReview());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM, yyyy");
        dto.setReviewedOn(review.getReviewedOn().format(formatter));
        return dto;
    }

    public List<GetReviewDto> toGetReviewsList(List<Review> reviews){
        return reviews.stream().map(this::toGetReview).collect(Collectors.toList());
    }
}
