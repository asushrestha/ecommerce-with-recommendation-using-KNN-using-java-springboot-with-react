package nce.majorproject.recommendation.repository;

import nce.majorproject.recommendation.entity.DataSetReferer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DataSetRefererRepository extends JpaRepository<DataSetReferer,Long> {

    @Query(value = "select data from DataSetReferer  data where age between :lower and :upper ")
    List<DataSetReferer> findByAgeInterval(Long lower, Long upper);
}
