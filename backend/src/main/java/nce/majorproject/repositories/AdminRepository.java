package nce.majorproject.repositories;

import nce.majorproject.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Long> {

    @Query(value = "select a from Admin a where a.userName=?1")
    Optional<Admin> validateUserName(String userName);

    @Query(value = "select a from Admin a where a.userName=?1 and a.password=?2")
    Optional<Admin> authenticateAdminCredential(String username,String password);

    @Query(value = "Select a from Admin  a where a.id=:id")
    Optional<Admin> validateAdminId(Long id);


}
