package org.hhplus.hhplusweek2.interfaces.api.lecture;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hhplus.hhplusweek2.application.lecture.LectureFacade;
import org.hhplus.hhplusweek2.interfaces.api.lecture.request.LectureRequest;
import org.hhplus.hhplusweek2.interfaces.api.lecture.response.LectureResponse;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/lectures")
@RequiredArgsConstructor
public class LectureController {

    private final LectureFacade lectureFacade;

    /**
     * 특강 신청 api
     * @param lectureRequest
     * @return
     */
    @PostMapping
    public ResponseEntity bookLecture(@RequestBody LectureRequest lectureRequest) {
        lectureFacade.bookLecture(LectureRequest.toCommand(lectureRequest));
        return ResponseEntity.ok().build();
    }

    /**
     * 특강 신청 가능 목록 api
     * @param date
     * @return
     */
    @GetMapping
    public ResponseEntity<List<LectureResponse>> lectures(
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        @RequestParam LocalDate date
    ) {
        return ResponseEntity.ok(LectureResponse.fromDtos(lectureFacade.getAvailableLectures(date)));
    }

    /**
     * 특강 신청 완료 목록 조회 api
     * @param userId
     * @return
     */
    @GetMapping
    public ResponseEntity<List<LectureResponse>> bookedLectures(
        @RequestParam Long userId
    ) {
        return ResponseEntity.ok(LectureResponse.fromDtos(lectureFacade.getBookedLectures(userId)));
    }

}
