package org.hhplus.hhplusweek2.infrastructure.lecturebooking;

import java.util.List;
import org.hhplus.hhplusweek2.domain.lecture.Lecture;
import org.hhplus.hhplusweek2.domain.lecturebooking.LectureBooking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureBookingJpaRepository extends JpaRepository<LectureBooking, Long> {

    LectureBooking findByLectureIdAndUserId(Long lectureId, Long userId);

    List<LectureBooking> findByUserId(Long userId);

    List<LectureBooking> findByLectureId(Long lectureId);
}
