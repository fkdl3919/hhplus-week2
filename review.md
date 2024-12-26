1. service의 책임에 대해서
```java
    public Lecture findById(Long id) {
        Lecture lecture = lectureRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("특강이 존재하지 않습니다."));
        return lecture;
    }
```
이처럼 facade - service 로 계층이 존재한다면 valid 에 대한 책임을 service에서 처리할 지 facade에서 처리할지 고민
- 내가 한 방법
  - 논리적으로 “Lecture가 존재하지 않는 것은 비즈니스적으로도 오류 상황인가?” 를 생각해본다면 맞기때문.. 고민끝에 도메인에 대한 직접적인 책임은 도메인이 갖고있어야 된다 생각해서 service에서 valid처리를 하였다.

2. dto에 대해서


