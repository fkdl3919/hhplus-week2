package org.hhplus.hhplusweek2.infrastructure.lecturebooking;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hhplus.hhplusweek2.domain.lecturebooking.LectureBooking;
import org.hhplus.hhplusweek2.domain.lecturebooking.LectureBookingRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LectureBookingRepositoryImpl implements LectureBookingRepository {

    private final LectureBookingJpaRepository lectureBookingJpaRepository;

    @Override
    public LectureBooking save(LectureBooking lectureBooking) {
        return lectureBookingJpaRepository.save(lectureBooking);
    }

    @Override
    public LectureBooking findByLectureIdAndUserId(Long lectureId, Long userId) {
        return lectureBookingJpaRepository.findByLectureIdAndUserId(lectureId, userId);
    }

    @Override
    public List<LectureBooking> findByUserId(Long userId) {
        return lectureBookingJpaRepository.findByUserId(userId);
    }

    @Override
    public List<LectureBooking> findByLectureId(Long lectureId) {
        return lectureBookingJpaRepository.findByLectureId(lectureId);
    }

}
