package mk.ukim.finki.productcatalog.repository;

import mk.ukim.finki.productcatalog.domain.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByProductIdAndIsDeletedFalseOrderByReviewedOnDesc(Long productId);
    List<Review> findAllByProductIdAndRatingAndIsDeletedFalseOrderByReviewedOnDesc(Long productId, Float rating);
    Boolean existsReviewByProductIdAndUserId(Long productId, Long userId);
}
