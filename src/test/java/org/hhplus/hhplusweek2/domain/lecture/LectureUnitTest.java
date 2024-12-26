package org.hhplus.hhplusweek2.domain.lecture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LectureUnitTest {

    @InjectMocks
    private LectureService lectureService;

    @Mock
    private LectureRepository lectureRepository;

    @Test
    @DisplayName("특강이 존재하지 않을 경우 EntityNotFoundException 발생")
    public void lectureTest1(){
        // given
        long lectureId = 1L;

        // stub
        when(lectureRepository.findByIdWithLock(lectureId)).thenReturn(Optional.empty());

        // when
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> lectureService.findByIdWithLock(lectureId));

        // then
        assertEquals("특강이 존재하지 않습니다.", ex.getMessage());
    }


}
