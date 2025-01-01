package org.hhplus.hhplusweek2.infrastructure.lecture;

import jakarta.persistence.LockModeType;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.hhplus.hhplusweek2.domain.lecture.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

public interface LectureJpaRepository extends JpaRepository<Lecture, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select a from Lecture a where a.id = :id")
    Optional<Lecture> findByIdWithLock(Long id);

    @Query("select a from Lecture a where a.date = :date and a.capacity != 0")
    List<Lecture> findAvailableLecturesByDate(LocalDate date);

    /**
     * Lecture와 LectureBooking를 join하여 주어진 userId로 검색
     * @return
     */
    @Query("select a from Lecture a join LectureBooking b on a.id = b.lectureId where b.userId = :userId order by b.id desc ")
    List<Lecture> findBookedLecturesByUserId(Long userId);

}
