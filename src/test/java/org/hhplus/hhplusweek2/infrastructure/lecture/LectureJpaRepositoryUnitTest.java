package org.hhplus.hhplusweek2.infrastructure.lecture;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import org.hhplus.hhplusweek2.domain.lecture.Lecture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/**
 * query 검증 및 데이터 검증
 */
@DataJpaTest
public class LectureJpaRepositoryUnitTest {

    @Autowired
    private LectureJpaRepository lectureJpaRepository;

    /**
     * 특강 신청 가능 목록
     * - test case 1
     *  - 입력한 날짜의 특강이 조회되는지 검증
     */
    @Test
    @DisplayName("특강 신청 가능 목록 - 입력한 날짜의 특강이 조회되는지 검증")
    public void availableLecturesByDate1(){
        // given
        LocalDate now = LocalDate.now();

        List<Lecture> lectures = Arrays.asList(
            new Lecture(null, "특강1", "강연자1", now, 30),
            new Lecture(null, "특강2", "강연자2", now, 30),
            new Lecture(null, "특강3", "강연자3", now, 30),

            // 조회되면 안되는 특강
            new Lecture(null, "특강4", "강연자4", now.plus(1, ChronoUnit.DAYS), 30)
        );

        lectureJpaRepository.saveAll(lectures);

        // when
        List<Lecture> findLectures = lectureJpaRepository.findAvailableLecturesByDate(now);

        // then
        assertEquals(3, findLectures.size());
        assertDoesNotThrow(() -> {
            findLectures.stream().forEach(lecture -> {

                // 조회 시 파라미터 날짜와 같지않으면 throw
                if(!lecture.getDate().isEqual(now)){
                    throw new IllegalArgumentException();
                }

            });
        });
    }

    /**
     * 특강 신청 가능 목록
     * - test case 2
     *  - 입력한 날짜의 특강이 현재정원이 0이 아닌 특강을 조회하는지 검증
     */
    @Test
    @DisplayName("특강 신청 가능 목록 - 입력한 날짜의 특강이 현재정원이 0이 아닌 특강을 조회하는지 검증")
    public void availableLecturesByDate2(){
        // given
        LocalDate now = LocalDate.now();

        List<Lecture> lectures = Arrays.asList(
            new Lecture(null, "특강1", "강연자1", now, 0),
            new Lecture(null, "특강2", "강연자2", now, 0),
            new Lecture(null, "특강3", "강연자3", now, 0)
        );

        lectureJpaRepository.saveAll(lectures);

        // when
        List<Lecture> findLectures = lectureJpaRepository.findAvailableLecturesByDate(now);

        // then
        assertTrue(findLectures.isEmpty());

    }


}
