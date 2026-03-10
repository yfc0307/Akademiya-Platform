package hkmu.wadd.demo1.repository;

import hkmu.wadd.demo1.model.AppUser;
import hkmu.wadd.demo1.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByUserOrderByTimestampDesc(AppUser user);
    List<Comment> findByLectureIdOrderByTimestampDesc(Long lectureId);
    List<Comment> findByPollIdOrderByTimestampDesc(Long pollId);
}

