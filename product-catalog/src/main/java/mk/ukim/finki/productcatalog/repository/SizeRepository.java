package mk.ukim.finki.productcatalog.repository;

import mk.ukim.finki.productcatalog.domain.models.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SizeRepository extends JpaRepository<Size, Long> {
    Optional<Size> findSizeByIdAndIsDeletedFalse(Long sizeId);
}
