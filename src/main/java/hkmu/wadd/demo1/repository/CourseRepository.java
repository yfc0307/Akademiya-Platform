package hkmu.wadd.demo1.repository;

import hkmu.wadd.demo1.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}

