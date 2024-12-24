package org.hhplus.hhplusweek2.domain.lecture;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.hhplus.hhplusweek2.domain.common.Base;
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

}
