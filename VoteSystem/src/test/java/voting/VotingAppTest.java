package voting;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class VotingAppTest {

    private VotingSystem candidateVote;
    private VotingSystem pollVote;
    private VotingApp votingApp;

    @Before
    public void setUp() {
        candidateVote = new VoteCandidate();
        pollVote = new VotePoll();

        votingApp = new VotingApp(candidateVote, pollVote);
    }

    @Test
    public void testCastVoteForCandidate() {
        String candidate = "John Doe";
        candidateVote.castVote(candidate);

        Map<String, Integer> votes = candidateVote.getVotes();
        assertEquals(Integer.valueOf(1), votes.get(candidate));
    }

    @Test
    public void testCastVoteForPoll() {
        String pollOption = "Option A";
        pollVote.castVote(pollOption);

        Map<String, Integer> votes = pollVote.getVotes();
        assertEquals(Integer.valueOf(1), votes.get(pollOption));
    }

    @Test
    public void testShowStatistics() {
        candidateVote.castVote("John Doe");
        candidateVote.castVote("Jane Doe");

        pollVote.castVote("Option A");
        pollVote.castVote("Option A");
        pollVote.castVote("Option B");

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        votingApp.showStatistics();

        assertTrue(outContent.toString().contains("John Doe: 1 votes"));
        assertTrue(outContent.toString().contains("Option A: 2 votes"));

        System.setOut(System.out);
    }

    @Test
    public void testSaveToJson() {
        VotingSystem candidateVote = new VoteCandidate();
        VotingSystem pollVote = new VotePoll();

        candidateVote.castVote("John Doe");
        pollVote.castVote("Option A");

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        VotingApp votingApp = new VotingApp(candidateVote, pollVote);

        votingApp.saveToJson();

        assertFalse(outContent.toString().contains("Invalid type for candidateVote"));

        System.setOut(System.out);
    }

    @Test
    public void testLoadFromJson() {
        candidateVote.castVote("John Doe");
        pollVote.castVote("Option A");

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        votingApp.loadFromJson();

        assertTrue(outContent.toString().contains("Candidates: {John Doe=1}"));
        assertTrue(outContent.toString().contains("Poll Options: {Option A=1}"));

        System.setOut(System.out);
    }
}
