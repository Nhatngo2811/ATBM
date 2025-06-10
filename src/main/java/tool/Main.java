package tool;

import javax.swing.SwingUtilities;


public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SignatureModel model = new SignatureModel();
            SignatureView view = new SignatureView();
            SignatureController controller = new SignatureController(model, view);
            view.setVisible(true);
        });
    }
}
