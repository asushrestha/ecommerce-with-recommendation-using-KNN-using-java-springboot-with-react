package nce.majorproject.repositories;

import nce.majorproject.dto.UserProfileResponse;
import nce.majorproject.entities.Admin;
import nce.majorproject.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    @Query(value = "select u from User u where u.userName=?1")
    Optional<User> validateUserName(String userName);

    @Query(value = "select u from User u where u.userName=?1 and u.pasword=?2")
    Optional<User> authenticateUserCredential(String username, String password);

    @Query(value = "select u from User u where u.id=?1")
    User getUserProfile(Long id);

    @Query(value = "select u from User u where u.id=?1")
    Optional<User>  validateUserById(Long userid);

    @Query(value = "select user from User user")
    List<User> getAllUsers();

    @Query(value = "select user from User user where user.id NOT  in ?1")
    List<User>findOtherUsers(Long userId);

    @Query(value = "select count(user) from User user where user.gender=:gender")
    int findUserBasedOnGender(String gender);
}
