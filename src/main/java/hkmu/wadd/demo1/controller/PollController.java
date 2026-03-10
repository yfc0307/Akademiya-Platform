package hkmu.wadd.demo1.controller;

import hkmu.wadd.demo1.model.AppUser;
import hkmu.wadd.demo1.model.Poll;
import hkmu.wadd.demo1.service.PollService;
import hkmu.wadd.demo1.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/poll")
public class PollController {

    private final PollService pollService;
    private final UserService userService;

    public PollController(PollService pollService, UserService userService) {
        this.pollService = pollService;
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public String viewPoll(@PathVariable Long id, Model model, Principal principal) {
        Poll poll = pollService.findById(id);
        if (poll == null) {
            return "redirect:/";
        }
        model.addAttribute("poll", poll);
        if (principal != null) {
            AppUser user = userService.findByUsername(principal.getName());
            model.addAttribute("currentUser", user);
            model.addAttribute("hasVoted", pollService.hasUserVoted(id, user));
        }
        return "poll/view";
    }

    @PostMapping("/{id}/vote")
    public String vote(@PathVariable Long id, @RequestParam int selectedOption,
                       Principal principal, RedirectAttributes redirectAttributes) {
        AppUser user = userService.findByUsername(principal.getName());
        boolean success = pollService.vote(id, user, selectedOption);
        if (!success) {
            redirectAttributes.addFlashAttribute("voteError", "You have already voted on this poll.");
        }
        return "redirect:/poll/" + id;
    }

    @PostMapping("/{id}/comment")
    public String addComment(@PathVariable Long id, @RequestParam String content, Principal principal) {
        AppUser user = userService.findByUsername(principal.getName());
        pollService.addComment(id, user, content);
        return "redirect:/poll/" + id;
    }
}

