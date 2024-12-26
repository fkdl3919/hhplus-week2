package org.hhplus.hhplusweek2.application.lecture.dto;

import org.hhplus.hhplusweek2.domain.lecture.Lecture;

public record LectureDto(
    Long lectureId,
    Long userId
) {

/*
    */
/**
     * domain -> application 영역
     * @param lecture
     * @return
     *//*

    public static LectureDto of(Lecture lecture){
        return new LectureDto(lecture.getId(),);
    }
*/

    /**
     * domain -> infrastructure 영역
     * @return
     */
    public Lecture toEntity(){
        return new Lecture();
    }


}
