package mk.ukim.finki.productcatalog.repository;

import mk.ukim.finki.productcatalog.domain.models.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    List<Wishlist> findAllByUserIdAndIsDeletedFalse(Long userId);
}
