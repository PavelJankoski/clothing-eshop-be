package mk.ukim.finki.productcatalog.repository;

import mk.ukim.finki.productcatalog.domain.models.Product;
import mk.ukim.finki.productcatalog.util.QueryConstants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByIsDeletedFalseAndCategoryId(Long categoryId);

    @Query(value = QueryConstants.isProductInWishlist, nativeQuery = true)
    Boolean isProductInWishlist(Long userId, Long productId);
}
