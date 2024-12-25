package org.hhplus.hhplusweek2.interfaces.api.lecture.dto;

import org.hhplus.hhplusweek2.application.lecture.dto.LectureDto;

public record LectureRequest(
    Integer userId,
    Integer lectureId
) {

    public static LectureDto toDto (LectureRequest lectureRequest){
        return new LectureDto();
    }

}
