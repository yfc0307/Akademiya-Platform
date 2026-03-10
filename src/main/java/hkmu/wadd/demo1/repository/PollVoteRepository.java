package hkmu.wadd.demo1.repository;

import hkmu.wadd.demo1.model.AppUser;
import hkmu.wadd.demo1.model.Poll;
import hkmu.wadd.demo1.model.PollVote;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PollVoteRepository extends JpaRepository<PollVote, Long> {
    Optional<PollVote> findByPollAndUser(Poll poll, AppUser user);
    boolean existsByPollAndUser(Poll poll, AppUser user);
    List<PollVote> findByUser(AppUser user);
}

