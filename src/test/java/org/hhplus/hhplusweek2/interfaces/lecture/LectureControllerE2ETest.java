package org.hhplus.hhplusweek2.interfaces.lecture;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class LectureControllerE2ETest {

    @Autowired
    private MockMvc mockMvc;

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

}
