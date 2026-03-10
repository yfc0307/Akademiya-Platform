package hkmu.wadd.demo1.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 2000)
    private String content;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "poll_id")
    private Poll poll;

    public Comment() {}

    public Comment(String content, AppUser user, Lecture lecture) {
        this.content = content;
        this.user = user;
        this.lecture = lecture;
        this.timestamp = LocalDateTime.now();
    }

    public Comment(String content, AppUser user, Poll poll) {
        this.content = content;
        this.user = user;
        this.poll = poll;
        this.timestamp = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public Date getTimestampAsDate() {
        if (timestamp == null) return null;
        return Date.from(timestamp.atZone(ZoneId.systemDefault()).toInstant());
    }

    public AppUser getUser() { return user; }
    public void setUser(AppUser user) { this.user = user; }

    public Lecture getLecture() { return lecture; }
    public void setLecture(Lecture lecture) { this.lecture = lecture; }

    public Poll getPoll() { return poll; }
    public void setPoll(Poll poll) { this.poll = poll; }
}

