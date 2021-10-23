package mk.ukim.finki.productcatalog.repository;

import mk.ukim.finki.productcatalog.domain.models.Product;
import mk.ukim.finki.productcatalog.util.QueryConstants;
import org.checkerframework.checker.nullness.Opt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByIsDeletedFalseAndCategoryId(Long categoryId);

    List<Product> findAllByIsDeletedFalseAndCategoryIdAndPriceBetween(Long categoryId, Float low, Float high);

    Optional<Product> findProductByIdAndIsDeletedFalse(Long id);

    Optional<Product> findProductByCodeAndIsDeletedFalse(String code);

    @Query(value = QueryConstants.isProductInWishlist, nativeQuery = true)
    Boolean isProductInWishlist(Long userId, Long productId);

    @Query("select p from Product p where ((lower(p.description) like %:searchText% or " +
            "lower(p.code) like %:searchText% or " +
            "lower(p.name) like %:searchText% or " +
            "lower(p.brand.name) like %:searchText%)) and p.isDeleted=false")
    List<Product> findAllSearchedProducts(String searchText);


}
