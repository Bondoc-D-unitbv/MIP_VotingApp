package voting;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class VoteTest {

    @Test
    public void testCastVote() {
        VoteCandidate candidateVote = new VoteCandidate();
        candidateVote.castVote("Alice");
        candidateVote.castVote("Alice");
        candidateVote.castVote("Bob");

        Map<String, Integer> votes = candidateVote.getVotes();
        assertEquals(2, (int)votes.get("Alice"));
        assertEquals(1, (int)votes.get("Bob"));
    }

    @Test
    public void testGetVotes() {
        VotePoll pollVote = new VotePoll();
        pollVote.castVote("Option 1");
        pollVote.castVote("Option 2");
        pollVote.castVote("Option 1");

        Map<String, Integer> votes = pollVote.getVotes();
        assertNotNull(votes);
        assertEquals(2, (int)votes.get("Option 1"));
        assertEquals(1, (int)votes.get("Option 2"));
    }
}
