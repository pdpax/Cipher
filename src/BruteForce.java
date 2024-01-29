import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;
import java.util.StringTokenizer;

public class BruteForce {
    private static final String ALERT = "Note that using brute force will delete all existing files in src\\Decrypted\\Brute Candidates.\nPress Enter to Continue...";
    private static final String NOT_ELIGIBLE = "The text is not eligible for brute force as it does not contain long enough words.\nPress Enter to Continue...";
    private static final String BRUTE_SUCCESS = "Brute Force Succeeded!";
    private static final String BRUTE_FAIL = "Brute Force Failed...";
    private Cipher cipher;

    public BruteForce(Cipher cipher) {
        this.cipher = cipher;
    }

    protected void perform() {

        boolean isEligible = this.checkBruteForceEligibility();
        if (!isEligible) {
            System.out.print(NOT_ELIGIBLE);
            new Scanner(System.in).nextLine();
            return;
        }

        System.out.print("\n" + ALERT);
        new Scanner(System.in).nextLine();
        clearBruteCandidates();

        String path = "src/Dictionary/Words/" + this.cipher.dictionary.lang + ".txt";
        try (FileReader fr = new FileReader(path); BufferedReader bf = new BufferedReader(fr);) {
            String dictionary = bf.readLine();
            boolean matchFound;
            boolean isDecrypted = false;
            for (int i = 0; i < cipher.dictionary.alphabet.size(); i++) {    //all possible keys
                matchFound = false;
                cipher.key = i;
                cipher.useKey();    //updating cipher.outputText
                //FileOutput.saveToFile(cipher);

                Scanner scanner = new Scanner(this.cipher.outputText);
                while (scanner.hasNext()) {
                    String word = scanner.next();
                    if (dictionary.contains(" " + word.toUpperCase() + " ")) {
                        matchFound = true;
                        isDecrypted = true;
                        System.out.println("WORD FOUND: " + word);
                        FileOutput.saveToFile(cipher);
                        scanner.close();
                        break;
                    }
                }
                scanner.close();
                /*
                if (matchFound) {
                    break;
                }

                 */
            }

            if (isDecrypted) {
                System.out.println(BRUTE_SUCCESS);
            } else {
                this.cipher.outputText = null;
                System.out.println(BRUTE_FAIL);
            }
            System.out.println("Press Enter to Continue...");
            new Scanner(System.in).nextLine();

        } catch (
                Exception e) {
            e.printStackTrace();
        }

    }

    private boolean checkBruteForceEligibility() {
        StringTokenizer tokenizer = new StringTokenizer(this.cipher.inputText);
        boolean isEligible = false;
        final int MIN_LENGTH = 2;
        while (tokenizer.hasMoreTokens()) {
            if (tokenizer.nextToken().length() > MIN_LENGTH) {
                isEligible = true;
                break;
            }
        }
        return isEligible;
    }

    private void clearBruteCandidates() {

        String directoryPath = "src/Decrypted/Brute Candidates/";
        File directory = new File(directoryPath);
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    file.delete();
                }
            }
        }
    }
}
