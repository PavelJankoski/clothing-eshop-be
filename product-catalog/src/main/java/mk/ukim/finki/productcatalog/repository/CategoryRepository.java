package mk.ukim.finki.productcatalog.repository;

import mk.ukim.finki.productcatalog.domain.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
