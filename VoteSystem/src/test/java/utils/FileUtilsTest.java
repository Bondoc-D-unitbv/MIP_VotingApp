package utils;

import junit.framework.TestCase;
import org.junit.Test;
import voting.VoteCandidate;

import java.util.Map;

public class FileUtilsTest extends TestCase {

    @Test
    public void testSaveAndLoadVotes() {
        VoteCandidate candidateVote = new VoteCandidate();
        candidateVote.castVote("Alice");
        candidateVote.castVote("Bob");
        String filePath = "testVotes.json";

        FileUtils.saveVotesToJson(candidateVote, filePath);

        Map<String, Integer> loadedVotes = FileUtils.loadVotesFromJson(filePath);

        assertNotNull(loadedVotes);
        assertEquals(1, (int)loadedVotes.get("Alice"));
        assertEquals(1, (int)loadedVotes.get("Bob"));
    }
}