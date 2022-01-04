package nce.majorproject.recommendation.repository;

import nce.majorproject.entities.User;
import nce.majorproject.recommendation.entity.UserProductData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserDataTrackerRepository extends JpaRepository<UserProductData, Long> {
    @Query(value = "select userProductData from UserProductData userProductData where userProductData.userId=:userId order by userProductData.id desc ")
    List<UserProductData> findByUserId(User userId);

    @Query(value = "select userProductData from UserProductData  userProductData where userProductData.userId.dob between :minDate and :maxDate")
    List<UserProductData> findByUserAge(LocalDate minDate, LocalDate maxDate);
}
