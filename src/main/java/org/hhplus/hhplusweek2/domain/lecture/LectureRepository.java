package org.hhplus.hhplusweek2.domain.lecture;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LectureRepository {

    Lecture save(Lecture lecture);

    Optional<Lecture> findByIdWithLock(Long id);

    List<Lecture> findAvailableLecturesByDate(LocalDate date);

    List<Lecture> saveAll(List<Lecture> lectures);

    List<Lecture> findBookedLecturesByUserId(Long userId);
}
