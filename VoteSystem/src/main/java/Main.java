import voting.VotingSystem;
import voting.VoteCandidate;
import voting.VotePoll;
import voting.VotingApp;

public class Main {
    public static void main(String[] args) {
        VotingSystem candidateVote = new VoteCandidate();
        VotingSystem pollVote = new VotePoll();

        VotingApp app = new VotingApp(candidateVote, pollVote);

        app.run();
    }
}
