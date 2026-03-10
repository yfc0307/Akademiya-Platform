package hkmu.wadd.demo1.controller;

import hkmu.wadd.demo1.model.*;
import hkmu.wadd.demo1.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final CourseService courseService;
    private final LectureService lectureService;
    private final PollService pollService;

    public AdminController(UserService userService, CourseService courseService,
                           LectureService lectureService, PollService pollService) {
        this.userService = userService;
        this.courseService = courseService;
        this.lectureService = lectureService;
        this.pollService = pollService;
    }

    // ===== USER MANAGEMENT =====

    @GetMapping("/users")
    public String listUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "admin/user-list";
    }

    @GetMapping("/users/edit/{id}")
    public String editUserForm(@PathVariable Long id, Model model) {
        AppUser user = userService.findById(id);
        if (user == null) return "redirect:/admin/users";
        model.addAttribute("user", user);
        return "admin/user-form";
    }

    @PostMapping("/users/edit/{id}")
    public String updateUser(@PathVariable Long id,
                             @RequestParam String fullName,
                             @RequestParam String email,
                             @RequestParam(required = false) String phone,
                             @RequestParam String role,
                             @RequestParam(required = false) String password,
                             RedirectAttributes redirectAttributes) {
        try {
            userService.updateUser(id, fullName, email, phone, AppUser.Role.valueOf(role), password);
            redirectAttributes.addFlashAttribute("success", "User updated successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/users";
    }

    @PostMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        userService.deleteUser(id);
        redirectAttributes.addFlashAttribute("success", "User deleted successfully.");
        return "redirect:/admin/users";
    }

    // ===== COURSE MANAGEMENT =====

    @GetMapping("/course/add")
    public String addCourseForm() {
        return "admin/course-form";
    }

    @PostMapping("/course/add")
    public String addCourse(@RequestParam String name, @RequestParam String shortDescription,
                            RedirectAttributes redirectAttributes) {
        courseService.createCourse(name, shortDescription);
        redirectAttributes.addFlashAttribute("success", "Course created successfully.");
        return "redirect:/";
    }

    @GetMapping("/course/edit/{id}")
    public String editCourseForm(@PathVariable Long id, Model model) {
        Course course = courseService.findById(id);
        if (course == null) return "redirect:/";
        model.addAttribute("course", course);
        return "admin/course-form";
    }

    @PostMapping("/course/edit/{id}")
    public String updateCourse(@PathVariable Long id, @RequestParam String name,
                               @RequestParam String shortDescription, RedirectAttributes redirectAttributes) {
        courseService.updateCourse(id, name, shortDescription);
        redirectAttributes.addFlashAttribute("success", "Course updated successfully.");
        return "redirect:/";
    }

    @PostMapping("/course/delete/{id}")
    public String deleteCourse(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        courseService.deleteCourse(id);
        redirectAttributes.addFlashAttribute("success", "Course deleted successfully.");
        return "redirect:/";
    }

    // ===== LECTURE MANAGEMENT =====

    @GetMapping("/lecture/add")
    public String addLectureForm(@RequestParam Long courseId, Model model) {
        model.addAttribute("courseId", courseId);
        return "admin/lecture-form";
    }

    @PostMapping("/lecture/add")
    public String addLecture(@RequestParam Long courseId,
                             @RequestParam String title,
                             @RequestParam String summary,
                             @RequestParam(required = false) List<MultipartFile> files,
                             RedirectAttributes redirectAttributes) throws IOException {
        Lecture lecture = lectureService.createLecture(courseId, title, summary, files);
        redirectAttributes.addFlashAttribute("success", "Lecture created successfully.");
        return "redirect:/lecture/" + lecture.getId();
    }

    @GetMapping("/lecture/edit/{id}")
    public String editLectureForm(@PathVariable Long id, Model model) {
        Lecture lecture = lectureService.findById(id);
        if (lecture == null) return "redirect:/";
        model.addAttribute("lecture", lecture);
        return "admin/lecture-form";
    }

    @PostMapping("/lecture/edit/{id}")
    public String updateLecture(@PathVariable Long id,
                                @RequestParam String title,
                                @RequestParam String summary,
                                @RequestParam(required = false) List<MultipartFile> files,
                                RedirectAttributes redirectAttributes) throws IOException {
        lectureService.updateLecture(id, title, summary, files);
        redirectAttributes.addFlashAttribute("success", "Lecture updated successfully.");
        return "redirect:/lecture/" + id;
    }

    @PostMapping("/lecture/delete/{id}")
    public String deleteLecture(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Lecture lecture = lectureService.findById(id);
        Long courseId = lecture != null ? lecture.getCourse().getId() : null;
        lectureService.deleteLecture(id);
        redirectAttributes.addFlashAttribute("success", "Lecture deleted successfully.");
        return "redirect:/";
    }

    @PostMapping("/lecture/attachment/delete/{id}")
    public String deleteAttachment(@PathVariable Long id, @RequestParam Long lectureId,
                                   RedirectAttributes redirectAttributes) {
        lectureService.deleteAttachment(id);
        redirectAttributes.addFlashAttribute("success", "Attachment deleted.");
        return "redirect:/admin/lecture/edit/" + lectureId;
    }

    @PostMapping("/comment/delete/{id}")
    public String deleteComment(@PathVariable Long id, @RequestParam String redirectUrl,
                                RedirectAttributes redirectAttributes) {
        lectureService.deleteComment(id);
        redirectAttributes.addFlashAttribute("success", "Comment deleted.");
        return "redirect:" + redirectUrl;
    }

    // ===== POLL MANAGEMENT =====

    @GetMapping("/poll/add")
    public String addPollForm(@RequestParam Long courseId, Model model) {
        model.addAttribute("courseId", courseId);
        return "admin/poll-form";
    }

    @PostMapping("/poll/add")
    public String addPoll(@RequestParam Long courseId,
                          @RequestParam String question,
                          @RequestParam String option1,
                          @RequestParam String option2,
                          @RequestParam String option3,
                          @RequestParam String option4,
                          @RequestParam String option5,
                          RedirectAttributes redirectAttributes) {
        Poll poll = pollService.createPoll(courseId, question, option1, option2, option3, option4, option5);
        redirectAttributes.addFlashAttribute("success", "Poll created successfully.");
        return "redirect:/poll/" + poll.getId();
    }

    @GetMapping("/poll/edit/{id}")
    public String editPollForm(@PathVariable Long id, Model model) {
        Poll poll = pollService.findById(id);
        if (poll == null) return "redirect:/";
        model.addAttribute("poll", poll);
        return "admin/poll-form";
    }

    @PostMapping("/poll/edit/{id}")
    public String updatePoll(@PathVariable Long id,
                             @RequestParam String question,
                             @RequestParam String option1,
                             @RequestParam String option2,
                             @RequestParam String option3,
                             @RequestParam String option4,
                             @RequestParam String option5,
                             RedirectAttributes redirectAttributes) {
        pollService.updatePoll(id, question, option1, option2, option3, option4, option5);
        redirectAttributes.addFlashAttribute("success", "Poll updated successfully.");
        return "redirect:/poll/" + id;
    }

    @PostMapping("/poll/delete/{id}")
    public String deletePoll(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        pollService.deletePoll(id);
        redirectAttributes.addFlashAttribute("success", "Poll deleted successfully.");
        return "redirect:/";
    }

    @PostMapping("/poll/comment/delete/{id}")
    public String deletePollComment(@PathVariable Long id, @RequestParam String redirectUrl,
                                    RedirectAttributes redirectAttributes) {
        pollService.deleteComment(id);
        redirectAttributes.addFlashAttribute("success", "Comment deleted.");
        return "redirect:" + redirectUrl;
    }
}

