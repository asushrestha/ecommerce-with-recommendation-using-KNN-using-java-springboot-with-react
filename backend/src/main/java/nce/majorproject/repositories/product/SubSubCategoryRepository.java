package nce.majorproject.repositories.product;

import nce.majorproject.entities.Product.SubSubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubSubCategoryRepository extends JpaRepository<SubSubCategory,Long> {

    @Query(value = "select s from SubSubCategory  s where s.name = :subSubCategoryName")
    Optional<SubSubCategory> findByName(String subSubCategoryName);
}
