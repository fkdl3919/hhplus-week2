package org.hhplus.hhplusweek2.domain.lecturebooking;

public interface LectureBookingRepository {

    LectureBooking save(LectureBooking lectureBooking);

    LectureBooking findByLectureIdAndUserId(Long lectureId, Long userId);
}
