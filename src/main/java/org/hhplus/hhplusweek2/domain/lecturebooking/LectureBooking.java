package org.hhplus.hhplusweek2.domain.lecturebooking;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hhplus.hhplusweek2.domain.common.Base;
import org.hhplus.hhplusweek2.domain.lecture.Lecture;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class LectureBooking extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 논리적 fk > Lecture 관계
    private Long lectureId;

    private Long userId;

    public LectureBooking(Long lectureId, Long userId) {
        this.lectureId = lectureId;
        this.userId = userId;
    }
}
