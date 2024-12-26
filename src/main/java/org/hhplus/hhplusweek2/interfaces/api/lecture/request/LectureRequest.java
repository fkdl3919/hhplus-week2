package org.hhplus.hhplusweek2.interfaces.api.lecture.request;

import org.hhplus.hhplusweek2.application.lecture.dto.LectureCommand;
import org.hhplus.hhplusweek2.application.lecture.dto.LectureDto;

public record LectureRequest(
    Long lectureId,
    Long userId
) {

/*
    public static LectureDto toDto (LectureRequest lectureRequest){
        return new LectureDto(lectureRequest.userId, lectureRequest.lectureId);
    }
*/

    public static LectureCommand toCommand (LectureRequest lectureRequest){
        return new LectureCommand(lectureRequest.lectureId, lectureRequest.userId);
    }


}
