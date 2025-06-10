package tool;



import javax.swing.*;
import java.io.*;
import java.security.*;
import java.security.spec.*;

public class SignatureController {
    private SignatureModel model;
    private SignatureView view;

    public SignatureController(SignatureModel model, SignatureView view) {
        this.model = model;
        this.view = view;
        initializeListeners();
    }

    private void initializeListeners() {
        view.addGenerateKeyPairListener(e -> generateKeyPair());
        view.addSavePublicKeyListener(e -> savePublicKey());
        view.addSavePrivateKeyListener(e -> savePrivateKey());
        view.addLoadPublicKeyListener(e -> loadPublicKey());
        view.addLoadPrivateKeyListener(e -> loadPrivateKey());
        view.addEncryptListener(e -> encrypt());
        view.addDecryptListener(e -> decrypt());
    }

    private void generateKeyPair() {
        try {
            model.setKeySize(view.getSelectedKeySize());
            model.setPadding(view.getSelectedPadding());
            model.generateKeyPair();
            view.appendResult("Key pair generated successfully");
        } catch (Exception e) {
            view.appendResult("Error generating key pair: " + e.getMessage());
        }
    }

    private void savePublicKey() {
        if (model.getPublicKey() == null) {
            view.appendResult("No public key available to save");
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(view) == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fileChooser.getSelectedFile();
                try (FileOutputStream fos = new FileOutputStream(file)) {
                    fos.write(model.getPublicKey().getEncoded());
                }
                view.appendResult("Public key saved successfully");
            } catch (Exception e) {
                view.appendResult("Error saving public key: " + e.getMessage());
            }
        }
    }

    private void savePrivateKey() {
        if (model.getPrivateKey() == null) {
            view.appendResult("No private key available to save");
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(view) == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fileChooser.getSelectedFile();
                try (FileOutputStream fos = new FileOutputStream(file)) {
                    fos.write(model.getPrivateKey().getEncoded());
                }
                view.appendResult("Private key saved successfully");
            } catch (Exception e) {
                view.appendResult("Error saving private key: " + e.getMessage());
            }
        }
    }

    private void loadPublicKey() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(view) == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fileChooser.getSelectedFile();
                byte[] keyBytes = new byte[(int) file.length()];
                try (FileInputStream fis = new FileInputStream(file)) {
                    fis.read(keyBytes);
                }
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
                PublicKey publicKey = keyFactory.generatePublic(spec);
                model.setPublicKey(publicKey);
                view.appendResult("Public key loaded successfully");
            } catch (Exception e) {
                view.appendResult("Error loading public key: " + e.getMessage());
            }
        }
    }

    private void loadPrivateKey() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(view) == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fileChooser.getSelectedFile();
                byte[] keyBytes = new byte[(int) file.length()];
                try (FileInputStream fis = new FileInputStream(file)) {
                    fis.read(keyBytes);
                }
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
                PrivateKey privateKey = keyFactory.generatePrivate(spec);
                model.setPrivateKey(privateKey);
                view.appendResult("Private key loaded successfully");
            } catch (Exception e) {
                view.appendResult("Error loading private key: " + e.getMessage());
            }
        }
    }

    private void encrypt() {
        try {
            String text = view.getInputText();
            if (text.isEmpty()) {
                view.appendResult("Please enter text to encrypt");
                return;
            }
            String encrypted = model.encrypt(text);
            view.setResult(encrypted);
        } catch (Exception e) {
            view.appendResult(e.getMessage());
        }
    }

    private void decrypt() {
        try {
            String text = view.getInputText();
            if (text.isEmpty()) {
                view.appendResult("Please enter text to decrypt");
                return;
            }
            String decrypted = model.decrypt(text);
            view.setResult(decrypted);
        } catch (Exception e) {
            view.appendResult( e.getMessage());
        }
    }
}