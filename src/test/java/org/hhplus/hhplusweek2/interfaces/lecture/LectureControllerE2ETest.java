package org.hhplus.hhplusweek2.interfaces.lecture;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.hhplus.hhplusweek2.domain.lecture.Lecture;
import org.hhplus.hhplusweek2.domain.lecture.LectureService;
import org.hhplus.hhplusweek2.domain.lecturebooking.LectureBooking;
import org.hhplus.hhplusweek2.domain.lecturebooking.LectureBookingRepository;
import org.hhplus.hhplusweek2.interfaces.api.lecture.request.LectureRequest;
import org.hhplus.hhplusweek2.interfaces.api.lecture.response.LectureResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class LectureControllerE2ETest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LectureService lectureService;

    @Autowired
    private LectureBookingRepository lectureBookingRepository;

    @Autowired
    private ObjectMapper objectMapper;

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


    /**
     * 특강 신청 가능 목록
     * - test case 1
     *  - 조회날짜 입력 값 검증
     *  - format yyyy-MM-dd
     *
     */
    @Test
    @DisplayName("특강 신청 가능 목록 - 조회날짜 입력 값 검증 실패시 status 400")
    public void lecturesTest() throws Exception {
        String url = "/api/lectures";

        // given
        LocalDate now = LocalDate.now();
        DateTimeFormatter validDtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter invalidDtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");

        // when
        mockMvc.perform(get(url).queryParam("date", validDtf.format(now)))
            // then
            .andExpect(status().isOk());

        // when
        mockMvc.perform(get(url).queryParam("date", invalidDtf.format(now)))
            // then
            .andExpect(status().isBadRequest());

    }

    /**
     * feature1
     * 특강 신청 api e2e 테스트
     *
     */
    @Test
    @DisplayName("특강 신청 api e2e")
    public void bookLecture() throws Exception {
        String url = "/api/lectures";

        // given
        long userId = 1L;
        LectureRequest lectureRequest = new LectureRequest(lecture.getId(), userId);

        // when
        mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(lectureRequest)))
            // then
            .andExpect(status().isOk());

        // then
        // 해당 userId로 예약된 특강목록
        List<Lecture> findLecture = lectureService.findBookedLecturesByUserId(userId);
        assertEquals(1, findLecture.size());

    }


    /**
     * feature2
     * 특강 신청 가능 목록 api e2e 테스트
     *
     */
    @Test
    @DisplayName("특강 신청 가능 목록 api e2e")
    public void lectures() throws Exception {
        String url = "/api/lectures";

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
        MvcResult mvcResult = mockMvc.perform(get(url).queryParam("date", validDtf.format(today)))
            // then
            .andExpect(status().isOk())
            .andReturn();

        // json 역직렬화
        List<LectureResponse> lectureResponses1 = objectMapper.readValue(new String(mvcResult.getResponse().getContentAsByteArray(), "UTF-8"), new TypeReference<List<LectureResponse>>() {
        });

        // then
        assertEquals(todayLectureCount, lectureResponses1.size());
        assertDoesNotThrow(() -> {
            lectureResponses1.stream().forEach(lecture -> {

                // 조회 시 파라미터 날짜와 같지않으면 throw
                if(!lecture.date().isEqual(today)){
                    throw new IllegalArgumentException();
                }

            });
        });

        // when
        MvcResult mvcResult2 = mockMvc.perform(get(url).queryParam("date", validDtf.format(tomorrow)))
            // then
            .andExpect(status().isOk())
            .andReturn();

        // json 역직렬화
        List<LectureResponse> lectureResponses2 = objectMapper.readValue(new String(mvcResult2.getResponse().getContentAsByteArray(), "UTF-8"), new TypeReference<List<LectureResponse>>() {
        });

        // then
        assertEquals(tomorrowLectureCount, lectureResponses2.size());
        assertDoesNotThrow(() -> {
            lectureResponses2.stream().forEach(lecture -> {

                // 조회 시 파라미터 날짜와 같지않으면 throw
                if(!lecture.date().isEqual(tomorrow)){
                    throw new IllegalArgumentException();
                }

            });
        });
    }



    /**
     * feature3
     * 특강 신청 완료 목록 조회 api e2e 테스트
     *
     */
    @Test
    @DisplayName("특강 신청 완료 목록 조회 api e2e")
    public void bookedLectures() throws Exception {
        String url = "/api/lectures/booked";

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
            lectureBookingRepository.save(
                new LectureBooking(lecture.getId(), userId)
            );
        }

        // when
        MvcResult mvcResult = mockMvc.perform(get(url).queryParam("userId", String.valueOf(userId)))
            // then
            .andExpect(status().isOk())
            .andReturn();

        // json 역직렬화
        List<LectureResponse> lectureResponses = objectMapper.readValue(new String(mvcResult.getResponse().getContentAsByteArray(), "UTF-8"), new TypeReference<List<LectureResponse>>() {
        });

        // then
        assertEquals(todayLectureCount, lectureResponses.size());


    }


}
