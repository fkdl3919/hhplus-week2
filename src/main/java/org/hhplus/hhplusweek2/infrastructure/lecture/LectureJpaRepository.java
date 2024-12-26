package org.hhplus.hhplusweek2.infrastructure.lecture;

import java.time.LocalDate;
import java.util.List;
import org.hhplus.hhplusweek2.domain.lecture.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LectureJpaRepository extends JpaRepository<Lecture, Long> {

    @Query(value = "select a from Lecture a where a.date = :date and a.capacity != 0")
    List<Lecture> findAvailableLecturesByDate(LocalDate date);

}
