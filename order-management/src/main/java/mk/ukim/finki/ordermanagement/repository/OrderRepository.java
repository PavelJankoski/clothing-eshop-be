package mk.ukim.finki.ordermanagement.repository;

import mk.ukim.finki.ordermanagement.domain.models.Order;
import mk.ukim.finki.ordermanagement.domain.projections.GetProductExistsInShoppingCart;
import mk.ukim.finki.ordermanagement.util.QueryConstants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query(value = QueryConstants.isProductInShoppingCart, nativeQuery = true)
    GetProductExistsInShoppingCart isProductInShoppingCart(Long userId, Long productId);
}
