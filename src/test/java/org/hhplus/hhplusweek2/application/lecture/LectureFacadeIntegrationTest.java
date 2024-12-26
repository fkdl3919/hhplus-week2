package org.hhplus.hhplusweek2.application.lecture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.hhplus.hhplusweek2.application.lecture.dto.LectureCommand;
import org.hhplus.hhplusweek2.application.lecture.dto.LectureDto;
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

    private void lectureSetUp(int repeat, LocalDate date) {
        for (int i = 0; i < repeat; i++) {
            int seq = i + 1;
            lectureService.save(
                new Lecture(null, "특강"+ seq, "강연자"+seq, date, 30)
            );
        }
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

    @Test
    @DisplayName("특강 신청 가능 목록 통합 테스트")
    public void availableLecturesIntegrationTest(){
        // given
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        DateTimeFormatter validDtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        int todayLectureCount = 10;
        int tomorrowLectureCount = 20;

        // 미리 입력
        lectureSetUp(todayLectureCount, today);
        lectureSetUp(tomorrowLectureCount, tomorrow);

        // when
        List<LectureDto> lectures = lectureFacade.getAvailableLectures(today);
        List<LectureDto> lectures2 = lectureFacade.getAvailableLectures(tomorrow);

        // then
        // 각 날짜 별 특강 수 검증
        assertEquals(todayLectureCount, lectures.size());
        assertEquals(tomorrowLectureCount, lectures2.size());

    }

    @Test
    @DisplayName("특강 신청 완료 목록 조회 통합 테스트")
    public void bookedLecturesIntegrationTest(){
        // given
        long userId = 1L;

        LocalDate today = LocalDate.now();
        LocalDate tomorrow = LocalDate.now().plusDays(1);

        int todayLectureCount = 10;
        int tomorrowLectureCount = 20;

        // 특강 미리 입력
        lectureSetUp(todayLectureCount, today);
        lectureSetUp(tomorrowLectureCount, tomorrow);

        List<Lecture> lectures = lectureService.findAvailableLecturesByDate(today);

        // 입력해둔 특강에 user 등록 - today 특강만 입력
        for (Lecture lecture : lectures) {
            lectureBookingService.save(
                new LectureBooking(lecture.getId(), userId)
            );
        }

        // when
        List<LectureDto> findLectures = lectureFacade.getBookedLectures(userId);

        // then
        // 각 날짜 별 특강 수 검증
        assertEquals(todayLectureCount, findLectures.size());

    }

    /**
     * 동시성
     * test case 1
     * - 여러 명의 유저가 같은 특강에 동시에 신청할 경우
     * - 동시에 동일한 특강에 대해 40명이 신청했을 때, 30명만 성공하는 것을 검증
     *
     */
    @Test
    @DisplayName("동시성 테스트 - 여러 명의 유저가 같은 특강에 동시에 신청할 경우")
    public void concurrentBookLectureIntegrationTest2() throws InterruptedException {
        // given
        int threadCount = 40;

        // 정원
        int capacity = 30;
        long userId1 = 1L;
        long lectureId = lecture.getId();

        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        // when
        for (int i = 0; i < threadCount; i++) {
            int finalI = i;
            executorService.submit(() -> {
                try{
                    lectureFacade.bookLecture(new LectureCommand(lectureId, userId1 + finalI));
                } catch(IllegalArgumentException e) {

                } catch(Exception e) {
                    e.printStackTrace();
                }finally {
                    countDownLatch.countDown();
                }
                return null;
            });
        }
        countDownLatch.await();

        Lecture findLecture = lectureService.findByIdWithLock(lecture.getId());
        List<LectureBooking> findLectureBookings = lectureBookingService.findByLectureId(lectureId);

        // then
        assertEquals(0, findLecture.getCapacity());
        assertEquals(capacity, findLectureBookings.size());
    }

    /**
     * 동시성
     * test case 2
     * - 동일한 유저 정보로 같은 특강을 5번 신청했을 때, 1번만 성공하는 것을 검증
     */
    @Test
    @DisplayName("동시성 테스트 - 동일한 유저 정보로 같은 특강을 5번 신청했을 때, 1번만 성공하는 것을 검증")
    public void concurrentBookLectureIntegrationTest() throws InterruptedException {
        // given
        int threadCount = 5;

        // 정원
        int capacity = 30;
        long userId1 = 1L;
        long lectureId = lecture.getId();

        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        // when

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try{
                    lectureFacade.bookLecture(new LectureCommand(lectureId, userId1));
                } catch(Exception e) {
                    e.printStackTrace();
                }finally {
                    countDownLatch.countDown();
                }
                return null;
            });
        }
        countDownLatch.await();

        Lecture findLecture = lectureService.findByIdWithLock(lecture.getId());
        List<LectureBooking> findLectureBookings = lectureBookingService.findByUserId(userId1);

        // then
        assertEquals(capacity - 1, findLecture.getCapacity());
        assertEquals(1, findLectureBookings.size());
    }


}
