package tool;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

public class DigitalSignatureApp extends JFrame {

    private JTextArea dataInputArea;
    private JTextArea signatureArea;
    private JButton generateKeysBtn, saveKeysBtn, loadPrivateKeyBtn, loadPublicKeyBtn, signBtn, verifyBtn;
    private JLabel statusLabel;

    private PrivateKey privateKey;
    private PublicKey publicKey;

    public DigitalSignatureApp() {
        setTitle("Digital Signature Tool");
        setSize(700, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        add(panel);

        dataInputArea = new JTextArea(7, 50);
        dataInputArea.setLineWrap(true);
        dataInputArea.setWrapStyleWord(true);

        signatureArea = new JTextArea(7, 50);
        signatureArea.setLineWrap(true);
        signatureArea.setWrapStyleWord(true);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createTitledBorder("Dữ liệu cần ký / xác minh"));
        topPanel.add(new JScrollPane(dataInputArea), BorderLayout.CENTER);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createTitledBorder("Chữ ký Base64"));
        centerPanel.add(new JScrollPane(signatureArea), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 4, 10, 10));

        generateKeysBtn = new JButton("Tạo cặp khóa mới");
        saveKeysBtn = new JButton("Lưu khóa ra file");
        loadPrivateKeyBtn = new JButton("Tải Private Key");
        loadPublicKeyBtn = new JButton("Tải Public Key");
        signBtn = new JButton("Ký dữ liệu");
        verifyBtn = new JButton("Xác minh chữ ký");

        buttonPanel.add(generateKeysBtn);
        buttonPanel.add(saveKeysBtn);
        buttonPanel.add(loadPrivateKeyBtn);
        buttonPanel.add(loadPublicKeyBtn);
        buttonPanel.add(signBtn);
        buttonPanel.add(verifyBtn);

        statusLabel = new JLabel("Chưa có khóa");
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // *** Sửa phần này để tránh trùng vị trí BorderLayout ***
        JPanel bottomPanel = new JPanel(new BorderLayout(5, 5));
        bottomPanel.add(buttonPanel, BorderLayout.CENTER);
        bottomPanel.add(statusLabel, BorderLayout.SOUTH);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(centerPanel, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        // Xử lý sự kiện
        generateKeysBtn.addActionListener(e -> generateKeys());
        saveKeysBtn.addActionListener(e -> saveKeys());
        loadPrivateKeyBtn.addActionListener(e -> loadPrivateKey());
        loadPublicKeyBtn.addActionListener(e -> loadPublicKey());
        signBtn.addActionListener(e -> signData());
        verifyBtn.addActionListener(e -> verifySignature());
    }


    private void generateKeys() {
        try {
            KeyPair kp = KeyUtil.generateKeyPair();
            privateKey = kp.getPrivate();
            publicKey = kp.getPublic();
            statusLabel.setText("Đã tạo cặp khóa mới");
            JOptionPane.showMessageDialog(this, "Tạo cặp khóa RSA 2048-bit thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            statusLabel.setText("Lỗi tạo khóa");
            JOptionPane.showMessageDialog(this, "Lỗi tạo khóa: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveKeys() {
        if (privateKey == null || publicKey == null) {
            JOptionPane.showMessageDialog(this, "Chưa có khóa để lưu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Chọn thư mục lưu khóa Private");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File dir = chooser.getSelectedFile();
                KeyUtil.savePrivateKey(privateKey, dir.getAbsolutePath() + File.separator + "private.key");
                KeyUtil.savePublicKey(publicKey, dir.getAbsolutePath() + File.separator + "public.key");
                statusLabel.setText("Lưu khóa thành công tại: " + dir.getAbsolutePath());
                JOptionPane.showMessageDialog(this, "Lưu khóa thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            statusLabel.setText("Lỗi lưu khóa");
            JOptionPane.showMessageDialog(this, "Lỗi lưu khóa: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadPrivateKey() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Chọn file Private Key (*.key)");
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                File file = chooser.getSelectedFile();
                privateKey = KeyUtil.loadPrivateKey(file.getAbsolutePath());
                statusLabel.setText("Đã tải khóa Private từ file: " + file.getName());
                JOptionPane.showMessageDialog(this, "Tải Private Key thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                statusLabel.setText("Lỗi tải Private Key");
                JOptionPane.showMessageDialog(this, "Lỗi tải Private Key: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void loadPublicKey() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Chọn file Public Key (*.key)");
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                File file = chooser.getSelectedFile();
                publicKey = KeyUtil.loadPublicKey(file.getAbsolutePath());
                statusLabel.setText("Đã tải khóa Public từ file: " + file.getName());
                JOptionPane.showMessageDialog(this, "Tải Public Key thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                statusLabel.setText("Lỗi tải Public Key");
                JOptionPane.showMessageDialog(this, "Lỗi tải Public Key: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void signData() {
        if (privateKey == null) {
            JOptionPane.showMessageDialog(this, "Chưa có Private Key!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            byte[] data = dataInputArea.getText().getBytes("UTF-8");
            String signature = SignatureUtil.signData(data, privateKey);
            signatureArea.setText(signature);
            statusLabel.setText("Ký dữ liệu thành công");
            JOptionPane.showMessageDialog(this, "Ký dữ liệu thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            statusLabel.setText("Lỗi ký dữ liệu");
            JOptionPane.showMessageDialog(this, "Lỗi ký dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void verifySignature() {
        if (publicKey == null) {
            JOptionPane.showMessageDialog(this, "Chưa có Public Key!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            byte[] data = dataInputArea.getText().getBytes("UTF-8");
            String signature = signatureArea.getText().trim();
            boolean valid = SignatureUtil.verifySignature(data, signature, publicKey);
            if (valid) {
                statusLabel.setText("Chữ ký hợp lệ");
                JOptionPane.showMessageDialog(this, "Chữ ký hợp lệ!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                statusLabel.setText("Chữ ký không hợp lệ");
                JOptionPane.showMessageDialog(this, "Chữ ký không hợp lệ!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            statusLabel.setText("Lỗi xác minh chữ ký");
            JOptionPane.showMessageDialog(this, "Lỗi xác minh chữ ký: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DigitalSignatureApp app = new DigitalSignatureApp();
            app.setVisible(true);
        });
    }
}

