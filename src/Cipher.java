public class Cipher {
    protected String inputText;
    protected String outputText;
    protected int key;
    private final int mode;
    protected static final String ENCRYPTION = "Encryption";
    protected static final String DECRYPTION = "Decryption";
    protected static final String BRUTEFORCE = "BruteForce";
    Dictionary dictionary;
    String task;
    private final int ALPHABET_LENGTH;
    private final int ALPHABET_MIN = 0;

    public Cipher(Dictionary dictionary, int taskIndex, String text, int key, int mode) {
        this.dictionary = dictionary;
        this.ALPHABET_LENGTH = this.dictionary.alphabet.size();
        if (taskIndex == 1) {
            this.task = ENCRYPTION;
        } else if (taskIndex == 2) {
            this.task = DECRYPTION;
        }
        this.inputText = text;
        this.key = key;
        this.mode = mode;
    }

    public void perform() {
        if (this.key != -1) {
            this.useKey();
            FileOutput.saveToFile(this);
        } else if (this.mode == 2) {
            this.task = BRUTEFORCE;
            BruteForce bruteForce = new BruteForce(this);
            bruteForce.perform();
        }
    }


    protected void useKey() {

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < this.inputText.length(); i++) {

            if (this.inputText.charAt(i) == '\r') {

            } else if (this.inputText.charAt(i) == '\n') {
                stringBuilder.append(System.lineSeparator());

            } else {
                String character = this.inputText.charAt(i) + "";
                int oldIndex = this.dictionary.alphabet.indexOf(character);
                int newIndex;
                if (this.task == ENCRYPTION) {
                    int tempIndex = oldIndex + this.key;
                    if (tempIndex > this.ALPHABET_LENGTH-1) {
                        newIndex = tempIndex - this.ALPHABET_LENGTH;
                    } else {
                        newIndex = tempIndex;
                    }
                } else { //if (this.task == DECRYPTION)
                    int tempIndex = oldIndex - this.key;
                    if (tempIndex < ALPHABET_MIN) {
                        newIndex = tempIndex + this.ALPHABET_LENGTH;
                    } else {
                        newIndex = tempIndex;
                    }
                }
                stringBuilder.append(this.dictionary.alphabet.get(newIndex));

            }
        }
        this.outputText = stringBuilder.toString();
    }
}
