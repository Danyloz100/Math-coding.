import jakarta.xml.bind.DatatypeConverter;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.*;

public class Main {
    static char[][] table = {{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'},
            {'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p'},
            {'q', 'r', 's', 't', 'u', 'v', 'w', 'x'},
            {'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F'},
            {'G', 'H', 'I', 'J', 'R', 'L', 'M', 'N'},
            {'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V'},
            {'W', 'X', 'Y', 'Z', '0', '1', '2', '3'},
            {'4', '5', '6', '7', '8', '9', ' ', ','}};

    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));

        System.out.println("Lab3 - 1\n" +
                "Lab1 - 2\n" +
                "Lab9 - 3\n" +
                "Lab7 - 4\n" +
                "Lab10 - 5\n");

        String menu = reader.readLine();
        switch (menu) {
            case "1":
                lab3();
                break;
            case "2":
                lab1(reader);
                break;
            case"3":
                lab9(reader);
                break;
            case"4":
                lab7();
                break;
            case"5":
                lab10();
                break;
        }
    }

    private static void lab10() throws Exception {
        String input = "HELLO WORLD";
        KeyPair keyPair = DSA.Generate_RSA_KeyPair();

        byte[] signature
                = DSA.Create_Digital_Signature(
                input.getBytes(),
                keyPair.getPrivate());
        System.out.println("Signature Value:\n " + DatatypeConverter.printHexBinary(signature));
        System.out.println("Verification: " +
                DSA.Verify_Digital_Signature(input.getBytes(), signature, keyPair.getPublic()));
    }

    private static void lab7() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        String input = "Hello World";
        SecretKey key = AES.generateKey(128);
        IvParameterSpec ivParameterSpec = AES.generateIv();
        String algorithm = "AES/CBC/PKCS5Padding";
        String cipherText = AES.encrypt(algorithm, input, key, ivParameterSpec);
        System.out.println("Зашифрування: " + cipherText + "\n");
        String plainText = AES.decrypt(algorithm, cipherText, key, ivParameterSpec);
        System.out.println("Розшифрування: " + plainText);
        System.out.println();
    }

    private static void lab9(BufferedReader reader) throws IOException, NoSuchAlgorithmException {
        String input = reader.readLine();
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedhash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
        System.out.println(bytesToHex(encodedhash));
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    private static void lab3() {
        String text = "123456ABCD132536";
        String key = "AABB09182736CCDD";

        DES cipher = new DES();
        System.out.println("Шифрування\n");
        text = cipher.encrypt(text, key);
        System.out.println("Результат: " + text.toUpperCase() + "\n");
        System.out.println("Розшифрування\n");
        text = cipher.decrypt(text, key);
        System.out.println("Результат: " + text.toUpperCase());
    }

    private static void lab1(BufferedReader reader) throws IOException {
        String name = reader.readLine();
        System.out.println("Ви увели: " + name);
        int lengths = name.length();
        StringBuilder result = new StringBuilder("Результат: ");
        char[] chars = name.toCharArray();
        for (int k = 0; k < lengths; k++) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (table[i][j] == chars[k]) {
                        result.append(table[0][j]).append(table[0][i]);
                    }
                }
            }
        }
        System.out.println(result);

        String decode = reader.readLine();
        System.out.println("Ви увели: " + decode);
        int decodeLengths = decode.length();
        if (decodeLengths % 2 == 0) {
            char[] decodeChars = decode.toCharArray();
            int j, k;
            StringBuilder decodeResult = new StringBuilder("Результат: ");
            for (int i = 0; i < decodeLengths; i = i + 2) {
                for (j = 0; j < 8; j++) {
                    if (decodeChars[i] == table[0][j]) break;
                }

                for (k = 0; k < 8; k++) {
                    if (decodeChars[i + 1] == table[0][k]) break;
                }

                decodeResult.append(table[k][j]);
            }
            System.out.println(decodeResult);
        } else {
            throw new RuntimeException("Довжина уведеного тексту має ділитись на 2");
        }
    }
}
