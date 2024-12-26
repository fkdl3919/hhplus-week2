package org.hhplus.hhplusweek2.application.lecture.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.hhplus.hhplusweek2.domain.lecture.Lecture;
import org.hhplus.hhplusweek2.interfaces.api.lecture.response.LectureResponse;

public record LectureDto(
    Long id,
    String title,
    String instructor,
    LocalDate date,
    Integer capacity
) {

    /**
     * application -> interfaces 영역
     * @param lecture
     * @return
     */

    public static LectureDto of(Lecture lecture){
        return new LectureDto(lecture.getId(), lecture.getTitle(), lecture.getInstructor(), lecture.getDate(), lecture.getCapacity());
    }

    /**
     * application -> interfaces 영역
     * @param lectures
     * @return
     */

    public static List<LectureDto> toDtos(List<Lecture> lectures){
        return lectures.stream().map((lecture) -> LectureDto.of(lecture)).collect(Collectors.toList());
    }

}
