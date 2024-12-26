package org.hhplus.hhplusweek2.domain.lecture;

import jakarta.persistence.EntityNotFoundException;
import java.text.MessageFormat;
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

    public Lecture save(Lecture lecture) {
        return lectureRepository.save(lecture);
    }

    public Lecture findById(Long id) {
        Lecture lecture = lectureRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("특강이 존재하지 않습니다."));
        return lecture;
    }

}
