import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.util.Random;
import java.util.Scanner;

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
                "Lab10 - 5\n" +
                "Lab8 - 6\n");

        String menu = reader.readLine();
        switch (menu) {
            case "1":
                lab3();
                break;
            case "2":
                lab1(reader);
                break;
            case "3":
                lab9(reader);
                break;
            case "4":
                lab10();
                break;
            case "5":
                lab8(reader);
                break;
        }
    }

    private static void lab8(BufferedReader reader) throws IOException {
        long P, G, x, a, y, bi, ka, kb;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Both the users should be agreed upon the public keys G and P");
        System.out.println("Enter value for public key G:");
        G = scanner.nextLong();
        System.out.println("Enter value for public key P:");
        P = scanner.nextLong();
        System.out.println("Enter value for private key a selected by user1:");
        a = scanner.nextLong();
        System.out.println("Enter value for private key b selected by user2:");
        bi = scanner.nextLong();
        x = DiffieHellman.calculatePower(G, a, P);
        y = DiffieHellman.calculatePower(G, bi, P);
        ka = DiffieHellman.calculatePower(y, a, P);
        kb = DiffieHellman.calculatePower(x, bi, P);
        System.out.println("Secret key for User1 is:" + ka);
        System.out.println("Secret key for User2 is:" + kb);
        BigInteger p, b, c, secretKey;
        Random sc = new SecureRandom();
        secretKey = new BigInteger(String.valueOf(ka));
        //
        // public key calculation
        //
        System.out.println("secretKey = " + secretKey);
        p = BigInteger.probablePrime(64, sc);
        b = new BigInteger("3");
        c = b.modPow(secretKey, p);
        System.out.println("p = " + p);
        System.out.println("b = " + b);
        System.out.println("c = " + c);
        //
        // Encryption
        //
        System.out.print("Enter your Big Number message -->");
        String s = reader.readLine();
        BigInteger X = new BigInteger(s);
        BigInteger r = new BigInteger(64, sc);
        BigInteger EC = X.multiply(c.modPow(r, p)).mod(p);
        BigInteger brmodp = b.modPow(r, p);
        System.out.println("Plaintext = " + X);
        System.out.println("r = " + r);
        System.out.println("EC = " + EC);
        System.out.println("b^r mod p = " + brmodp);
        //
        // Decryption
        //
        BigInteger crmodp = brmodp.modPow(secretKey, p);
        BigInteger d = crmodp.modInverse(p);
        BigInteger ad = d.multiply(EC).mod(p);
        System.out.println("\n\nc^r mod p = " + crmodp);
        System.out.println("d = " + d);
        System.out.println("Alice decodes: " + ad);

    }

    private static void lab10(){
        String msg = "9876543210987654321";
        BigInteger p, b, c, secretKey;
        Random sc = new SecureRandom();
        secretKey = new BigInteger("12345678901234567890");
        System.out.println("secretKey = " + secretKey);
        p = BigInteger.probablePrime(64, sc);
        b = new BigInteger("3");
        c = b.modPow(secretKey, p);
        System.out.println("p = " + p);
        System.out.println("b = " + b);
        System.out.println("c = " + c);
        System.out.print("Enter your Big Number message --> ");
        String s = msg;
        BigInteger X = new BigInteger(s);
        BigInteger r = new BigInteger(64, sc);
        BigInteger EC = X.multiply(c.modPow(r, p)).mod(p);
        BigInteger brmodp = b.modPow(r, p);
        System.out.println("Plaintext = " + X);
        System.out.println("\nEncryption");
        System.out.println("r = " + r);
        System.out.println("EC = " + EC);
        System.out.println("b^r mod p = " + brmodp);
        System.out.println("\nDecryption");
        BigInteger crmodp = brmodp.modPow(secretKey, p);
        BigInteger d = crmodp.modInverse(p);
        BigInteger ad = d.multiply(EC).mod(p);
        System.out.println("c^r mod p = " + crmodp);
        System.out.println("d = " + d);
        System.out.println("Alice decodes: " + ad);
    }

    private static void lab9(BufferedReader reader) throws IOException {
        String input = reader.readLine();
        System.out.println("0x" + MD5.toHexString(MD5.computeMD5(input.getBytes())) + " <== \"" + input + "\"");
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
