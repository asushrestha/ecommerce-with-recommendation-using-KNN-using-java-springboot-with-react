package nce.majorproject.repositories.product;

import nce.majorproject.entities.Product.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

    @Query(value = "select c from Category c where c.id=?1")
    Optional<Category> validateById(Long id);
}
