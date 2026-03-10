package hkmu.wadd.demo1.service;

import hkmu.wadd.demo1.model.Course;
import hkmu.wadd.demo1.repository.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepo;

    public CourseService(CourseRepository courseRepo) {
        this.courseRepo = courseRepo;
    }

    public List<Course> findAll() {
        return courseRepo.findAll();
    }

    public Course findById(Long id) {
        return courseRepo.findById(id).orElse(null);
    }

    @Transactional
    public Course createCourse(String name, String shortDescription) {
        return courseRepo.save(new Course(name, shortDescription));
    }

    @Transactional
    public Course updateCourse(Long id, String name, String shortDescription) {
        Course course = courseRepo.findById(id).orElseThrow(() -> new RuntimeException("Course not found"));
        course.setName(name);
        course.setShortDescription(shortDescription);
        return courseRepo.save(course);
    }

    @Transactional
    public void deleteCourse(Long id) {
        courseRepo.deleteById(id);
    }
}

