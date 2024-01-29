import java.io.*;
import java.util.Scanner;

public class FileOutput {

    public static void saveToFile(Cipher cipher) {

        String saveDirectory = switch (cipher.task) {
            case Cipher.ENCRYPTION -> "src/Encrypted/";
            case Cipher.DECRYPTION -> "src/Decrypted/";
            case Cipher.BRUTEFORCE -> "src/Decrypted/Brute Candidates/";
            default -> null;
        };
        String fileName = setFileName(cipher, saveDirectory);
        try (FileWriter fw = new FileWriter(saveDirectory + fileName);
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(cipher.outputText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("File Successfully Saved As " + fileName);
        if (!cipher.task.equals(Cipher.BRUTEFORCE)) { //to avoid prompting press enter each time if there are multiple brute candidates
            System.out.print("Press Enter to Continue...");
            new Scanner(System.in).nextLine();
        }
    }

    private static String setFileName(Cipher cipher, String saveDirectory) {
        int fileCount = 0;
        String fileName = "";
        while (true) {
            if (cipher.task.equals(Cipher.ENCRYPTION)) {
                fileName = fileCount + "K" + cipher.key + ".txt";
            } else {
                fileName = fileCount + ".txt";  //Decryption or BruteForce or StatisticalAnalysis
            }
            File file = new File(saveDirectory, fileName);
            if (file.exists()) {
                fileCount++;
            } else {
                break;
            }
        }
        return fileName;
    }
}
