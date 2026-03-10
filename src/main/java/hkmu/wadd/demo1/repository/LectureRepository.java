package hkmu.wadd.demo1.repository;

import hkmu.wadd.demo1.model.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
    List<Lecture> findByCourseId(Long courseId);
}

