package tool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SignatureView extends JFrame {
    private JComboBox<String> paddingCombo;
    private JComboBox<String> keySizeCombo;
    private JButton generateKeyPairButton;
    private JButton savePublicKeyButton;
    private JButton savePrivateKeyButton;
    private JButton loadPublicKeyButton;
    private JButton loadPrivateKeyButton;
    private JTextArea inputTextArea;
    private JButton encryptButton;
    private JButton decryptButton;
    private JTextArea resultArea;

    public SignatureView() {
        setTitle("RSA Encryption/Decryption Tool");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        initComponents();
        pack();
        setLocationRelativeTo(null);
        setSize(600, 800);
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // 1. Panel chọn padding và key size
        JPanel settingsPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        settingsPanel.setBorder(BorderFactory.createTitledBorder("Settings"));

        paddingCombo = new JComboBox<>(new String[]{"PKCS1Padding", "OAEPPadding"});
        keySizeCombo = new JComboBox<>(new String[]{"1024", "2048", "4096"});

        settingsPanel.add(new JLabel("Padding:"));
        settingsPanel.add(paddingCombo);
        settingsPanel.add(new JLabel("Key Size:"));
        settingsPanel.add(keySizeCombo);

        // 2. Panel tạo khóa
        JPanel generatePanel = new JPanel();
        generatePanel.setBorder(BorderFactory.createTitledBorder("Key Generation"));
        generateKeyPairButton = new JButton("Generate Key Pair");
        generatePanel.add(generateKeyPairButton);

        // 3. Panel lưu khóa
        JPanel savePanel = new JPanel(new FlowLayout());
        savePanel.setBorder(BorderFactory.createTitledBorder("Save Keys"));
        savePublicKeyButton = new JButton("Save Public Key");
        savePrivateKeyButton = new JButton("Save Private Key");
        savePanel.add(savePublicKeyButton);
        savePanel.add(savePrivateKeyButton);

        // 4. Panel upload khóa
        JPanel loadPanel = new JPanel(new FlowLayout());
        loadPanel.setBorder(BorderFactory.createTitledBorder("Load Keys"));
        loadPublicKeyButton = new JButton("Upload Public Key");
        loadPrivateKeyButton = new JButton("Upload Private Key");
        loadPanel.add(loadPublicKeyButton);
        loadPanel.add(loadPrivateKeyButton);

        // 5. Panel nhập text
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Input Text"));
        inputTextArea = new JTextArea(5, 40);
        inputPanel.add(new JScrollPane(inputTextArea), BorderLayout.CENTER);

        // 6. Panel mã hóa và giải mã
        JPanel actionPanel = new JPanel(new FlowLayout());
        actionPanel.setBorder(BorderFactory.createTitledBorder("Actions"));
        encryptButton = new JButton("Encrypt (Private Key)");
        decryptButton = new JButton("Decrypt (Public Key)");
        actionPanel.add(encryptButton);
        actionPanel.add(decryptButton);

        // 7. Panel kết quả
        JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.setBorder(BorderFactory.createTitledBorder("Result"));
        resultArea = new JTextArea(5, 40);
        resultArea.setEditable(false);
        resultPanel.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        // Thêm tất cả các panel vào main panel
        mainPanel.add(settingsPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(generatePanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(savePanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(loadPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(inputPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(actionPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(resultPanel);

        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        wrapperPanel.add(mainPanel, BorderLayout.CENTER);

        add(wrapperPanel);
    }

    // Getters
    public String getSelectedPadding() {
        return (String) paddingCombo.getSelectedItem();
    }

    public int getSelectedKeySize() {
        return Integer.parseInt((String) keySizeCombo.getSelectedItem());
    }

    public String getInputText() {
        return inputTextArea.getText();
    }

    public String getResult() {
        return resultArea.getText();
    }

    // Setters
    public void setResult(String text) {
        resultArea.setText(text);
    }

    public void appendResult(String text) {
        resultArea.append(text + "\n");
        resultArea.setCaretPosition(resultArea.getDocument().getLength());
    }

    // Listeners
    public void addGenerateKeyPairListener(ActionListener listener) {
        generateKeyPairButton.addActionListener(listener);
    }

    public void addSavePublicKeyListener(ActionListener listener) {
        savePublicKeyButton.addActionListener(listener);
    }

    public void addSavePrivateKeyListener(ActionListener listener) {
        savePrivateKeyButton.addActionListener(listener);
    }

    public void addLoadPublicKeyListener(ActionListener listener) {
        loadPublicKeyButton.addActionListener(listener);
    }

    public void addLoadPrivateKeyListener(ActionListener listener) {
        loadPrivateKeyButton.addActionListener(listener);
    }

    public void addEncryptListener(ActionListener listener) {
        encryptButton.addActionListener(listener);
    }

    public void addDecryptListener(ActionListener listener) {
        decryptButton.addActionListener(listener);
    }
}