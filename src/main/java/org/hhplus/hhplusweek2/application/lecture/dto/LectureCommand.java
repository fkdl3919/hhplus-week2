package org.hhplus.hhplusweek2.application.lecture.dto;


import org.hhplus.hhplusweek2.domain.lecturebooking.LectureBooking;

public record LectureCommand(
    Long lectureId,
    Long userId
) {

    public LectureBooking toLectureBooking() {
        return new LectureBooking(this.lectureId, this.userId);
    }

}
