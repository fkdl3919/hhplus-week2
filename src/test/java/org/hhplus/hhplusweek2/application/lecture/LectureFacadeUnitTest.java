package org.hhplus.hhplusweek2.application.lecture;

import static org.mockito.Mockito.*;
import org.hhplus.hhplusweek2.application.lecture.dto.LectureCommand;
import org.hhplus.hhplusweek2.application.lecture.dto.LectureDto;
import org.hhplus.hhplusweek2.domain.lecture.Lecture;
import org.hhplus.hhplusweek2.domain.lecture.LectureRepository;
import org.hhplus.hhplusweek2.domain.lecture.LectureService;
import org.hhplus.hhplusweek2.domain.lecturebooking.LectureBooking;
import org.hhplus.hhplusweek2.domain.lecturebooking.LectureBookingService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class LectureFacadeUnitTest {

    @InjectMocks
    private LectureFacade lectureFacade;

    @Mock
    private LectureService lectureService;

    @Mock
    private LectureBookingService lectureBookingService;

    /**
     * 특강 신청 API
     * - test case 1
     *  - 동일한 user가 이미 해당  특강 에 신청한 경우
     */
    @Test
    @DisplayName("특강신청 - 동일한 user가 이미 해당 특강 에 신청한 경우 IllegalArgumentException 발생")
    public void bookLecture1(){
        // given
        long lectureId = 1L;
        long userId = 1L;
        LectureCommand lectureCommand = new LectureCommand(userId, lectureId);

        Lecture lecture = new Lecture();
        lecture.setId(lectureId);

        LectureBooking lectureBooking = new LectureBooking();
        lectureBooking.setId(any());
        lectureBooking.setLectureId(lectureCommand.lectureId());
        lectureBooking.setUserId(lectureCommand.userId());

        // stub
        when(lectureService.findById(lectureCommand.lectureId())).thenReturn(lecture);

        // 해당 특강의 유저가 존재하는 경우
        when(lectureBookingService.findByLectureIdAndUserId(lectureCommand.lectureId(), lectureCommand.userId())).thenReturn(lectureBooking);

        // when
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> lectureFacade.bookLecture(lectureCommand));

        // then
        assertEquals("이미 신청한 유저입니다.", ex.getMessage());

    }

    /**
     * 특강 신청 API
     * - test case 2
     *  - 특강 신청 인원이 이미 30명을 초과한 경우
     */
    @Test
    @DisplayName("특강신청 - 특강 신청 인원이 이미 30명을 초과한 경우 IllegalArgumentException 발생")
    public void bookLecture2(){
        // given
        long lectureId = 1L;
        long userId = 1L;
        int lectureCapacity = 0;
        LectureCommand lectureCommand = new LectureCommand(userId, lectureId);

        Lecture lecture = new Lecture();
        lecture.setId(lectureId);
        lecture.setCapacity(lectureCapacity);

        LectureBooking lectureBooking = new LectureBooking();
        lectureBooking.setId(any());
        lectureBooking.setLectureId(lectureCommand.lectureId());
        lectureBooking.setUserId(lectureCommand.userId());

        // stub
        when(lectureService.findById(lectureCommand.lectureId())).thenReturn(lecture);
        when(lectureBookingService.findByLectureIdAndUserId(lectureCommand.lectureId(), lectureCommand.userId())).thenReturn(null);

        // when
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> lectureFacade.bookLecture(lectureCommand));

        // then
        assertEquals("정원이 초과된 특강입니다.", ex.getMessage());

    }

    /**
     * 특강 신청 API
     * - test case 3
     *  - 특강 신청 후 특강의 정원 감소 검증
     */
    @Test
    @DisplayName("특강신청 - 특강 신청 후 특강의 정원 감소 검증")
    public void bookLecture3(){
        // given
        long lectureId = 1L;
        long userId = 1L;
        int lectureCapacity = 30;
        LectureCommand lectureCommand = new LectureCommand(userId, lectureId);

        Lecture lecture = new Lecture();
        lecture.setId(lectureId);
        lecture.setCapacity(lectureCapacity);

        LectureBooking lectureBooking = new LectureBooking();
        lectureBooking.setId(any());
        lectureBooking.setLectureId(lectureCommand.lectureId());
        lectureBooking.setUserId(lectureCommand.userId());

        // stub
        when(lectureService.findById(lectureCommand.lectureId())).thenReturn(lecture);
        when(lectureBookingService.findByLectureIdAndUserId(lectureCommand.lectureId(), lectureCommand.userId())).thenReturn(null);

        // when
        lectureFacade.bookLecture(lectureCommand);

        // then
        // 신청 후 정원 1 감소
        assertEquals(--lectureCapacity, lecture.getCapacity());
    }

}
