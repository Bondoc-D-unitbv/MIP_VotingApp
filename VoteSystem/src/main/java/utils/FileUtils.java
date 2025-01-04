package utils;

import voting.Vote;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class FileUtils {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static void saveVotesToJson(Vote vote, String filePath) {
        try {
            mapper.writeValue(new File(filePath), vote.getVotes());
            System.out.println("Votes have been saved to " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, Integer> loadVotesFromJson(String filePath) {
        File file = new File(filePath);
        if (file.exists() && file.length() > 0) {
            try {
                return mapper.readValue(file, mapper.getTypeFactory().constructMapType(Map.class, String.class, Integer.class));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("File not found or empty: " + filePath);
        }
        return null;
    }

}