package hkmu.wadd.demo1.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String shortDescription;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Lecture> lectures = new ArrayList<>();

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Poll> polls = new ArrayList<>();

    public Course() {}

    public Course(String name, String shortDescription) {
        this.name = name;
        this.shortDescription = shortDescription;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getShortDescription() { return shortDescription; }
    public void setShortDescription(String shortDescription) { this.shortDescription = shortDescription; }

    public List<Lecture> getLectures() { return lectures; }
    public void setLectures(List<Lecture> lectures) { this.lectures = lectures; }

    public List<Poll> getPolls() { return polls; }
    public void setPolls(List<Poll> polls) { this.polls = polls; }
}

