package org.hhplus.hhplusweek2.application.lecture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import org.hhplus.hhplusweek2.application.lecture.dto.LectureCommand;
import org.hhplus.hhplusweek2.domain.lecture.Lecture;
import org.hhplus.hhplusweek2.domain.lecture.LectureService;
import org.hhplus.hhplusweek2.domain.lecturebooking.LectureBooking;
import org.hhplus.hhplusweek2.domain.lecturebooking.LectureBookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LectureFacadeIntegrationTest {

    @Autowired
    private LectureFacade lectureFacade;

    @Autowired
    private LectureService lectureService;

    @Autowired
    private LectureBookingService lectureBookingService;

    private Lecture lecture;

    @BeforeEach
    public void setUp(){
        this.lecture = new Lecture();
        this.lecture.setTitle("특강1");
        this.lecture.setInstructor("강연자1");

        // 특강 저장
        lectureService.save(lecture);
    }

    @Test
    @DisplayName("특강신청 서비스 통합 테스트")
    public void bookLectureIntegrationTest(){
        // given
        long lectureId = lecture.getId();
        long userId = 1L;
        LectureCommand lectureCommand = new LectureCommand(userId, lectureId);

        // when
        lectureFacade.bookLecture(lectureCommand);

        // then
        // 특강, 유저 매핑정보
        LectureBooking findLectureBooking = lectureBookingService.findByLectureIdAndUserId(lectureId, userId);
        assertEquals(lectureId, findLectureBooking.getLectureId());
        assertEquals(userId, findLectureBooking.getUserId());

    }



}
