package org.hhplus.hhplusweek2.domain.lecture;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import org.hhplus.hhplusweek2.domain.common.Base;
import org.hhplus.hhplusweek2.application.lecture.dto.LectureDto;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
public class Lecture extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String instructor;

    private LocalDate date;

    @ColumnDefault("30")
    private int capacity;

    /**
     * domain -> infrastructure 영역
     * @param dto
     * @return
     */
    public static Lecture toEntity(LectureDto dto){
        return new Lecture();
    }

}
