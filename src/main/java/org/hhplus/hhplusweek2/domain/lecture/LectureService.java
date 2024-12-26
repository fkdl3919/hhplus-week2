package org.hhplus.hhplusweek2.domain.lecture;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.hhplus.hhplusweek2.application.lecture.dto.LectureDto;
import org.springframework.stereotype.Service;

/**
 * 도메인의 비즈니스 핵심 로직
 */
@Service
@RequiredArgsConstructor
public class LectureService {

    private final LectureRepository lectureRepository;

    @Transactional
    public Lecture save(Lecture lecture) {
        return lectureRepository.save(lecture);
    }

    public Lecture findById(Long id) {
        Lecture lecture = lectureRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("특강이 존재하지 않습니다."));
        return lecture;
    }

    public List<Lecture> findAvailableLecturesByDate(LocalDate date) {
        return lectureRepository.findAvailableLecturesByDate(date);
    }

    public List<Lecture> findBookedLecturesByUserId(Long userId) {
        return lectureRepository.findBookedLecturesByUserId(userId);
    }
}
