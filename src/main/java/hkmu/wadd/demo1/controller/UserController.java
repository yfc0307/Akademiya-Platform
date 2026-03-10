package hkmu.wadd.demo1.controller;

import hkmu.wadd.demo1.model.AppUser;
import hkmu.wadd.demo1.model.Comment;
import hkmu.wadd.demo1.model.PollVote;
import hkmu.wadd.demo1.repository.CommentRepository;
import hkmu.wadd.demo1.service.PollService;
import hkmu.wadd.demo1.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class UserController {

    private final UserService userService;
    private final CommentRepository commentRepository;
    private final PollService pollService;

    public UserController(UserService userService, CommentRepository commentRepository, PollService pollService) {
        this.userService = userService;
        this.commentRepository = commentRepository;
        this.pollService = pollService;
    }

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String fullName,
                           @RequestParam String email,
                           @RequestParam(required = false) String phone,
                           @RequestParam String role,
                           RedirectAttributes redirectAttributes) {
        try {
            AppUser.Role userRole = AppUser.Role.valueOf(role);
            userService.register(username, password, fullName, email, phone, userRole);
            redirectAttributes.addFlashAttribute("success", "Registration successful! Please login.");
            return "redirect:/login";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/register";
        }
    }

    @GetMapping("/profile/history")
    public String viewHistory(Model model, Principal principal) {
        AppUser user = userService.findByUsername(principal.getName());
        List<Comment> comments = commentRepository.findByUserOrderByTimestampDesc(user);
        List<PollVote> votes = pollService.getUserVotes(user);
        model.addAttribute("user", user);
        model.addAttribute("comments", comments);
        model.addAttribute("votes", votes);
        return "profile/history";
    }

    @GetMapping("/profile/edit")
    public String showProfileEditForm(Model model, Principal principal) {
        AppUser user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "profile/edit";
    }

    @PostMapping("/profile/edit")
    public String updateProfile(@RequestParam String fullName,
                                @RequestParam String email,
                                @RequestParam(required = false) String phone,
                                @RequestParam(required = false) String password,
                                Principal principal,
                                RedirectAttributes redirectAttributes) {
        try {
            userService.updateProfile(principal.getName(), fullName, email, phone, password);
            redirectAttributes.addFlashAttribute("success", "Profile updated successfully.");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/profile/edit";
    }
}

