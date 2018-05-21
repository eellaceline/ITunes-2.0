package sample.Handlers;

import java.security.SecureRandom;
import java.util.Random;

public class Handler_Password {

    // This encryption is really simple. Just swapping the letter 5 steps to the right
    public static String encryption(String pw) {
        String encryptedPW = "";
        byte[] letters = pw.getBytes();

        for (int i=0; i < letters.length; i++) {
            //System.out.println("BE: " + Character.toString((char)letters[i]));
            System.out.println("i: "+i);
            System.out.println("BE: " + letters[i] + (char)letters[i]);
            letters[i] = (byte) (letters[i] + 5);
            System.out.println("EF: " + letters[i] + (char)letters[i]);
            System.out.println("---------------");
            encryptedPW += Character.toString((char)letters[i]);
            //System.out.println("AE: " + Character.toString((char)letters[i]));
        }

        return encryptedPW;
    }

    // This decryption is really simple. Just swapping the letter 5 steps to the left
    public static String decryption(String encryptedPW) {
        String decryptedPW = "";
        byte[] letters = encryptedPW.getBytes();

        for (int i=0; i < letters.length; i++) {
            letters[i] = (byte) (letters[i] - 5);
            decryptedPW += Character.toString((char)letters[i]);
        }
        return decryptedPW;
    }
    public static String generateRandomPassword(){
        Random random = new SecureRandom();

        int password = 8;
        String letters = "abcdefgh123456";

        String pw = "";
        for (int i = 0; i<password; i++){
            int index = (random.nextInt(letters.length()));
            pw += letters.substring(index, index+1);

        }return pw;
    }
    
}
