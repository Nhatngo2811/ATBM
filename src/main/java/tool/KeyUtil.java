package tool;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;

public class KeyUtil {

    // Tạo cặp khóa RSA 2048 bit
    public static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
        gen.initialize(2048);
        return gen.generateKeyPair();
    }

    // Lưu PrivateKey ra file
    public static void savePrivateKey(PrivateKey privateKey, String filePath) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(privateKey.getEncoded());
        }
    }

    // Lưu PublicKey ra file
    public static void savePublicKey(PublicKey publicKey, String filePath) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(publicKey.getEncoded());
        }
    }

    // Đọc PrivateKey từ file (PKCS8)
    public static PrivateKey loadPrivateKey(String filePath) throws Exception {
        byte[] bytes = readAllBytes(filePath);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(new java.security.spec.PKCS8EncodedKeySpec(bytes));
    }

    // Đọc PublicKey từ file (X509)
    public static PublicKey loadPublicKey(String filePath) throws Exception {
        byte[] bytes = readAllBytes(filePath);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(new java.security.spec.X509EncodedKeySpec(bytes));
    }

    private static byte[] readAllBytes(String filePath) throws IOException {
        try (FileInputStream fis = new FileInputStream(filePath)) {
            return fis.readAllBytes();
        }
    }
}
