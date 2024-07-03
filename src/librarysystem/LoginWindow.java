package librarysystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JOptionPane;

import business.ControllerInterface;
import business.LoginException;
import business.SystemController;
import librarysystem.AppScreens.AppPanel;

public class LoginWindow extends JPanel implements LibWindow {
	public static final LoginWindow INSTANCE = new LoginWindow();
	private SystemController systemController = new SystemController();

	private boolean isInitialized = false;

	private JPanel mainPanel;
	private JPanel upperSection;
	private JPanel middleSection;
	private JPanel lowerSection;

	private JPanel topSection;
	private JPanel middleFieldsSection;
	private JPanel bottomSection;
	private JPanel leftFieldPanel;
	private JPanel rightFieldPanel;

	private JTextField usernameField;
	private JPasswordField passwordField;
	private JLabel fieldLabel;
	private JButton loginButton;
	private JButton backButton;

	private JTextField messageBar = new JTextField();

	@Override
	public void init() {

	}

	public boolean isInitialized() {
		return isInitialized;
	}

	@Override
	public void isInitialized(boolean val) {

	}

	public void setInitialized(boolean val) {
		isInitialized = val;
	}

	public void clearMessageBar() {
		messageBar.setText("");
	}

	/* Singleton pattern */
	private LoginWindow() {
		initialize();
	}

	public void initialize() {
		mainPanel = new JPanel();
		setupUpperSection();
		setupMiddleSection();
		setupLowerSection();

		BorderLayout layout = new BorderLayout();
		layout.setVgap(30);
		mainPanel.setLayout(layout);

		mainPanel.add(upperSection, BorderLayout.NORTH);
		mainPanel.add(middleSection, BorderLayout.CENTER);
		mainPanel.add(lowerSection, BorderLayout.SOUTH);
		add(mainPanel);

		setInitialized(true);
	}

	private void setupUpperSection() {
		upperSection = new JPanel();
		upperSection.setLayout(new BorderLayout());
		setupTopSection();
		setupMiddleFieldsSection();
		setupBottomSection();
		upperSection.add(topSection, BorderLayout.NORTH);
		upperSection.add(middleFieldsSection, BorderLayout.CENTER);
		upperSection.add(bottomSection, BorderLayout.SOUTH);
	}

	private void setupMiddleSection() {
		middleSection = new JPanel();
		middleSection.setLayout(new BorderLayout());
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.HORIZONTAL);
		middleSection.add(separator, BorderLayout.SOUTH);
	}

	private void setupLowerSection() {
		lowerSection = new JPanel();
		lowerSection.setLayout(new FlowLayout(FlowLayout.LEFT));
		lowerSection.setVisible(false);
		backButton = new JButton("<= Back to Main");
		lowerSection.add(backButton);
	}

	private void setupTopSection() {
		topSection = new JPanel();
		JPanel internalPanel = new JPanel(new BorderLayout());
		internalPanel.add(Box.createRigidArea(new Dimension(0, 20)), BorderLayout.NORTH);
		JLabel loginLabel = new JLabel("Login");
		Util.adjustLabelFont(loginLabel, Color.BLUE.darker(), true);
		internalPanel.add(loginLabel, BorderLayout.CENTER);
		topSection.setLayout(new FlowLayout(FlowLayout.LEFT));
		topSection.add(internalPanel);
	}

	private void setupMiddleFieldsSection() {
		middleFieldsSection = new JPanel();
		middleFieldsSection.setLayout(new FlowLayout(FlowLayout.LEFT));
		setupLeftFieldPanel();
		setupRightFieldPanel();
		middleFieldsSection.add(leftFieldPanel);
		middleFieldsSection.add(rightFieldPanel);
	}

	private void setupBottomSection() {
		bottomSection = new JPanel();
		loginButton = new JButton("Login");
		addLoginButtonListener(loginButton);
		bottomSection.add(loginButton);
	}

	private void setupLeftFieldPanel() {
		JPanel topTextPanel = new JPanel();
		JPanel bottomTextPanel = new JPanel();
		topTextPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
		bottomTextPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));

		usernameField = new JTextField(10);
		fieldLabel = new JLabel("Username");
		fieldLabel.setFont(Util.makeSmallFont(fieldLabel.getFont()));
		topTextPanel.add(usernameField);
		bottomTextPanel.add(fieldLabel);

		leftFieldPanel = new JPanel();
		leftFieldPanel.setLayout(new BorderLayout());
		leftFieldPanel.add(topTextPanel, BorderLayout.NORTH);
		leftFieldPanel.add(bottomTextPanel, BorderLayout.CENTER);
	}

	private void setupRightFieldPanel() {
		JPanel topTextPanel = new JPanel();
		JPanel bottomTextPanel = new JPanel();
		topTextPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
		bottomTextPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));

		passwordField = new JPasswordField(10);
		fieldLabel = new JLabel("Password");
		fieldLabel.setFont(Util.makeSmallFont(fieldLabel.getFont()));
		topTextPanel.add(passwordField);
		bottomTextPanel.add(fieldLabel);

		rightFieldPanel = new JPanel();
		rightFieldPanel.setLayout(new BorderLayout());
		rightFieldPanel.add(topTextPanel, BorderLayout.NORTH);
		rightFieldPanel.add(bottomTextPanel, BorderLayout.CENTER);
	}

	private void addLoginButtonListener(JButton button) {
		button.addActionListener(evt -> {
			try {
				systemController.login(usernameField.getText(), new String(passwordField.getPassword()));
				Main.getInstance().navigateToApp();
				AppPanel.INSTANCE.setRoleMenu();

			} catch (LoginException e) {
				JOptionPane.showMessageDialog(this, e.getMessage());
			}
			usernameField.setText("");
			passwordField.setText("");
		});
	}
}
