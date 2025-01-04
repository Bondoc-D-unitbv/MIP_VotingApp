package voting;

import java.util.Map;

public interface VotingSystem {
    void castVote(String option);
    Map<String, Integer> getVotes();
}
