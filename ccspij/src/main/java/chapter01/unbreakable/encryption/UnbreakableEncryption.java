package chapter01.unbreakable.encryption;

import java.util.Random;

public class UnbreakableEncryption {
    private static byte[] randomKey(int length) {
        byte[] dummy = new byte[length];
        Random random = new Random();
        // method: Random.nextBytes
        // params: bytes: this is non-null byte array in which to put the random bytes
        random.nextBytes(dummy);
        return dummy;
    }

    /**
     * Listing 1.16 unfinished
     * @param original
     * @return
     */
    public static KeyPair encrypt(String original){
        byte[] originalBytes = original.getBytes();
        byte[] dummyKey = randomKey(originalBytes.length);
        byte[] encryptedKey = new byte[originalBytes.length];
        for (int i = 0; i < originalBytes.length; i++) {
            encryptedKey[i] = (byte) (originalBytes[i] ^ dummyKey[i]);
        }
        return new KeyPair(dummyKey, encryptedKey);
    }

    /**
     * Listing 1.17
     */
    public static String decrypt(KeyPair kp) {
        byte[] decrypted = new byte[kp.key1.length];
        for (int i = 0; i < kp.key1.length; i++) {
            decrypted[i] = (byte) (kp.key1[i] ^ kp.key2[i]);
        }
        return new String(decrypted);
    }

    public static void main(String[] args) {
        KeyPair kp = encrypt("One Time Pad");
        String result = decrypt(kp);
        System.out.println(result);
    }
}
