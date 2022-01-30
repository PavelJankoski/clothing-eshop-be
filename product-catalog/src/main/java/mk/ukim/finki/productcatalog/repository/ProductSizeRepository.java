package mk.ukim.finki.productcatalog.repository;

import mk.ukim.finki.productcatalog.domain.models.ProductSize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductSizeRepository extends JpaRepository<ProductSize, Long> {
    Optional<ProductSize> findProductSizeByProductIdAndSizeIdAndIsDeletedFalse(Long productId, Long sizeId);
}
