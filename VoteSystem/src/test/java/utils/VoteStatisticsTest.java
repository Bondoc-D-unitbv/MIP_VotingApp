package utils;

import org.junit.Test;
import voting.VoteCandidate;
import voting.VotePoll;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class VoteStatisticsTest {

    @Test
    public void testGetTotalVotes() {
        VoteCandidate candidateVote = new VoteCandidate();
        candidateVote.castVote("Alice");
        candidateVote.castVote("Alice");
        candidateVote.castVote("Bob");

        int totalVotes = VoteStatistics.getTotalVotes(candidateVote);
        assertEquals(3, totalVotes);
    }

    @Test
    public void testGetVotePercentages() {
        Map<String, Integer> votes = new HashMap<>();
        votes.put("Candidat1", 50);
        votes.put("Candidat2", 30);
        votes.put("Candidat3", 20);

        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));

        VoteStatistics.getVotePercentages(votes, "Test Category");

        String expectedOutput = "\n--- Test Category Voting Percentages ---\n" +
                "Candidat1: 50 votes (50.00%)\n" +
                "Candidat2: 30 votes (30.00%)\n" +
                "Candidat3: 20 votes (20.00%)\n" +
                "Total Votes: 100.0";

        String normalizedExpected = expectedOutput.replace("\r\n", "\n").trim();
        String normalizedActual = outContent.toString().replace("\r\n", "\n").trim();

        System.out.println("Expected Output:\n" + normalizedExpected);
        System.out.println("Actual Output:\n" + normalizedActual);

        assertEquals(normalizedExpected, normalizedActual);
    }

    @Test
    public void testGetVotePercentages_NoVotes() {
        Map<String, Integer> votes = new HashMap<>();

        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));

        VoteStatistics.getVotePercentages(votes, "Empty Category");

        String expectedOutput = "\n--- Empty Category Voting Percentages ---\nNo votes cast yet.";

        String normalizedExpected = expectedOutput.replace("\r\n", "\n").trim();
        String normalizedActual = outContent.toString().replace("\r\n", "\n").trim();

        System.out.println("Expected Output:\n" + normalizedExpected);
        System.out.println("Actual Output:\n" + normalizedActual);

        assertEquals(normalizedExpected, normalizedActual);

        System.setOut(System.out);
    }

    @Test
    public void testGetWinner() {
        VotePoll pollVote = new VotePoll();
        pollVote.castVote("Option 1");
        pollVote.castVote("Option 2");
        pollVote.castVote("Option 1");

        String winner = VoteStatistics.getWinner(pollVote);
        assertEquals("Option 1", winner);
    }
}
