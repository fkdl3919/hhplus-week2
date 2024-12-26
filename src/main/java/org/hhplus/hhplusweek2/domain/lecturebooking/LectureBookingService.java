package org.hhplus.hhplusweek2.domain.lecturebooking;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LectureBookingService {

    private final LectureBookingRepository lectureBookingRepository;

    public LectureBooking findByLectureIdAndUserId(Long lectureId, Long userId) {
        return lectureBookingRepository.findByLectureIdAndUserId(lectureId, userId);
    }

    public List<LectureBooking> findByUserId(Long userId) {
        return lectureBookingRepository.findByUserId(userId);
    }

    public List<LectureBooking> findByLectureId(Long lectureId) {
        return lectureBookingRepository.findByLectureId(lectureId);
    }

    public LectureBooking save(LectureBooking lectureBooking) {
        return lectureBookingRepository.save(lectureBooking);
    }
}
