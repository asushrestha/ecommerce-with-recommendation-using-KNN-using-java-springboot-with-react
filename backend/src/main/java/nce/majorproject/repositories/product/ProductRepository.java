package nce.majorproject.repositories.product;

import nce.majorproject.entities.Product.Product;
import nce.majorproject.entities.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query(value = "select p from Product p order by p.addedDate desc")
    List<Product> findLatestAddedProduct();

    @Query(value="select p from Product p where p.id=?1")
    Optional<Product> validateProductById(Long id);

    @Query(value = "select p from Product p order by p.addedDate desc")
    List<Product> randomProduct();

    @Query(value = "select p from Product p where LOWER(p.subSubCategory.name)=LOWER(:subSubCategory)")
    List<Product>findProductBySubSubcategory(String subSubCategory);

    @Query(value = "select p from Product p where LOWER(p.subSubCategory.name)=LOWER(:subSubCategory) and LOWER(p.category.name)=LOWER(:category)")
    List<Product>findProductBySubSubcategory(String subSubCategory,String category);

    @Query(value = "select p from Product p where LOWER(p.category.name)=LOWER(:category) AND LOWER(p.subCategory.name)=LOWER(:subCategory)")
    List<Product>filter(String category, String subCategory);

    @Query(value = "select p from Product p where LOWER(p.category.name)=LOWER(:category)")
    List<Product>filterCat(String category);

    @Query(value = "select  p from Product  p  order by p.addedDate desc")
    List<Product> getTopFive(Pageable pageable);

}
