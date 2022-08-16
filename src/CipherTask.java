//Byron Goldsack
//20111390

public class CipherTask<E, F> extends Task
{
    private String progress;
    private String s;
    public static final String alphabet = "abcdefghijklmnopqrstuvwxyz";

    public CipherTask(String E) {
        super(E);
        this.s = E;
    }

    @Override
    public void run() {
        s = s.toLowerCase();
        String encrypted = "";

        for(int i = 0; i < s.length(); i++)
        {
            char current = s.charAt(i);
            if(current != ' ') {
                int pos = alphabet.indexOf(s.charAt(i));
                char newChar = alphabet.charAt((13 + pos) % 26); // Caesar/ROT13 Cipher
                encrypted += newChar;
            }
            else{
                encrypted += ' ';
            }
        }

        notifyAll("Encrypted message: " + encrypted);
    }
}
