package mk.ukim.finki.productcatalog.repository;

import mk.ukim.finki.productcatalog.domain.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByProductIdAndIsDeletedFalse(Long productId);
    List<Review> findAllByProductIdAndRatingAndIsDeletedFalse(Long productId, Float rating);
}
