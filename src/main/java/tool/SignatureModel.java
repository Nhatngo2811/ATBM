package tool;

import javax.crypto.Cipher;
import java.security.*;
import java.util.Base64;

public class SignatureModel {
    private PublicKey publicKey;
    private PrivateKey privateKey;
    private String selectedAlgorithm;
    private int keySize;
    private String padding;

    public SignatureModel() {
        selectedAlgorithm = "RSA";
        keySize = 2048;
        padding = "PKCS1Padding";
    }

    public void generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance(selectedAlgorithm);
        keyGen.initialize(keySize);
        KeyPair pair = keyGen.generateKeyPair();
        publicKey = pair.getPublic();
        privateKey = pair.getPrivate();
    }

    public String encrypt(String text) throws Exception {
        if (privateKey == null) {
            throw new IllegalStateException("Private key not available");
        }

        // Sửa lại transformation format
        String transformation = "RSA/ECB/" + padding;
        Cipher cipher = Cipher.getInstance(transformation);
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] encryptedBytes = cipher.doFinal(text.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public String decrypt(String encryptedText) throws Exception {
        if (publicKey == null) {
            throw new IllegalStateException("Public key not available");
        }

        // Sửa lại transformation format
        String transformation = "RSA/ECB/" + padding;
        Cipher cipher = Cipher.getInstance(transformation);
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        return new String(decryptedBytes);
    }

    // Getters and Setters giữ nguyên
    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public void setKeySize(int keySize) {
        this.keySize = keySize;
    }

    public void setPadding(String padding) {
        this.padding = padding;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }
}