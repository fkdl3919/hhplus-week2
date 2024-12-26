package org.hhplus.hhplusweek2.domain.lecturebooking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LectureBookingService {

    private final LectureBookingRepository lectureBookingRepository;

    public LectureBooking findByLectureIdAndUserId(Long lectureId, Long userId) {
        return lectureBookingRepository.findByLectureIdAndUserId(lectureId, userId);
    }

    public LectureBooking save(LectureBooking lectureBooking) {
        return lectureBookingRepository.save(lectureBooking);
    }
}
