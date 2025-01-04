package voting;

public class VoteCandidate extends Vote {
    @Override
    public void specificVoteBehavior(String candidate) {
        System.out.println("Casting vote for candidate: " + candidate);
        castVote(candidate);
    }
}