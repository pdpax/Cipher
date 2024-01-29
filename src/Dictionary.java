import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Dictionary {
    String lang; //en or sb
    List<String> alphabet = new ArrayList<>();

    public Dictionary(String lang) {
        this.lang = lang;
        String path = "src/Dictionary/Alphabets/" + lang + "Alphabet.txt";
        String pathDic = "src/Dictionary/Words/" + lang + ".txt";
        try (FileReader fr = new FileReader(path); BufferedReader br = new BufferedReader(fr);) {
            while (br.ready()) {
                alphabet.add(br.readLine());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
