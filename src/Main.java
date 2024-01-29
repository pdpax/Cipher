import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

import static java.lang.System.exit;

public class Main {
    public static final String TITLE = "\n\n==========================\n      CRYPTOGRAPHY      \n==========================\n";
    public static final String TASK_SELECT = "Task:\n1.Encryption\n2.Decryption\n3.Exit\nSelect Task: ";
    private static final String SELECT_SOURCE = "\nSource:\n1.File\n2.User Input\nSelect Source: ";
    private static final String SELECT_MODE = "\nMode:\n1.Use a Key\n2.Brute Force\nSelect Mode: ";
    private static final String ENTER_ENCRYPTION_KEY = "\nEnter Encryption Key: ";
    private static final String ENTER_DECRYPTION_KEY = "\nEnter Decryption Key: ";


    public static void main(String[] args) {
        folderInit();
        while(true) {
            Cipher cipher = pathSelect();
            cipher.perform();
        }

    }

    private static Cipher pathSelect() {
        while (true) {
            System.out.println(TITLE);

            List<String> langCodes = getLangList();
            promptLanguage(langCodes);
            int langIndex = UserInput.getInt(1, langCodes.size());
            String lang = langCodes.get(langIndex - 1);   //for constructor
            Dictionary dictionary = new Dictionary(lang);
            System.out.println();

            System.out.print(TASK_SELECT);
            int task = UserInput.getInt(1, 3);      //for constructor
            if (task == 3) {
                exit(0);
            }

            System.out.println();

            System.out.print(SELECT_SOURCE);
            int source = UserInput.getInt(1, 2);

            String text;                            //for constructor
            if (source == 1) {
                text = FileInput.getFile("File");
            } else {
                text = FileInput.getFile("UserInput");
            }

            int mode = -1;
            int key = -1;
            if (task == 1) {
                System.out.print(ENTER_ENCRYPTION_KEY);
                key = UserInput.getInt(1, dictionary.alphabet.size() - 1);
            } else if (task == 2) {
                System.out.print(SELECT_MODE);
                mode = UserInput.getInt(1, 2);
                if (mode == 1) {
                    System.out.print(ENTER_DECRYPTION_KEY);
                    key = UserInput.getInt(1, dictionary.alphabet.size() - 1);
                }
            }
            return new Cipher(dictionary, task, text, key, mode);
        }
    }

    private static void promptLanguage(List<String> langCodes) {
        String folderPath = "src/Dictionary/Words/";
        StringBuilder langSelect = new StringBuilder();
        langSelect.append("Language:");
        //"Language:\n1.English\n2.Serbian\nSelect Language: ";
        List<String> lang = new ArrayList<>();
        try {
            for (int i = 0; i < langCodes.size(); i++) {
                FileReader fr = new FileReader(folderPath + langCodes.get(i) + ".txt");
                BufferedReader br = new BufferedReader(fr);
                Scanner sc = new Scanner(br);
                lang.add(sc.next());
                fr.close();
                br.close();
            }

            for (int i = 0; i < lang.size(); i++) {
                langSelect.append("\n").append(i + 1).append(".").append(lang.get(i));
            }
            langSelect.append("\nSelect Language: ");

            System.out.print(langSelect);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<String> getLangList() { //returns en sb etc as prefix to ".txt"
        List<String> langCodes = new ArrayList<>();
        String folderPath = "src/Dictionary/Words";
        try {
            File folder = new File(folderPath);
            if (folder.isDirectory()) {
                File[] files = folder.listFiles();
                if (files != null) {
                    for (File file : files) {
                        String fileName = file.getName();
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < fileName.length(); i++) {
                            if (fileName.charAt(i) != '.') {
                                sb.append(fileName.charAt(i));
                            } else {
                                break;
                            }
                        }
                        langCodes.add(sb.toString());
                    }
                }
            }
            return langCodes;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void folderInit() {
        String folderPath = "src/Encrypted";
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdir();
        }

        folderPath = "src/Decrypted/";
        folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdir();
        }
        folderPath = "src/Decrypted/Brute Candidates/";
        folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdir();
        }

    }

}