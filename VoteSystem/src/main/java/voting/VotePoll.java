package voting;

public class VotePoll extends Vote {
    @Override
    public void specificVoteBehavior(String pollOption) {
        System.out.println("Casting vote for poll option: " + pollOption);
        castVote(pollOption);
    }
}

