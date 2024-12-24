package org.hhplus.hhplusweek2.infrastructure.lecture;

import org.hhplus.hhplusweek2.domain.lecture.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureJpaRepository extends JpaRepository<Lecture, Long> {

}
