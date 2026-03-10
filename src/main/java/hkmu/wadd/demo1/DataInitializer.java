package hkmu.wadd.demo1;

import hkmu.wadd.demo1.model.*;
import hkmu.wadd.demo1.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final AppUserRepository userRepo;
    private final CourseRepository courseRepo;
    private final LectureRepository lectureRepo;
    private final PollRepository pollRepo;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(AppUserRepository userRepo, CourseRepository courseRepo,
                           LectureRepository lectureRepo, PollRepository pollRepo,
                           PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.courseRepo = courseRepo;
        this.lectureRepo = lectureRepo;
        this.pollRepo = pollRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        // Create default teacher
        if (!userRepo.existsByUsername("teacher")) {
            userRepo.save(new AppUser("nahida", passwordEncoder.encode("sumerulove"),
                    "Buer", "nahida@akasha.su", "6633", AppUser.Role.TEACHER));
        }

        // Create default student
        if (!userRepo.existsByUsername("student")) {
            userRepo.save(new AppUser("WantToSleep", passwordEncoder.encode("star1234"),
                    "Layla", "lyl@rwh.ake.edu.su", "9002", AppUser.Role.STUDENT));
        }

        // Create sample course
        if (courseRepo.count() == 0) {
            Course course = new Course("TECH3091 Advanced Deshret Technology",
                    "This course covers ancient Deshret Technology with case studies on ASIMON (Algorithm of Semi-Intransient Matrix of Overseer Network) and related technologies. Students will learn to build polyhedron prisms with basic reshape and lazer functions.");
            course = courseRepo.save(course);

            // Create sample lectures
            Lecture lecture1 = new Lecture("Introduction to Overseer Network",
                    "This lecture covers the basics of ancient Deshret algorithms and matrix theories.",
                    course);
            lectureRepo.save(lecture1);

            Lecture lecture2 = new Lecture("Matrix and Polyhedron",
                    "Learn about the design pattern in Polyhedron standard construct, prism mechanism, and reflection network.",
                    course);
            lectureRepo.save(lecture2);

            Lecture lecture3 = new Lecture("Advanced Akasha Querying",
                    "This lecture introduces advanced Akasha querying techniques, memory storage security, privacy protection, and index-based batch serialization.",
                    course);
            lectureRepo.save(lecture3);

            // Create sample polls
            Poll poll1 = new Poll("New name for Hat Guy?",
                    "Hat Boy", "Kushmanda", "Arjun", "Umbrella", "Spring",
                    course);
            pollRepo.save(poll1);

            Poll poll2 = new Poll("Which nation do you want to travel outside Sumeru?",
                    "Fontaine", "Liyue", "Mondstadt", "Natlan", "Snezhnaya",
                    course);
            pollRepo.save(poll2);
        }
    }
}

