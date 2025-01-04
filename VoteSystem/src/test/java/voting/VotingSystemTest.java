package voting;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class VotingSystemTest {

    private VotingSystem candidateVotingSystem;
    private VotingSystem pollVotingSystem;

    @Before
    public void setUp() {
        candidateVotingSystem = new VoteCandidate();
        pollVotingSystem = new VotePoll();
    }

    @Test
    public void testCastVote_Candidate() {
        String candidateName = "John Doe";
        candidateVotingSystem.castVote(candidateName);

        Map<String, Integer> votes = candidateVotingSystem.getVotes();
        assertEquals(1, votes.get(candidateName).intValue());

        candidateVotingSystem.castVote(candidateName);

        assertEquals(2, votes.get(candidateName).intValue());
    }

    @Test
    public void testCastVote_PollOption() {
        String pollOption = "Option A";
        pollVotingSystem.castVote(pollOption);

        Map<String, Integer> votes = pollVotingSystem.getVotes();
        assertEquals(1, votes.get(pollOption).intValue());

        pollVotingSystem.castVote(pollOption);

        assertEquals(2, votes.get(pollOption).intValue());
    }

    @Test
    public void testGetVotes_Empty() {
        Map<String, Integer> candidateVotes = candidateVotingSystem.getVotes();
        Map<String, Integer> pollVotes = pollVotingSystem.getVotes();

        assertTrue(candidateVotes.isEmpty());
        assertTrue(pollVotes.isEmpty());
    }

    @Test
    public void testGetVotes_AfterVoting() {
        candidateVotingSystem.castVote("Alice");
        candidateVotingSystem.castVote("Bob");
        pollVotingSystem.castVote("Yes");
        pollVotingSystem.castVote("No");

        Map<String, Integer> candidateVotes = candidateVotingSystem.getVotes();
        assertEquals(1, candidateVotes.get("Alice").intValue());
        assertEquals(1, candidateVotes.get("Bob").intValue());

        Map<String, Integer> pollVotes = pollVotingSystem.getVotes();
        assertEquals(1, pollVotes.get("Yes").intValue());
        assertEquals(1, pollVotes.get("No").intValue());
    }

    @Test
    public void testGetVotes_NoVotes() {
        Map<String, Integer> candidateVotes = candidateVotingSystem.getVotes();
        Map<String, Integer> pollVotes = pollVotingSystem.getVotes();

        assertTrue(candidateVotes.isEmpty());
        assertTrue(pollVotes.isEmpty());
    }

    @Test
    public void testVotesAreIndependent() {
        candidateVotingSystem.castVote("John Doe");
        pollVotingSystem.castVote("Option 1");

        Map<String, Integer> candidateVotes = candidateVotingSystem.getVotes();
        Map<String, Integer> pollVotes = pollVotingSystem.getVotes();

        assertTrue(candidateVotes.containsKey("John Doe"));
        assertTrue(pollVotes.containsKey("Option 1"));
        assertEquals(1, candidateVotes.get("John Doe").intValue());
        assertEquals(1, pollVotes.get("Option 1").intValue());
    }

}