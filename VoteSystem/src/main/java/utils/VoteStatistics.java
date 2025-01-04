package utils;

import voting.VotingSystem;

import java.util.Locale;
import java.util.Map;

public class VoteStatistics {

    public static int getTotalVotes(VotingSystem votingSystem) {
        return votingSystem.getVotes().values().stream().mapToInt(Integer::intValue).sum();
    }

    public static void getVotePercentages(Map<String, Integer> votes, String categoryName) {
        System.out.println("\n--- " + categoryName + " Voting Percentages ---");

        double totalVotes = votes.values().stream().mapToInt(Integer::intValue).sum();

        if (totalVotes == 0) {
            System.out.println("No votes cast yet.");
            return;
        }

        votes.entrySet().stream()
                .sorted((entry1, entry2) -> Integer.compare(entry2.getValue(), entry1.getValue()))
                .forEach(entry -> {
                    String option = entry.getKey();
                    int count = entry.getValue();
                    double percentage = (count * 100.0) / totalVotes;
                    System.out.printf(Locale.US, "%s: %d votes (%.2f%%)\n", option, count, percentage);
                });

        System.out.println("Total Votes: " + totalVotes);
    }


    public static String getWinner(VotingSystem votingSystem) {
        Map<String, Integer> votes = votingSystem.getVotes();

        int maxVotes = votes.values().stream()
                .max(Integer::compareTo)
                .orElse(0);

        long countMaxVotes = votes.values().stream()
                .filter(vote -> vote == maxVotes)
                .count();

        if (countMaxVotes > 1) {
            return "Draw";
        }

        return votes.entrySet()
                .stream()
                .filter(entry -> entry.getValue() == maxVotes)
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse("No votes cast");
    }

}