package hkmu.wadd.demo1.model;

import jakarta.persistence.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"poll_id", "user_id"}))
public class PollVote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "poll_id", nullable = false)
    private Poll poll;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    @Column(nullable = false)
    private int selectedOption; // 1-5

    public PollVote() {}

    public PollVote(Poll poll, AppUser user, int selectedOption) {
        this.poll = poll;
        this.user = user;
        this.selectedOption = selectedOption;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Poll getPoll() { return poll; }
    public void setPoll(Poll poll) { this.poll = poll; }

    public AppUser getUser() { return user; }
    public void setUser(AppUser user) { this.user = user; }

    public int getSelectedOption() { return selectedOption; }
    public void setSelectedOption(int selectedOption) { this.selectedOption = selectedOption; }
}

