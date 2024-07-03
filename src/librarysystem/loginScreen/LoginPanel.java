package librarysystem.loginScreen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import librarysystem.LoginWindow;

public class LoginPanel extends JPanel {

    public static final LoginPanel INSTANCE = new LoginPanel();

    private JSplitPane splitPane;

    private JPanel topImagePanel;
    private JPanel bottomLoginPanel;
    private JLabel icon;

    private LoginPanel() {
        super(new BorderLayout());
        initComponents();
    }

    private void setTopImagePanel() {
        topImagePanel = new JPanel(new BorderLayout());
        topImagePanel.setBackground(new Color(240, 248, 255)); // Alice Blue

        String currDirectory = System.getProperty("user.dir");
        String pathToImage = currDirectory + "/src/librarysystem/logo.jpg";
        ImageIcon originalImageIcon = new ImageIcon(pathToImage);

        icon = new JLabel(originalImageIcon);
        topImagePanel.add(icon, BorderLayout.CENTER);

        // Add a component listener to resize the image when the panel size changes
        topImagePanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                resizeImage(originalImageIcon);
            }
        });
    }

    private void resizeImage(ImageIcon originalImageIcon) {
        int width = topImagePanel.getWidth();
        int height = topImagePanel.getHeight();
        if (width > 0 && height > 0) {
            Image img = originalImageIcon.getImage();
            Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            ImageIcon scaledImageIcon = new ImageIcon(scaledImg);
            icon.setIcon(scaledImageIcon);
        }
    }

    private void setBottomLoginPanel() {
        bottomLoginPanel = new JPanel();
        bottomLoginPanel.setBackground(new Color(255, 250, 250)); // Snow
        bottomLoginPanel.setLayout(new BorderLayout()); // Added BorderLayout

        // Center the LoginWindow in the panel
        bottomLoginPanel.add(LoginWindow.INSTANCE, BorderLayout.CENTER);

        // Added padding
        bottomLoginPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    private void initComponents() {
        setTopImagePanel();
        setBottomLoginPanel();

        splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, topImagePanel, bottomLoginPanel);
        splitPane.setDividerLocation(300); // Divider location for the split
        splitPane.setDividerSize(12); // Size of the divider
        splitPane.setPreferredSize(new Dimension(850, 550)); // Preferred size for the split pane

        // Add the SplitPane to the Panel
        add(splitPane, BorderLayout.CENTER);
    }
}