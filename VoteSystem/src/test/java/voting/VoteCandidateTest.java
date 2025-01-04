package voting;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class VoteCandidateTest {

    @Test
    public void testVoteCasting() {
        VoteCandidate vote = new VoteCandidate();
        vote.castVote("John Doe");
        assertEquals(1, (int)vote.getVotes().get("John Doe"));
    }

    @Test
    public void testVoteCastingMultiple() {
        VoteCandidate vote = new VoteCandidate();
        vote.castVote("John Doe");
        vote.castVote("Jane Smith");
        assertEquals(1, (int)vote.getVotes().get("Jane Smith"));
    }
}
