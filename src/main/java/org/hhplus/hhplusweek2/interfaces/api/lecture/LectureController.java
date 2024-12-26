package org.hhplus.hhplusweek2.interfaces.api.lecture;

import lombok.RequiredArgsConstructor;
import org.hhplus.hhplusweek2.application.lecture.LectureFacade;
import org.hhplus.hhplusweek2.interfaces.api.common.ApiRes;
import org.hhplus.hhplusweek2.interfaces.api.lecture.request.LectureRequest;
import org.hhplus.hhplusweek2.interfaces.api.lecture.response.LectureResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/lectures")
@RequiredArgsConstructor
public class LectureController {

    private final LectureFacade lectureFacade;

    @PostMapping("/")
    public ApiRes<LectureResponse> lecture(@RequestBody LectureRequest lectureRequest) {
        lectureFacade.bookLecture(LectureRequest.toCommand(lectureRequest));
        return ApiRes.success(null, null);
    }

}
