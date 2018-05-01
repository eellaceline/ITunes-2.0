package sample.Handlers;

public class Handler_Password {

    // This encryption is really simple. Just swapping the letter 5 steps to the right
    public static String encryption(String pw) {
        String encryptedPW = "";
        byte[] letters = pw.getBytes();

        for (int i=0; i < letters.length; i++) {
            //System.out.println("BE: " + Character.toString((char)letters[i]));
            letters[i] = (byte) (letters[i] + 5);
            encryptedPW += Character.toString((char)letters[i]);
            //System.out.println("AE: " + Character.toString((char)letters[i]));
        }

        return encryptedPW;
    }

    // This decryption is really simple. Just swapping the letter 5 steps to the left
    public static String decryption(String encryptedPW) {
        String decryptedPW = "";
        System.out.println(encryptedPW);
        byte[] letters = encryptedPW.getBytes();

        for (int i=0; i < letters.length; i++) {
            letters[i] = (byte) (letters[i] - 5);
            decryptedPW += Character.toString((char)letters[i]);
        }
        System.out.println(decryptedPW);
        return decryptedPW;
    }

}
