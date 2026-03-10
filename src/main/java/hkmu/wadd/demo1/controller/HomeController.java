package hkmu.wadd.demo1.controller;

import hkmu.wadd.demo1.model.Course;
import hkmu.wadd.demo1.service.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final CourseService courseService;

    public HomeController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<Course> courses = courseService.findAll();
        model.addAttribute("courses", courses);
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}

