package nce.majorproject.repositories;

import nce.majorproject.entities.Comment;
import nce.majorproject.entities.Product.Product;
import nce.majorproject.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE Comment c set c.isDisabled=true where c.id=?1")
    int disableComment(Long id);

    @Query(value = "select c from Comment  c where c.id=?1")
    Optional<Comment> verifyById(Long id);

    @Query(value = "select count (c) from Comment c  where c.productId=?1")
    int countCommentById(Product product);

    @Query(value = "select c from Comment c where c.productId=?1 and c.isDisabled=?2 and c.isDeleted=?3 order by c.addedDate desc ")
    List<Comment> getCommentsByPostId(Product productId, boolean disable, boolean delete);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Comment c set c.isDisabled=false where c.id=?1")
    int activateComment(Long id);

    @Transactional
    @Modifying
    @Query(value = "update Comment c set c.isDeleted=true where c.id=?1")
    int deleteComment(Long id);

    @Query(value = "select c from Comment c where c.userId=?1 and c.productId=?2")
    Optional<Comment> findByUserAndProduct(User user, Product product);
}
