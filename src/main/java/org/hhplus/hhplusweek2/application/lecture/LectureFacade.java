package org.hhplus.hhplusweek2.application.lecture;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hhplus.hhplusweek2.application.lecture.dto.LectureCommand;
import org.hhplus.hhplusweek2.application.lecture.dto.LectureDto;
import org.hhplus.hhplusweek2.domain.lecture.Lecture;
import org.hhplus.hhplusweek2.domain.lecture.LectureService;
import org.hhplus.hhplusweek2.domain.lecturebooking.LectureBooking;
import org.hhplus.hhplusweek2.domain.lecturebooking.LectureBookingService;
import org.springframework.stereotype.Service;

/**
 * 도메인에 대한 use case
 */
@Service
@RequiredArgsConstructor
public class LectureFacade {

    private final LectureService lectureService;

    private final LectureBookingService lectureBookingService;

    public void bookLecture(LectureCommand lectureCommand) {
        Lecture lecture = lectureService.findById(lectureCommand.lectureId());

        LectureBooking lectureBooking = lectureBookingService.findByLectureIdAndUserId(lectureCommand.lectureId(), lectureCommand.userId());

        if(lectureBooking != null){
            throw new IllegalArgumentException("이미 신청한 유저입니다.");
        }

        if(lecture.getCapacity() == 0 ){
            throw new IllegalArgumentException("정원이 초과된 특강입니다.");
        }

        // 신청 후 정원 1 감소
        lecture.decreaseCapacity();

        lectureService.save(lecture);
        lectureBookingService.save(lectureCommand.toLectureBooking());

    }

    public List<LectureDto> getAvailableLectures(LocalDate date){
        return LectureDto.toDtos(lectureService.findAvailableLecturesByDate(date));
    }

    public List<LectureDto> getBookedLectures(Long userId){
        return LectureDto.toDtos(lectureService.findBookedLecturesByUserId(userId));
    }


}
