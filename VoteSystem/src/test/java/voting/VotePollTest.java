package voting;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class VotePollTest {

    @Test
    public void testPollCasting() {
        VotePoll pollVote = new VotePoll();
        pollVote.castVote("Option 1");
        pollVote.castVote("Option 2");
        pollVote.castVote("Option 1");

        Map<String, Integer> votes = pollVote.getVotes();
        assertEquals(2, (int)votes.get("Option 1"));
        assertEquals(1, (int)votes.get("Option 2"));
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
