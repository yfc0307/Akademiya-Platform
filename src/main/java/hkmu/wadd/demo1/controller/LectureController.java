package hkmu.wadd.demo1.controller;

import hkmu.wadd.demo1.model.AppUser;
import hkmu.wadd.demo1.model.Lecture;
import hkmu.wadd.demo1.model.LectureAttachment;
import hkmu.wadd.demo1.service.LectureService;
import hkmu.wadd.demo1.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/lecture")
public class LectureController {

    private final LectureService lectureService;
    private final UserService userService;

    public LectureController(LectureService lectureService, UserService userService) {
        this.lectureService = lectureService;
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public String viewLecture(@PathVariable Long id, Model model, Principal principal) {
        Lecture lecture = lectureService.findById(id);
        if (lecture == null) {
            return "redirect:/";
        }
        model.addAttribute("lecture", lecture);
        if (principal != null) {
            AppUser user = userService.findByUsername(principal.getName());
            model.addAttribute("currentUser", user);
        }
        return "lecture/view";
    }

    @PostMapping("/{id}/comment")
    public String addComment(@PathVariable Long id, @RequestParam String content, Principal principal) {
        AppUser user = userService.findByUsername(principal.getName());
        lectureService.addComment(id, user, content);
        return "redirect:/lecture/" + id;
    }

    @GetMapping("/attachment/{id}")
    public ResponseEntity<byte[]> downloadAttachment(@PathVariable Long id) {
        LectureAttachment attachment = lectureService.findAttachmentById(id);
        if (attachment == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + attachment.getFileName() + "\"")
                .contentType(MediaType.parseMediaType(attachment.getContentType()))
                .body(attachment.getData());
    }
}

