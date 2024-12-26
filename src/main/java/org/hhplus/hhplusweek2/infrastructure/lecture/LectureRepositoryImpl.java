package org.hhplus.hhplusweek2.infrastructure.lecture;

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
}
