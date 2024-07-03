package librarysystem.AppScreens;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.BorderFactory;

import business.SystemController;
import dataaccess.Auth;
import librarysystem.AddBookCopyWindow;
import librarysystem.AddBookWindow;
import librarysystem.AddNewLibraryMemberWindow;
import librarysystem.AllBookIdsWindow;
import librarysystem.AllMemberIdsWindow;
import librarysystem.BookCopiesWindow;
import librarysystem.CheckOutBookWindow;
import librarysystem.CheckoutRecordWindow;
import librarysystem.DashboardWindow;
import librarysystem.LoginWindow;
import librarysystem.Main;
import librarysystem.loginScreen.LoginPanel;

public class AppPanel extends JPanel {
    public static AppPanel INSTANCE = new AppPanel();
    // ITEN
    private JSplitPane splitPane;

    private JPanel leftLoginSide;
    private JPanel rightLoginSide;

    private JButton logoutBtn;

    private JList<String> sideBarMenuList;

	    private JPanel dashboard, addBookPanel, addMember, AllMemberIds, AllBook;
	    private DashboardWindow dashboardWindow;
	    private CheckOutBookWindow checkOutBookWindow;
	    private AddBookCopyWindow addBookCopy;
	    private CheckoutRecordWindow checkoutRecord;
	    private BookCopiesWindow bookCopiesWindow;
	    private String[] listMenu = { "Dashboard", "Add Member", "Add Book", "Add Book Copy", "Members", "Books", 
	            "CheckOut Book", "CheckOut Record", "Copies" };
	    private String[] listAdminMenu = { "Dashboard", "Add Member", "Add Book", "Add Book Copy", "Members", "Books",
	            "Copies" };
	    private String[] listLibrarianMenu = { "Dashboard", "CheckOut Book", "CheckOut Record" };

    private AppPanel() {
        super(new CardLayout());
        initComponents();
    }

    private String[] getRoleMenu() {
        if (SystemController.currentAuth == Auth.ADMIN) {
            return listAdminMenu;
        } else if (SystemController.currentAuth == Auth.LIBRARIAN) {
            return listLibrarianMenu;
        } else {
            return listMenu;
        }
    }

    public void setRoleMenu() {
        leftLoginSide.removeAll();
        paintMenu();
        goToDashBoard();
    }

    private void setLeftAppSidePanel() {
        leftLoginSide = new JPanel();
        leftLoginSide.setBackground(new Color(169, 169, 169)); // Changed to Dark Gray
        leftLoginSide.setLayout(new BorderLayout());
        paintMenu();
    }

    public void paintMenu() {
        sideBarMenuList = new JList<>(getRoleMenu());
        sideBarMenuList.setBackground(Color.WHITE);
        sideBarMenuList.setForeground(Color.BLACK);
        sideBarMenuList.setSelectedIndex(0);
        sideBarMenuList.setFixedCellHeight(50); // Changed cell height

        sideBarMenuList.setSelectionForeground(Color.WHITE);
        sideBarMenuList.setSelectionBackground(new Color(112, 128, 144)); // Changed to Slate Gray
        
        sideBarMenuList.addListSelectionListener(event -> {
            ((CardLayout) (rightLoginSide.getLayout())).show(rightLoginSide,
                    sideBarMenuList.getSelectedValue().toString());
        });

        DefaultListCellRenderer renderer = (DefaultListCellRenderer) sideBarMenuList.getCellRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);

        logoutBtn = new JButton("Log Out");
        logoutBtn.setBackground(new Color(169, 169, 169)); // Changed to Crimson
        logoutBtn.setForeground(Color.white);

        logoutBtn.addActionListener(e -> {
            Main.getInstance().navigateToLogin();
        });

        leftLoginSide.add(sideBarMenuList, BorderLayout.CENTER);
        leftLoginSide.add(logoutBtn, BorderLayout.SOUTH); // Changed to South position

    }

    private void setRightAppSidePanel() {
        rightLoginSide = new JPanel(new CardLayout());
        rightLoginSide.setBackground(new Color(211, 211, 211)); // Changed to Light Gray

        dashboard = DashboardWindow.INSTANCE;
        addBookPanel = AddBookWindow.INSTANCE;
        addMember = AddNewLibraryMemberWindow.INSTANCE;
        addBookCopy = AddBookCopyWindow.INSTANCE;
        AllMemberIds = AllMemberIdsWindow.INSTANCE;
        AllBook = AllBookIdsWindow.INSTANCE;
        checkOutBookWindow = CheckOutBookWindow.INSTANCE;
        checkoutRecord = CheckoutRecordWindow.INSTANCE;
        bookCopiesWindow = BookCopiesWindow.INSTANCE;

        rightLoginSide.add(listMenu[0], dashboard);
        rightLoginSide.add(listMenu[1], addMember);
        rightLoginSide.add(listMenu[2], addBookPanel);
        rightLoginSide.add(listMenu[3], addBookCopy);
        rightLoginSide.add(listMenu[4], AllMemberIds);
        rightLoginSide.add(listMenu[5], AllBook);
        rightLoginSide.add(listMenu[6], checkOutBookWindow);
        rightLoginSide.add(listMenu[7], checkoutRecord);
        rightLoginSide.add(listMenu[8], bookCopiesWindow);

        // Adding a border for better visibility
        rightLoginSide.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
    }

    public void goToDashBoard() {
        ((CardLayout) (rightLoginSide.getLayout())).show(rightLoginSide, listMenu[0]);
    }

    private void initComponents() {

        setRightAppSidePanel();
        setLeftAppSidePanel();

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftLoginSide, rightLoginSide);
        splitPane.setDividerLocation(200); // Changed divider location
        // Add the SplitPane to the Pane
        add(splitPane, BorderLayout.CENTER);

    }
}
