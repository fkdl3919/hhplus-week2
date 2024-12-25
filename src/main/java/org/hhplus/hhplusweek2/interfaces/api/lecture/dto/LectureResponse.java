package org.hhplus.hhplusweek2.interfaces.api.lecture.dto;

public record LectureResponse(

) {

    public static LectureResponse from(LectureCommand lectureCommand) {
        return new LectureResponse();
    }

}
