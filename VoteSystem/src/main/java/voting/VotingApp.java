package voting;

import org.junit.platform.engine.discovery.ClassNameFilter;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherFactory;

import utils.FileUtils;
import utils.VoteStatistics;

import java.util.Map;
import java.util.Scanner;


public class VotingApp {
    private VotingSystem candidateVote;
    private VotingSystem pollVote;

    private Scanner scanner;

    public VotingApp(VotingSystem candidateVote, VotingSystem pollVote) {
        this.candidateVote = candidateVote;
        this.pollVote = pollVote;
        this.scanner = new Scanner(System.in);
    }

    public int getValidChoice(int min, int max) {
        int choice = -1;
        boolean valid = false;

        while (!valid) {
            System.out.print("Enter your choice: ");
            String input = scanner.nextLine();

            try {
                choice = Integer.parseInt(input);
                if (choice >= min && choice <= max)
                {
                    valid = true;
                }
                else
                {
                    System.out.println(choice + " is not a valid choice.");
                    System.out.println("Please enter a value between " + min + " and " + max + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer between " + min + " and " + max + ".");
            }
        }

        return choice;
    }

    public void run()
    {
        boolean running = true;

        int minChoice = 1;
        int maxChoice = 6;

        int choice;

        while (running) {
            System.out.println("\n--- Voting System ---");
            System.out.println("1. Vote for a Candidate");
            System.out.println("2. Vote for a Poll Option");
            System.out.println("3. Show Statistics");
            System.out.println("4. Save votes to JSON");
            System.out.println("5. Load votes from JSON");
            System.out.println("6. Exit");
            System.out.println(" ");
            //System.out.println("7. Utils - Run Unit Tests");

            choice = getValidChoice(minChoice, maxChoice);

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter candidate's name: ");
                    String candidate = scanner.nextLine();
                    candidateVote.castVote(candidate);
                }
                case 2 -> {
                    System.out.print("Enter poll option: ");
                    String pollOption = scanner.nextLine();
                    pollVote.castVote(pollOption);
                }
                case 3 -> showStatistics();
                case 4 -> saveToJson();
                case 5 -> {
                    String candidateFile = "candidateVotes.json";
                    String pollFile = "pollVotes.json";

                    Map<String, Integer> loadedCandidateVotes = FileUtils.loadVotesFromJson(candidateFile);
                    if (loadedCandidateVotes != null) {
                        for (Map.Entry<String, Integer> entry : loadedCandidateVotes.entrySet()) {
                            String candidate = entry.getKey();
                            int votes = entry.getValue();
                            for (int i = 0; i < votes; i++) {
                                candidateVote.castVote(candidate);
                            }
                        }
                    }

                    Map<String, Integer> loadedPollVotes = FileUtils.loadVotesFromJson(pollFile);
                    if (loadedPollVotes != null) {
                        for (Map.Entry<String, Integer> entry : loadedPollVotes.entrySet()) {
                            String pollOption = entry.getKey();
                            int votes = entry.getValue();
                            for (int i = 0; i < votes; i++) {
                                pollVote.castVote(pollOption);
                            }
                        }
                    }

                    System.out.println("\n--- Loaded and Casted Votes ---");
                    System.out.println("Candidates: " + candidateVote.getVotes());
                    System.out.println("Poll Options: " + pollVote.getVotes());
                }

                case 6 -> running = false;
                case 7 -> runUnitTests();
                default -> System.out.println("Invalid choice. Try again.");
            }
        }

        scanner.close();
    }

    public void showStatistics() {
        System.out.println("\n--- Vote Statistics ---");

        System.out.println("Candidates:");
        candidateVote.getVotes().forEach((key, value) -> System.out.println(key + ": " + value + " votes"));
        VoteStatistics.getVotePercentages(candidateVote.getVotes(), "Candidate");

        System.out.println("\nTotal Candidate Votes: " + VoteStatistics.getTotalVotes(candidateVote));
        System.out.println("Candidate Winner: " + VoteStatistics.getWinner(candidateVote));

        System.out.println("\nPoll Options:");
        pollVote.getVotes().forEach((key, value) -> System.out.println(key + ": " + value + " votes"));
        VoteStatistics.getVotePercentages(pollVote.getVotes(), "Poll");

        System.out.println("\nTotal Poll Votes: " + VoteStatistics.getTotalVotes(pollVote));
        System.out.println("Poll Winner: " + VoteStatistics.getWinner(pollVote));
    }

    public void saveToJson() {
        String candidateFile = "candidateVotes.json";
        String pollFile = "pollVotes.json";

        if (candidateVote instanceof VoteCandidate) {
            FileUtils.saveVotesToJson((VoteCandidate) candidateVote, candidateFile);
        } else {
            System.out.println("Invalid type for candidateVote");
        }

        if (pollVote instanceof VotePoll) {
            FileUtils.saveVotesToJson((VotePoll) pollVote, pollFile);
        } else {
            System.out.println("Invalid type for pollVote");
        }
    }

    public void loadFromJson() {
        String candidateFile = "candidateVotes.json";
        String pollFile = "pollVotes.json";

        Map<String, Integer> loadedCandidateVotes = FileUtils.loadVotesFromJson(candidateFile);
        Map<String, Integer> loadedPollVotes = FileUtils.loadVotesFromJson(pollFile);

        System.out.println("\n--- Loaded Votes ---");
        System.out.println("Candidates: " + loadedCandidateVotes);
        System.out.println("Poll Options: " + loadedPollVotes);
    }

    public void runUnitTests() {
        Launcher launcher = LauncherFactory.create();

        try {
            boolean testClassesExist = checkIfTestClassesExist("voting.VoteCandidateTest", "voting.VoteStatisticsTest", "voting.VoteTest");
            if (!testClassesExist) {
                System.out.println("One or more test classes not found. Skipping test execution.");
                return;
            }

            LauncherDiscoveryRequest request = org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder.request()
                    .selectors(
                            DiscoverySelectors.selectClass("voting.VoteCandidateTest"),
                            DiscoverySelectors.selectClass("voting.VoteStatisticsTest"),
                            DiscoverySelectors.selectClass("voting.VoteTest")
                    )
                    .filters(ClassNameFilter.includeClassNamePatterns(".*Test.*"))
                    .build();

            launcher.execute(request);
        } catch (Exception e) {
            System.out.println("Error running unit tests: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean checkIfTestClassesExist(String... classNames) {
        for (String className : classNames) {
            try {
                Class.forName(className);
            } catch (ClassNotFoundException e) {
                System.out.println("Test class not found: " + className);
                return false;
            }
        }
        return true;
    }

}
