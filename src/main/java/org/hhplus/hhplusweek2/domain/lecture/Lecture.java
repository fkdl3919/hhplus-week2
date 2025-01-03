package org.hhplus.hhplusweek2.domain.lecture;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hhplus.hhplusweek2.domain.common.Base;
import org.hhplus.hhplusweek2.application.lecture.dto.LectureDto;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Lecture extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String instructor;

    private LocalDate date;

    @ColumnDefault("30")
    private Integer capacity = 30;

    /**
     * 수강 신청 시 잔여 정원 감소
     */
    public void decreaseCapacity() {
        this.capacity--;
    }

    public void validAvailable() {
        if(this.getCapacity() == 0 ){
            throw new IllegalArgumentException("정원이 초과된 특강입니다.");
        }
    }
}
