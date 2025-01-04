package voting;

import java.util.HashMap;
import java.util.Map;

public abstract class Vote implements VotingSystem {
    protected Map<String, Integer> votes = new HashMap<>();
    @Override
    public void castVote(String option) {
        votes.put(option, votes.getOrDefault(option, 0) + 1);
    }

    @Override
    public Map<String, Integer> getVotes() {
        return votes;
    }

    public abstract void specificVoteBehavior(String input);
}