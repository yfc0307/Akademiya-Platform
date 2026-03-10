package hkmu.wadd.demo1.repository;

import hkmu.wadd.demo1.model.Poll;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PollRepository extends JpaRepository<Poll, Long> {
    List<Poll> findByCourseId(Long courseId);
}

