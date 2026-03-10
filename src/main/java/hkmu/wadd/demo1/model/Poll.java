package hkmu.wadd.demo1.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Poll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String question;

    @Column(nullable = false)
    private String option1;

    @Column(nullable = false)
    private String option2;

    @Column(nullable = false)
    private String option3;

    @Column(nullable = false)
    private String option4;

    @Column(nullable = false)
    private String option5;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @OneToMany(mappedBy = "poll", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PollVote> votes = new ArrayList<>();

    @OneToMany(mappedBy = "poll", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("timestamp DESC")
    private List<Comment> comments = new ArrayList<>();

    public Poll() {}

    public Poll(String question, String option1, String option2, String option3, String option4, String option5, Course course) {
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.option5 = option5;
        this.course = course;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }

    public String getOption1() { return option1; }
    public void setOption1(String option1) { this.option1 = option1; }

    public String getOption2() { return option2; }
    public void setOption2(String option2) { this.option2 = option2; }

    public String getOption3() { return option3; }
    public void setOption3(String option3) { this.option3 = option3; }

    public String getOption4() { return option4; }
    public void setOption4(String option4) { this.option4 = option4; }

    public String getOption5() { return option5; }
    public void setOption5(String option5) { this.option5 = option5; }

    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }

    public List<PollVote> getVotes() { return votes; }
    public void setVotes(List<PollVote> votes) { this.votes = votes; }

    public List<Comment> getComments() { return comments; }
    public void setComments(List<Comment> comments) { this.comments = comments; }

    public String getOptionText(int optionNumber) {
        return switch (optionNumber) {
            case 1 -> option1;
            case 2 -> option2;
            case 3 -> option3;
            case 4 -> option4;
            case 5 -> option5;
            default -> "";
        };
    }

    public long getVoteCount(int optionNumber) {
        return votes.stream().filter(v -> v.getSelectedOption() == optionNumber).count();
    }
}

