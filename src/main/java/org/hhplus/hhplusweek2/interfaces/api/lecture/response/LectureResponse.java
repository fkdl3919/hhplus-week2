package org.hhplus.hhplusweek2.interfaces.api.lecture.response;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.hhplus.hhplusweek2.application.lecture.dto.LectureDto;

public record LectureResponse(
    Long id,
    String title,
    String instructor,
    LocalDate date,
    Integer capacity
) {

    public static LectureResponse fromDto(LectureDto lectureDto) {
        return new LectureResponse(lectureDto.id(),
            lectureDto.title(),
            lectureDto.instructor(),
            lectureDto.date(),
            lectureDto.capacity());
    }

    public static List<LectureResponse> fromDtos(List<LectureDto> lectureDto) {
        return lectureDto.stream().map((dto) -> LectureResponse.fromDto(dto)).collect(Collectors.toList());
    }


}
