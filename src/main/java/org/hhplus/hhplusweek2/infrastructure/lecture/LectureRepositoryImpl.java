package org.hhplus.hhplusweek2.infrastructure.lecture;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.hhplus.hhplusweek2.domain.lecture.Lecture;
import org.hhplus.hhplusweek2.domain.lecture.LectureRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LectureRepositoryImpl implements LectureRepository {

    private final LectureJpaRepository lectureJpaRepository;

    @Override
    public Lecture save(Lecture lecture) {
        return lectureJpaRepository.save(lecture);
    }

    @Override
    public Optional<Lecture> findById(Long id) {
        return lectureJpaRepository.findById(id);
    }

    @Override
    public List<Lecture> findAvailableLecturesByDate(LocalDate date) {
        return lectureJpaRepository.findAvailableLecturesByDate(date);
    }

    @Override
    public List<Lecture> saveAll(List<Lecture> lectures) {
        return lectureJpaRepository.saveAll(lectures);
    }

    @Override
    public List<Lecture> findBookedLecturesByUserId(Long userId) {
        return lectureJpaRepository.findBookedLecturesByUserId(userId);
    }


}
