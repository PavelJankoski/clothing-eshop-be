package mk.ukim.finki.productcatalog.web;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.productcatalog.domain.dtos.request.CreateReviewDto;
import mk.ukim.finki.productcatalog.domain.dtos.response.GetReviewDto;
import mk.ukim.finki.productcatalog.domain.models.Review;
import mk.ukim.finki.productcatalog.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/reviews")
@CrossOrigin(value = "*")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping(value = "/{productId}")
    public ResponseEntity<List<GetReviewDto>> getReviewsForProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(this.reviewService.findReviewsForProduct(productId));
    }

    @GetMapping(value = "/by-rating/{productId}")
    public ResponseEntity<List<GetReviewDto>> getReviewsByRatingForProduct(
            @PathVariable Long productId,
            @RequestParam Float rating
    ) {
        return ResponseEntity.ok(this.reviewService.findReviewsByRating(productId, rating));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Review> createReview(@RequestBody CreateReviewDto dto) {
        return ResponseEntity.ok(this.reviewService.addReviewToProduct(dto));
    }
}
