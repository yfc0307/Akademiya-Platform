package hkmu.wadd.demo1.service;

import hkmu.wadd.demo1.model.*;
import hkmu.wadd.demo1.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class LectureService {

    private final LectureRepository lectureRepo;
    private final LectureAttachmentRepository attachmentRepo;
    private final CommentRepository commentRepo;
    private final CourseRepository courseRepo;

    public LectureService(LectureRepository lectureRepo, LectureAttachmentRepository attachmentRepo,
                          CommentRepository commentRepo, CourseRepository courseRepo) {
        this.lectureRepo = lectureRepo;
        this.attachmentRepo = attachmentRepo;
        this.commentRepo = commentRepo;
        this.courseRepo = courseRepo;
    }

    public Lecture findById(Long id) {
        return lectureRepo.findById(id).orElse(null);
    }

    public List<Lecture> findByCourseId(Long courseId) {
        return lectureRepo.findByCourseId(courseId);
    }

    @Transactional
    public Lecture createLecture(Long courseId, String title, String summary, List<MultipartFile> files) throws IOException {
        Course course = courseRepo.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));
        Lecture lecture = new Lecture(title, summary, course);
        lecture = lectureRepo.save(lecture);
        if (files != null) {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    LectureAttachment att = new LectureAttachment(
                            file.getOriginalFilename(), file.getContentType(), file.getBytes(), lecture);
                    attachmentRepo.save(att);
                }
            }
        }
        return lecture;
    }

    @Transactional
    public Lecture updateLecture(Long id, String title, String summary, List<MultipartFile> files) throws IOException {
        Lecture lecture = lectureRepo.findById(id).orElseThrow(() -> new RuntimeException("Lecture not found"));
        lecture.setTitle(title);
        lecture.setSummary(summary);
        if (files != null) {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    LectureAttachment att = new LectureAttachment(
                            file.getOriginalFilename(), file.getContentType(), file.getBytes(), lecture);
                    attachmentRepo.save(att);
                }
            }
        }
        return lectureRepo.save(lecture);
    }

    @Transactional
    public void deleteLecture(Long id) {
        lectureRepo.deleteById(id);
    }

    public LectureAttachment findAttachmentById(Long id) {
        return attachmentRepo.findById(id).orElse(null);
    }

    @Transactional
    public void deleteAttachment(Long id) {
        attachmentRepo.deleteById(id);
    }

    @Transactional
    public Comment addComment(Long lectureId, AppUser user, String content) {
        Lecture lecture = lectureRepo.findById(lectureId).orElseThrow(() -> new RuntimeException("Lecture not found"));
        Comment comment = new Comment(content, user, lecture);
        return commentRepo.save(comment);
    }

    @Transactional
    public void deleteComment(Long commentId) {
        commentRepo.deleteById(commentId);
    }
}

