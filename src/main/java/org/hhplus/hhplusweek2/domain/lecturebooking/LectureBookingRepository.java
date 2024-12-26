package org.hhplus.hhplusweek2.domain.lecturebooking;

import java.util.List;

public interface LectureBookingRepository {

    LectureBooking save(LectureBooking lectureBooking);

    LectureBooking findByLectureIdAndUserId(Long lectureId, Long userId);

    List<LectureBooking> findByUserId(Long userId);

    List<LectureBooking> findByLectureId(Long lectureId);
}
