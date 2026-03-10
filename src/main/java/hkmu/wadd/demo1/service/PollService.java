package hkmu.wadd.demo1.service;

import hkmu.wadd.demo1.model.*;
import hkmu.wadd.demo1.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PollService {

    private final PollRepository pollRepo;
    private final PollVoteRepository voteRepo;
    private final CommentRepository commentRepo;
    private final CourseRepository courseRepo;

    public PollService(PollRepository pollRepo, PollVoteRepository voteRepo,
                       CommentRepository commentRepo, CourseRepository courseRepo) {
        this.pollRepo = pollRepo;
        this.voteRepo = voteRepo;
        this.commentRepo = commentRepo;
        this.courseRepo = courseRepo;
    }

    public Poll findById(Long id) {
        return pollRepo.findById(id).orElse(null);
    }

    public List<Poll> findByCourseId(Long courseId) {
        return pollRepo.findByCourseId(courseId);
    }

    @Transactional
    public Poll createPoll(Long courseId, String question, String o1, String o2, String o3, String o4, String o5) {
        Course course = courseRepo.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));
        Poll poll = new Poll(question, o1, o2, o3, o4, o5, course);
        return pollRepo.save(poll);
    }

    @Transactional
    public Poll updatePoll(Long id, String question, String o1, String o2, String o3, String o4, String o5) {
        Poll poll = pollRepo.findById(id).orElseThrow(() -> new RuntimeException("Poll not found"));
        poll.setQuestion(question);
        poll.setOption1(o1);
        poll.setOption2(o2);
        poll.setOption3(o3);
        poll.setOption4(o4);
        poll.setOption5(o5);
        return pollRepo.save(poll);
    }

    @Transactional
    public void deletePoll(Long id) {
        pollRepo.deleteById(id);
    }

    @Transactional
    public boolean vote(Long pollId, AppUser user, int selectedOption) {
        Poll poll = pollRepo.findById(pollId).orElseThrow(() -> new RuntimeException("Poll not found"));
        if (voteRepo.existsByPollAndUser(poll, user)) {
            return false; // Already voted
        }
        PollVote vote = new PollVote(poll, user, selectedOption);
        voteRepo.save(vote);
        return true;
    }

    public boolean hasUserVoted(Long pollId, AppUser user) {
        Poll poll = pollRepo.findById(pollId).orElse(null);
        if (poll == null) return false;
        return voteRepo.existsByPollAndUser(poll, user);
    }

    public List<PollVote> getUserVotes(AppUser user) {
        return voteRepo.findByUser(user);
    }

    @Transactional
    public Comment addComment(Long pollId, AppUser user, String content) {
        Poll poll = pollRepo.findById(pollId).orElseThrow(() -> new RuntimeException("Poll not found"));
        Comment comment = new Comment(content, user, poll);
        return commentRepo.save(comment);
    }

    @Transactional
    public void deleteComment(Long commentId) {
        commentRepo.deleteById(commentId);
    }
}

