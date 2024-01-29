import java.io.BufferedReader;
import java.io.FileReader;

public class FileInput {
    String source;
    private static final String ENTER_PATH = "\nEnter File Path: ";
    private static final String ENTER_TEXT = "\nEnter Text: ";


    public static String getFile(String source) {

        if (source.equals("File")) {
            System.out.print(ENTER_PATH);
            try (FileReader fr = new FileReader(UserInput.getPath());
                 BufferedReader br = new BufferedReader(fr);) {
                StringBuilder sb = new StringBuilder();
                while (br.ready()) {
                    //sb.append(br.readLine());
                    char character = (char) br.read(); //this way we preserve the new line characters
                    sb.append(character);
                }
                return sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        } else if (source.equals("UserInput")) {
            System.out.print(ENTER_TEXT);
            return UserInput.getString();

        } else {
            return "";
        }
    }
}
