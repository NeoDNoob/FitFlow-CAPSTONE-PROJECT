/* Decompiler 4376ms, total 5390ms, lines 3815 */
package Main;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import Connectors.ConnectionProvider;

@SuppressWarnings("serial")
public class OwnerFitFlow extends JFrame {
   private JButton addressDistributionButton;
   private JButton ageDistributionButton;
   @SuppressWarnings("unused")
private String amount;
   private CardLayout cardLayout = new CardLayout();
   private JPanel cardPanel;
   private JLabel currentTrainees;
   private JPanel editTraineePanel;
   private JPanel gymReports;
   @SuppressWarnings("unused")
private int loggedInUserId;
   private JButton membershipButton;
   private DefaultTableModel model;
   private DefaultTableModel model1;
   private JTextField mtAgeField;
   private JTextField mtEmailField;
   private JTextField mtMnField;
   private JTextField mtNameField;
   private JTextField mtSearchField;
   private JTextField mtSexField;
   private JButton paymentTypeButton;
   @SuppressWarnings("unused")
private String receipt;
   private JTextArea receiptArea;
   private JTabbedPane tabbedPane;
   private JTextArea textArea;
   private JTable tlTable;
   private JLabel tpDateLabel;
   @SuppressWarnings("rawtypes")
private JComboBox tpDateSorter;
   private JTextField tpEmailField;
   private JTextField tpMnField;
   @SuppressWarnings("rawtypes")
private JComboBox tpMonthSorter;
   private JTextField tpNameField;
   private JTextField tpPaymentField;
   private JTextField tpSearchField;
   private JTable tpTable;
   @SuppressWarnings("rawtypes")
private JComboBox tpYearSorter;
   private JPanel traineePanel;
   @SuppressWarnings("unused")
private String username;
   @SuppressWarnings("unused")
private String userType;
   private JComboBox<String> yearComboBox;
   private JButton genderDistributionButton;

   @SuppressWarnings({ "unchecked", "rawtypes" })
public OwnerFitFlow(int selectedIndex, final String username, final String userType, final int loggedInUserId) {
      this.cardPanel = new JPanel(this.cardLayout);
      this.gymReports = new JPanel();
      this.username = username;
      this.userType = userType;
      this.loggedInUserId = loggedInUserId;
      this.setFont(new Font("Segoe UI", 0, 14));
      this.addWindowListener(new WindowAdapter() {
    	    @Override
			public void windowClosing(WindowEvent e) {
    	        String[] options = {"Exit", "Logout"};
    	        int choice = JOptionPane.showOptionDialog(
    	                null,
    	                "Do you want to Exit or Logout instead?",
    	                "Confirmation",
    	                JOptionPane.YES_NO_OPTION,
    	                JOptionPane.QUESTION_MESSAGE,
    	                null,
    	                options,
    	                null
    	        );

    	        if (choice == 0 || choice == 1) {
    	            try (Connection connection = ConnectionProvider.getCon()) {
    	                String query = "UPDATE audit_trail SET logouttime = NOW() WHERE user_id = ? AND logouttime IS NULL";

    	                try (PreparedStatement ps = connection.prepareStatement(query)) {
    	                    ps.setInt(1, loggedInUserId);
    	                    int rowsUpdated = ps.executeUpdate();

    	                    if (rowsUpdated > 0) {
    	                        if (choice == 0) {
    	                            JOptionPane.showMessageDialog(null, "Logout recorded. Exiting application.");
    	                            System.exit(0);  // Exit the application if "Exit" was selected
    	                        } else {
    	                            JOptionPane.showMessageDialog(null, "Logout successful!");
    	                            new LoginForm();
    	                            OwnerFitFlow.this.dispose();
    	                        }
    	                    } else {
    	                        JOptionPane.showMessageDialog(null, "Error logging out. No active session found.");
    	                    }
    	                } catch (SQLException ex) {
    	                    JOptionPane.showMessageDialog(null, "Error with logout operation: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
    	                }
    	            } catch (SQLException ex) {
    	                JOptionPane.showMessageDialog(null, "Database connection error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    	            }
    	        }
    	    }
    	});

      this.setDefaultCloseOperation(0);
      this.getContentPane().setForeground(new Color(255, 215, 0));
      this.setTitle("FitFlow Gym Management");
      this.setIconImage(Toolkit.getDefaultToolkit().getImage(OwnerFitFlow.class.getResource("/Resources/FitFlowIconPngResized.png")));
      this.getContentPane().setBackground(new Color(255, 223, 88));
      this.setSize(1200, 700);
      this.setResizable(false);
      this.setLocationRelativeTo((Component)null);
      new Font("Tahoma", 1, 25);
      this.tabbedPane = new JTabbedPane(2);
      this.tabbedPane.setBackground(new Color(255, 244, 179));
      this.tabbedPane.setForeground(Color.BLACK);
      this.tabbedPane.setBounds(7, 58, 1170, 590);
      this.tabbedPane.setBorder(new LineBorder(new Color(51, 51, 51), 1, true));
      this.tabbedPane.setFont(new Font("Segoe UI", 0, 25));
      this.tabbedPane.addChangeListener(new ChangeListener() {
         @Override
		public void stateChanged(ChangeEvent e) {
            JTabbedPane sourceTabbedPane = (JTabbedPane)e.getSource();
            int selectedIndex = sourceTabbedPane.getSelectedIndex();
            if (selectedIndex == 1) {
               OwnerFitFlow.this.updateTableData();
               OwnerFitFlow.this.model.fireTableStructureChanged();
               OwnerFitFlow.this.checkForUnpaidTrainees();
            } else if (selectedIndex == 2) {
               OwnerFitFlow.this.updateActiveMembersLabel();
            } else if (selectedIndex == 3) {
               OwnerFitFlow.this.setDateLabel();
            } else if (selectedIndex == 4) {
               OwnerFitFlow.this.loadDataTable();
            }

         }
      });
      this.getContentPane().setLayout((LayoutManager)null);
      this.getContentPane().add(this.tabbedPane);
      JPanel homePanel = new JPanel();
      homePanel.setBorder(UIManager.getBorder("PopupMenu.border"));
      homePanel.setBackground(new Color(0, 0, 0));
      homePanel.setLayout((LayoutManager)null);
      homePanel.setFont(new Font("Segoe UI", 1, 25));
      this.tabbedPane.addTab("Home ", new ImageIcon(OwnerFitFlow.class.getResource("/Resources/home-icon-silhouette.png")), homePanel, "Home");
      JLabel welcome = new JLabel("Welcome, " + username + "!");
      welcome.setForeground(new Color(255, 215, 0));
      welcome.setFont(new Font("Impact", 3, 62));
      welcome.setBackground(new Color(0, 0, 0));
      welcome.setBounds(210, 243, 600, 60);
      homePanel.add(welcome);
      this.traineePanel = new JPanel();
      this.traineePanel.setLayout((LayoutManager)null);
      this.traineePanel.setFont(new Font("Tahoma", 1, 25));
      this.traineePanel.setBackground(new Color(0, 0, 0));
      this.tabbedPane.addTab("Trainee List ", new ImageIcon(OwnerFitFlow.class.getResource("/Resources/group.png")), this.traineePanel, (String)null);
      JLabel traineeLabel = new JLabel("Trainee List");
      traineeLabel.setForeground(new Color(255, 215, 0));
      traineeLabel.setIcon(new ImageIcon(OwnerFitFlow.class.getResource("/Resources/users.png")));
      traineeLabel.setFont(new Font("Impact", 3, 47));
      traineeLabel.setBounds(557, 0, 325, 83);
      this.traineePanel.add(traineeLabel);
      JScrollPane tlScrollPane = new JScrollPane();
      tlScrollPane.setBackground(new Color(128, 128, 128));
      tlScrollPane.setBounds(50, 108, 815, 402);
      this.traineePanel.add(tlScrollPane);
      this.tlTable = new JTable();
      JTableHeader tlTableHead = this.tlTable.getTableHeader();
      tlTableHead.setBackground(new Color(255, 204, 51));
      tlTableHead.setFont(new Font("Segoe UI", 1, 12));
      this.tlTable.setGridColor(new Color(211, 211, 211));
      int rowHeight = 30;
      this.tlTable.setRowHeight(rowHeight);
      this.tlTable.setSelectionForeground(new Color(255, 255, 255));
      this.tlTable.setSelectionBackground(SystemColor.textHighlight);
      this.tlTable.setBackground(new Color(255, 255, 255));
      this.tlTable.setFont(new Font("Segoe UI", 0, 11));
      this.model = new DefaultTableModel();
      this.tlTable.setModel(this.model);
      this.checkForUnpaidTrainees();
      JLabel lblNewLabel;
      JComboBox mtPaymentTimeField;
      populateMemberTable();
      this.tlTable.setFillsViewportHeight(true);
      this.tlTable.setAutoscrolls(false);
      tlScrollPane.setViewportView(this.tlTable);
      this.currentTrainees = new JLabel("Current Active Trainees: ");
      this.currentTrainees.setFont(new Font("Segoe UI", 1, 15));
      this.currentTrainees.setForeground(new Color(255, 255, 0));
      this.currentTrainees.setBounds(50, 85, 218, 21);
      this.traineePanel.add(this.currentTrainees);
      this.updateActiveMembersLabel();
      this.editTraineePanel = new JPanel();
      this.editTraineePanel.setForeground(new Color(255, 215, 0));
      this.editTraineePanel.setLayout((LayoutManager)null);
      this.editTraineePanel.setBackground(new Color(0, 0, 0));
      this.tabbedPane.addTab("Manage Trainee ", new ImageIcon(OwnerFitFlow.class.getResource("/Resources/user-avatar.png")), this.editTraineePanel, (String)null);
      JLabel lblEditTrainee = new JLabel("Manage Trainee");
      lblEditTrainee.setForeground(new Color(255, 215, 0));
      lblEditTrainee.setBounds(440, 0, 422, 83);
      lblEditTrainee.setIcon(new ImageIcon(OwnerFitFlow.class.getResource("/Resources/pencil.png")));
      lblEditTrainee.setFont(new Font("Impact", 3, 47));
      this.editTraineePanel.add(lblEditTrainee);
      lblNewLabel = new JLabel("Trainee ID");
      lblNewLabel.setForeground(new Color(255, 215, 0));
      lblNewLabel.setBounds(64, 84, 120, 24);
      lblNewLabel.setFont(new Font("Segoe UI", 1, 19));
      this.editTraineePanel.add(lblNewLabel);
      this.mtSearchField = new JTextField();
      this.mtSearchField.setForeground(Color.WHITE);
      this.mtSearchField.setHorizontalAlignment(0);
      this.mtSearchField.setBackground(Color.DARK_GRAY);
      this.mtSearchField.setFont(new Font("Segoe UI", 1, 18));
      this.mtSearchField.setBounds(200, 83, 60, 30);
      this.editTraineePanel.add(this.mtSearchField);
      this.mtSearchField.setColumns(10);
      String[] status = new String[]{"Select", "Active", "Inactive"};
      final JComboBox<String> mtStatusField = new JComboBox(status);
      mtStatusField.setForeground(Color.WHITE);
      mtStatusField.setBackground(Color.DARK_GRAY);
      mtStatusField.setBorder(new LineBorder(new Color(51, 51, 51), 2, true));
      mtStatusField.setModel(new DefaultComboBoxModel(new String[]{"Select", "Active", "Inactive"}));
      mtStatusField.setFont(new Font("Segoe UI", 1, 18));
      mtStatusField.setBounds(476, 333, 115, 30);
      this.editTraineePanel.add(mtStatusField);
      String[] paymentTF = new String[]{"Please select", "By Session", "Monthly"};
      mtPaymentTimeField = new JComboBox(paymentTF);
      mtPaymentTimeField.setForeground(Color.WHITE);
      mtPaymentTimeField.setBackground(Color.DARK_GRAY);
      mtPaymentTimeField.setModel(new DefaultComboBoxModel(new String[]{"Please select", "By Session", "Monthly"}));
      mtPaymentTimeField.setFont(new Font("Segoe UI", 1, 18));
      mtPaymentTimeField.setBounds(673, 334, 160, 30);
      mtPaymentTimeField.setBorder(new LineBorder(new Color(51, 51, 51), 2, true));
      this.editTraineePanel.add(mtPaymentTimeField);
      JButton mtSearchButton = new JButton("Search");
      mtSearchButton.setBorder(new LineBorder(new Color(51, 51, 51), 2, true));
      mtSearchButton.setFocusable(false);
      mtSearchButton.setForeground(new Color(0, 0, 0));
      mtSearchButton.setBackground(new Color(255, 204, 51));
      mtSearchButton.addActionListener(new ActionListener() {
    	    @Override
    	    public void actionPerformed(ActionEvent e) {
    	        // Step 1: Retrieve input
    	        String id = OwnerFitFlow.this.mtSearchField.getText().trim();

    	        // Step 2: Input validation
    	        if (id.isEmpty()) {
    	            JOptionPane.showMessageDialog(
    	                    null,
    	                    "Please enter a valid Member ID.",
    	                    "Input Error",
    	                    JOptionPane.ERROR_MESSAGE
    	            );
    	            return;
    	        }

    	        boolean checkID = false;

    	        // Step 3: Database operation to search member
    	        String searchQuery = "SELECT * FROM member WHERE id = ?";
    	        try (Connection con = ConnectionProvider.getCon();
    	             PreparedStatement ps = con.prepareStatement(searchQuery)) {

    	            // Set the ID parameter in the query
    	            ps.setString(1, id);

    	            try (ResultSet rst = ps.executeQuery()) {
    	                // Process the result set
    	                while (rst.next()) {
    	                    checkID = true;
    	                    OwnerFitFlow.this.mtNameField.setText(rst.getString("name"));
    	                    OwnerFitFlow.this.mtAgeField.setText(rst.getString("age"));
    	                    OwnerFitFlow.this.mtSexField.setText(rst.getString("sex"));
    	                    OwnerFitFlow.this.mtSexField.setEditable(false);
    	                    OwnerFitFlow.this.mtMnField.setText(rst.getString("mobile_number"));
    	                    OwnerFitFlow.this.mtEmailField.setText(rst.getString("email"));
    	                    mtPaymentTimeField.setSelectedItem(rst.getString("payment_time"));
    	                    mtStatusField.setSelectedItem(rst.getString("status"));
    	                }

    	                // If no record is found
    	                if (!checkID) {
    	                    JOptionPane.showMessageDialog(
    	                            null,
    	                            "Member ID not found.",
    	                            "Search Error",
    	                            JOptionPane.ERROR_MESSAGE
    	                    );
    	                }
    	            }
    	        } catch (SQLException ex) {
    	            // Handle database errors
    	            JOptionPane.showMessageDialog(
    	                    null,
    	                    "Database error: " + ex.getMessage(),
    	                    "Error",
    	                    JOptionPane.ERROR_MESSAGE
    	            );
    	            ex.printStackTrace();
    	        }
    	    }
    	});

      mtSearchButton.setBounds(288, 84, 110, 30);
      mtSearchButton.setIcon(new ImageIcon(OwnerFitFlow.class.getResource("/Resources/magnifying-glass.png")));
      mtSearchButton.setFont(new Font("Segoe UI", 1, 18));
      this.editTraineePanel.add(mtSearchButton);
      JLabel lblNewLabel_1 = new JLabel("Name");
      lblNewLabel_1.setForeground(new Color(255, 215, 0));
      lblNewLabel_1.setBounds(64, 134, 84, 24);
      lblNewLabel_1.setFont(new Font("Segoe UI", 1, 19));
      this.editTraineePanel.add(lblNewLabel_1);
      this.mtNameField = new JTextField();
      this.mtNameField.setForeground(Color.WHITE);
      this.mtNameField.setBackground(Color.DARK_GRAY);
      this.mtNameField.setFont(new Font("Segoe UI", 1, 18));
      this.mtNameField.setBounds(64, 169, 357, 35);
      this.editTraineePanel.add(this.mtNameField);
      this.mtNameField.setColumns(10);
      JLabel lblNewLabel_2 = new JLabel("Age");
      lblNewLabel_2.setForeground(new Color(255, 215, 0));
      lblNewLabel_2.setBounds(64, 210, 50, 30);
      lblNewLabel_2.setFont(new Font("Segoe UI", 1, 19));
      this.editTraineePanel.add(lblNewLabel_2);
      this.mtAgeField = new JTextField();
      this.mtAgeField.setForeground(Color.WHITE);
      this.mtAgeField.setBackground(Color.DARK_GRAY);
      this.mtAgeField.setBounds(new Rectangle(0, 0, 213, 30));
      this.mtAgeField.setFont(new Font("Segoe UI", 1, 18));
      this.mtAgeField.setBounds(64, 251, 357, 35);
      this.editTraineePanel.add(this.mtAgeField);
      this.mtAgeField.setColumns(10);
      JLabel lblNewLabel_3 = new JLabel("Sex");
      lblNewLabel_3.setForeground(new Color(255, 215, 0));
      lblNewLabel_3.setBounds(64, 297, 46, 24);
      lblNewLabel_3.setFont(new Font("Segoe UI", 1, 19));
      this.editTraineePanel.add(lblNewLabel_3);
      this.mtSexField = new JTextField();
      this.mtSexField.setForeground(Color.WHITE);
      this.mtSexField.setBackground(Color.DARK_GRAY);
      this.mtSexField.setBounds(new Rectangle(0, 0, 213, 30));
      this.mtSexField.setFont(new Font("Segoe UI", 1, 18));
      this.mtSexField.setBounds(64, 330, 357, 35);
      this.editTraineePanel.add(this.mtSexField);
      this.mtSexField.setColumns(10);
      JLabel lblNewLabel_3_1 = new JLabel("Mobile Number");
      lblNewLabel_3_1.setForeground(new Color(255, 215, 0));
      lblNewLabel_3_1.setFont(new Font("Segoe UI", 1, 19));
      lblNewLabel_3_1.setBounds(476, 139, 150, 24);
      this.editTraineePanel.add(lblNewLabel_3_1);
      this.mtMnField = new JTextField();
      this.mtMnField.setForeground(Color.WHITE);
      this.mtMnField.setBackground(Color.DARK_GRAY);
      this.mtMnField.setBounds(new Rectangle(0, 0, 213, 30));
      this.mtMnField.setFont(new Font("Segoe UI", 1, 18));
      this.mtMnField.setColumns(10);
      this.mtMnField.setBounds(476, 169, 357, 35);
      this.editTraineePanel.add(this.mtMnField);
      JLabel lblNewLabel_3_1_1 = new JLabel("Email");
      lblNewLabel_3_1_1.setForeground(new Color(255, 215, 0));
      lblNewLabel_3_1_1.setFont(new Font("Segoe UI", 1, 19));
      lblNewLabel_3_1_1.setBounds(476, 218, 90, 24);
      this.editTraineePanel.add(lblNewLabel_3_1_1);
      this.mtEmailField = new JTextField();
      this.mtEmailField.setForeground(Color.WHITE);
      this.mtEmailField.setBackground(Color.DARK_GRAY);
      this.mtEmailField.setBounds(new Rectangle(0, 0, 213, 30));
      this.mtEmailField.setFont(new Font("Segoe UI", 1, 18));
      this.mtEmailField.setColumns(10);
      this.mtEmailField.setBounds(476, 251, 357, 35);
      this.editTraineePanel.add(this.mtEmailField);
      JLabel lblNewLabel_4 = new JLabel("Status");
      lblNewLabel_4.setForeground(new Color(255, 215, 0));
      lblNewLabel_4.setFont(new Font("Segoe UI", 1, 19));
      lblNewLabel_4.setBounds(474, 297, 66, 24);
      this.editTraineePanel.add(lblNewLabel_4);
      JButton mtUpdateButton = new JButton("Update");
      mtUpdateButton.setBorder(new LineBorder(new Color(51, 51, 51), 2, true));
      mtUpdateButton.setFocusable(false);
      mtUpdateButton.setForeground(new Color(0, 0, 0));
      mtUpdateButton.setBackground(new Color(255, 204, 51));
      mtUpdateButton.addActionListener(new ActionListener() {
    	    @Override
    	    public void actionPerformed(ActionEvent e) {
    	        // Step 1: Collect input fields
    	        String id = OwnerFitFlow.this.mtSearchField.getText().trim();
    	        String name = OwnerFitFlow.this.mtNameField.getText().trim();
    	        String age = OwnerFitFlow.this.mtAgeField.getText().trim();
    	        String mobileNumber = OwnerFitFlow.this.mtMnField.getText().trim();
    	        String email = OwnerFitFlow.this.mtEmailField.getText().trim();
    	        String status = (String) mtStatusField.getSelectedItem();
    	        String paymentTime = (String) mtPaymentTimeField.getSelectedItem();

    	        // Step 2: Input validation
    	        if (id.isEmpty()) {
    	            JOptionPane.showMessageDialog(
    	                    null,
    	                    "Please enter a valid ID.",
    	                    "Input Error",
    	                    JOptionPane.ERROR_MESSAGE
    	            );
    	            return;
    	        }

    	        // Step 3: Database update operation
    	        String updateQuery = "UPDATE member SET name=?, age=?, mobile_number=?, email=?, status=?, payment_time=? WHERE id=?";
    	        try (Connection con = ConnectionProvider.getCon();
    	             PreparedStatement ps = con.prepareStatement(updateQuery)) {

    	            // Set parameters for the query
    	            ps.setString(1, name);
    	            ps.setString(2, age);
    	            ps.setString(3, mobileNumber);
    	            ps.setString(4, email);
    	            ps.setString(5, status);
    	            ps.setString(6, paymentTime);
    	            ps.setString(7, id);

    	            // Execute update and check rows affected
    	            int rowsUpdated = ps.executeUpdate();
    	            if (rowsUpdated > 0) {
    	                JOptionPane.showMessageDialog(null, "Trainee Information Updated Successfully");

    	                // Clear input fields after successful update
    	                OwnerFitFlow.this.mtSearchField.setText("");
    	                OwnerFitFlow.this.mtSearchField.setEditable(true);
    	                OwnerFitFlow.this.mtNameField.setText("");
    	                OwnerFitFlow.this.mtAgeField.setText("");
    	                OwnerFitFlow.this.mtSexField.setText("");
    	                OwnerFitFlow.this.mtEmailField.setText("");
    	                OwnerFitFlow.this.mtMnField.setText("");
    	                mtPaymentTimeField.setSelectedItem("Payment Session");
    	                mtStatusField.setSelectedItem("Select");
    	            } else {
    	                JOptionPane.showMessageDialog(
    	                        null,
    	                        "No trainee found with the given ID.",
    	                        "Update Error",
    	                        JOptionPane.ERROR_MESSAGE
    	                );
    	            }
    	        } catch (SQLException ex) {
    	            // Handle database errors
    	            JOptionPane.showMessageDialog(
    	                    null,
    	                    "Database error: " + ex.getMessage(),
    	                    "Error",
    	                    JOptionPane.ERROR_MESSAGE
    	            );
    	            ex.printStackTrace();
    	        }
    	    }
    	});

      mtUpdateButton.setFont(new Font("Segoe UI", 1, 18));
      mtUpdateButton.setIcon(new ImageIcon(OwnerFitFlow.class.getResource("/Resources/changes.png")));
      mtUpdateButton.setBounds(64, 474, 115, 30);
      this.editTraineePanel.add(mtUpdateButton);
      JButton mtDeleteButton = new JButton("Delete");
      mtDeleteButton.setFocusable(false);
      mtDeleteButton.setBorder(new LineBorder(new Color(51, 51, 51), 2, true));
      mtDeleteButton.setForeground(new Color(0, 0, 0));
      mtDeleteButton.setBackground(new Color(255, 204, 51));
      mtDeleteButton.addActionListener(new ActionListener() {
    	    @Override
    	    public void actionPerformed(ActionEvent e) {
    	        // Step 1: Confirmation Input
    	        String confirmation = JOptionPane.showInputDialog(
    	                null,
    	                "Please type 'Confirm' to continue"
    	        );

    	        if (confirmation == null || !confirmation.equals("Confirm")) {
    	            JOptionPane.showMessageDialog(null, "Type 'Confirm' properly.");
    	            return;
    	        }

    	        // Step 2: Double confirmation
    	        int confirmDialog = JOptionPane.showConfirmDialog(
    	                null,
    	                "Do you really want to delete this trainee?",
    	                "Select",
    	                JOptionPane.YES_NO_OPTION
    	        );

    	        if (confirmDialog != JOptionPane.YES_OPTION) {
    	            return; // Exit if "No" is chosen
    	        }

    	        // Step 3: Get ID input
    	        String id = OwnerFitFlow.this.mtSearchField.getText().trim();
    	        if (id.isEmpty()) {
    	            JOptionPane.showMessageDialog(
    	                    null,
    	                    "Please enter a valid ID.",
    	                    "Input Error",
    	                    JOptionPane.ERROR_MESSAGE
    	            );
    	            return;
    	        }

    	        // Step 4: Delete the trainee record
    	        try (Connection con = ConnectionProvider.getCon();
    	             PreparedStatement pst = con.prepareStatement("DELETE FROM member WHERE id=?")) {

    	            pst.setString(1, id);
    	            int rowsAffected = pst.executeUpdate();

    	            if (rowsAffected > 0) {
    	                JOptionPane.showMessageDialog(null, "Trainee Successfully Deleted");

    	                // Clear input fields
    	                OwnerFitFlow.this.mtSearchField.setText("");
    	                OwnerFitFlow.this.mtSearchField.setEditable(true);
    	                OwnerFitFlow.this.mtNameField.setText("");
    	                OwnerFitFlow.this.mtAgeField.setText("");
    	                OwnerFitFlow.this.mtSexField.setText("");
    	                OwnerFitFlow.this.mtEmailField.setText("");
    	                OwnerFitFlow.this.mtMnField.setText("");
    	            } else {
    	                JOptionPane.showMessageDialog(
    	                        null,
    	                        "No trainee found with the given ID.",
    	                        "Delete Error",
    	                        JOptionPane.ERROR_MESSAGE
    	                );
    	            }
    	        } catch (SQLException ex) {
    	            JOptionPane.showMessageDialog(
    	                    null,
    	                    "Database error: " + ex.getMessage(),
    	                    "Error",
    	                    JOptionPane.ERROR_MESSAGE
    	            );
    	            ex.printStackTrace();
    	        }
    	    }
    	});

      mtDeleteButton.setFont(new Font("Segoe UI", 1, 18));
      mtDeleteButton.setIcon(new ImageIcon(OwnerFitFlow.class.getResource("/Resources/trash.png")));
      mtDeleteButton.setBounds(461, 474, 115, 30);
      this.editTraineePanel.add(mtDeleteButton);
      JButton mtResetButton = new JButton("Reset");
      mtResetButton.setBorder(new LineBorder(new Color(51, 51, 51), 2, true));
      mtResetButton.setFocusable(false);
      mtResetButton.setForeground(new Color(0, 0, 0));
      mtResetButton.setBackground(new Color(255, 204, 51));
      mtResetButton.addActionListener(new ActionListener() {
         @Override
		public void actionPerformed(ActionEvent e) {
            OwnerFitFlow.this.mtSearchField.setText("");
            OwnerFitFlow.this.mtSearchField.setEditable(true);
            OwnerFitFlow.this.mtNameField.setText("");
            OwnerFitFlow.this.mtAgeField.setText("");
            OwnerFitFlow.this.mtSexField.setText("");
            OwnerFitFlow.this.mtEmailField.setText("");
            OwnerFitFlow.this.mtMnField.setText("");
            OwnerFitFlow.this.mtEmailField.setText("");
            mtStatusField.setSelectedItem("Select");
            mtPaymentTimeField.setSelectedItem("Please Select");
         }
      });
      mtResetButton.setFont(new Font("Segoe UI", 1, 18));
      mtResetButton.setIcon(new ImageIcon(OwnerFitFlow.class.getResource("/Resources/undo.png")));
      mtResetButton.setBounds(256, 474, 120, 30);
      this.editTraineePanel.add(mtResetButton);
      JLabel paymentT = new JLabel("Payment Type");
      paymentT.setForeground(new Color(255, 215, 0));
      paymentT.setFont(new Font("Segoe UI", 1, 19));
      paymentT.setBounds(673, 297, 150, 24);
      this.editTraineePanel.add(paymentT);
      JPanel membershipPaymentPanel = new JPanel();
      membershipPaymentPanel.setLayout((LayoutManager)null);
      membershipPaymentPanel.setFont(new Font("Tahoma", 1, 20));
      membershipPaymentPanel.setBackground(new Color(0, 0, 0));
      this.tabbedPane.addTab("Trainee Payment ", new ImageIcon(OwnerFitFlow.class.getResource("/Resources/wallet-filled-money-tool.png")), membershipPaymentPanel, "Trainee Payment");
      this.tpSearchField = new JTextField();
      this.tpSearchField.setForeground(Color.WHITE);
      this.tpSearchField.setHorizontalAlignment(0);
      this.tpSearchField.setBounds(142, 84, 60, 30);
      this.tpSearchField.setBackground(Color.DARK_GRAY);
      this.tpSearchField.setFont(new Font("Segoe UI", 1, 18));
      this.tpSearchField.setColumns(10);
      membershipPaymentPanel.add(this.tpSearchField);
      this.tpDateLabel = new JLabel("");
      this.tpDateLabel.setForeground(new Color(255, 215, 0));
      this.tpDateLabel.setBounds(76, 128, 250, 24);
      this.tpDateLabel.setFont(new Font("Segoe UI", 1, 18));
      membershipPaymentPanel.add(this.tpDateLabel);
      JScrollPane tpScrollPane = new JScrollPane(this.tpTable);
      tpScrollPane.setBounds(362, 148, 460, 317);
      tpScrollPane.setBackground(new Color(128, 128, 128));
      membershipPaymentPanel.add(tpScrollPane);
      this.tpTable = new JTable();
      JTableHeader tpTableHead = this.tpTable.getTableHeader();
      tpTableHead.setBackground(new Color(255, 204, 51));
      tpTableHead.setFont(new Font("Segoe UI", 1, 12));
      this.tpTable.setGridColor(new Color(211, 211, 211));
      this.tpTable.setSelectionForeground(SystemColor.text);
      this.tpTable.setSelectionBackground(SystemColor.textHighlight);
      this.tpTable.getTableHeader().setBackground(new Color(255, 255, 102));
      this.tpTable.setBackground(new Color(255, 255, 255));
      this.tpTable.setFont(new Font("Segoe UI", 0, 11));
      this.tableDetails();
      tpScrollPane.setViewportView(this.tpTable);
      membershipPaymentPanel.setForeground(new Color(0, 0, 0));
      JLabel lblPayment = new JLabel("Payment");
      lblPayment.setBounds(574, 0, 300, 83);
      lblPayment.setForeground(new Color(255, 215, 0));
      lblPayment.setIcon(new ImageIcon(OwnerFitFlow.class.getResource("/Resources/wallet.png")));
      lblPayment.setFont(new Font("Impact", 3, 47));
      membershipPaymentPanel.add(lblPayment);
      JLabel lblmbID = new JLabel("Trainee ID");
      lblmbID.setForeground(new Color(255, 215, 0));
      lblmbID.setBounds(30, 87, 150, 24);
      lblmbID.setFont(new Font("Segoe UI", 1, 18));
      membershipPaymentPanel.add(lblmbID);
      String[] paymentTF1 = new String[]{"Please Select", "By Session", "Monthly"};
      final JComboBox<String> tpPaymentTimeField = new JComboBox(paymentTF1);
      tpPaymentTimeField.setForeground(Color.WHITE);
      tpPaymentTimeField.setBackground(Color.DARK_GRAY);
      tpPaymentTimeField.setModel(new DefaultComboBoxModel(new String[]{"Please Select", "By Session", "Monthly"}));
      tpPaymentTimeField.setBounds(200, 500, 155, 30);
      tpPaymentTimeField.setBorder(new LineBorder(new Color(51, 51, 51), 2, true));
      tpPaymentTimeField.setFont(new Font("Segoe UI", 1, 18));
      membershipPaymentPanel.add(tpPaymentTimeField);
      final JButton tpSaveButton = new JButton("Save");
      tpSaveButton.setFocusable(false);
      tpSaveButton.setBorder(new LineBorder(new Color(51, 51, 51), 2, true));
      tpSaveButton.setBackground(new Color(255, 204, 51));
      tpSaveButton.setForeground(new Color(0, 0, 0));
      tpSaveButton.setBounds(393, 501, 110, 30);
      tpSaveButton.addActionListener(new ActionListener() {
    	    @Override
    	    public void actionPerformed(ActionEvent e) {
    	        String memberName = OwnerFitFlow.this.tpNameField.getText();
    	        String paymentTime = (String) tpPaymentTimeField.getSelectedItem();

    	        if (OwnerFitFlow.this.isPaymentAllowed(memberName, paymentTime)) {
    	            String date = OwnerFitFlow.this.tpDateLabel.getText();
    	            String amount = OwnerFitFlow.this.tpPaymentField.getText();

    	            // Database operation: Save payment data
    	            try (Connection con = ConnectionProvider.getCon();
    	                 PreparedStatement ps = con.prepareStatement(
    	                     "INSERT INTO payments (date, payment_time, amount, member_name) VALUES (?,?,?,?)")) {

    	                ps.setString(1, date);
    	                ps.setString(2, paymentTime);
    	                ps.setString(3, amount);
    	                ps.setString(4, memberName);
    	                ps.executeUpdate();

    	                OwnerFitFlow.this.tableDetails();
    	                JOptionPane.showMessageDialog(null, "Payment Successful");
    	            } catch (SQLException ex) {
    	                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
    	                ex.printStackTrace();
    	            }

    	            // Generate receipt
    	            StringBuilder receipt = new StringBuilder();
    	            receipt.append("--------------------------------\n");
    	            receipt.append("     IDOL'S FITNESS GYM\n");
    	            receipt.append("    M. Leonor St, II-B, SPC\n");
    	            receipt.append("--------------------------------\n");
    	            receipt.append("    Date: ").append(date).append("\n");
    	            receipt.append("    Name: ").append(memberName).append("\n");
    	            receipt.append("    Amount to Pay: ₱").append(amount).append("\n");
    	            receipt.append("    Payment Session: ").append(paymentTime).append("\n");
    	            receipt.append("--------------------------------\n");
    	            receipt.append("     FitFlow System\n");
    	            receipt.append("       By Group 3\n");
    	            receipt.append("--------------------------------\n");

    	            // Display receipt in JTextArea within JOptionPane
    	            JTextArea receiptArea = new JTextArea(receipt.toString());
    	            receiptArea.setSize(265, 250);
    	            receiptArea.setEditable(false);

    	            JScrollPane scrollPane = new JScrollPane(receiptArea);
    	            scrollPane.setPreferredSize(new Dimension(265, 265));

    	            Object[] options = {"Print Receipt"};
    	            int result = JOptionPane.showOptionDialog(
    	                    null,
    	                    scrollPane,
    	                    "Receipt",
    	                    JOptionPane.DEFAULT_OPTION,
    	                    JOptionPane.INFORMATION_MESSAGE,
    	                    null,
    	                    options,
    	                    options[0]
    	            );

    	            if (result == 0) {
    	                OwnerFitFlow.this.printReceipt(receipt.toString());
    	            }

    	            // Clear input fields
    	            OwnerFitFlow.this.tpNameField.setText("");
    	            OwnerFitFlow.this.tpPaymentField.setText("");
    	            OwnerFitFlow.this.tpEmailField.setText("");
    	            OwnerFitFlow.this.tpMnField.setText("");
    	            tpPaymentTimeField.setSelectedItem("Please Select");
    	        }
    	    }
    	});

      tpSaveButton.setIcon(new ImageIcon(OwnerFitFlow.class.getResource("/Resources/diskette.png")));
      tpSaveButton.setFont(new Font("Segoe UI", 1, 18));
      membershipPaymentPanel.add(tpSaveButton);
      JButton tpSearchButton = new JButton("Search");
      tpSearchButton.setFocusable(false);
      tpSearchButton.setBorder(new LineBorder(new Color(51, 51, 51), 2, true));
      tpSearchButton.setBackground(new Color(255, 204, 51));
      tpSearchButton.setForeground(new Color(0, 0, 0));
      tpSearchButton.setBounds(233, 85, 110, 30);
      tpSearchButton.addActionListener(new ActionListener() {
    	    @Override
    	    public void actionPerformed(ActionEvent e) {
    	        OwnerFitFlow.this.setDateLabel();
    	        String id = OwnerFitFlow.this.tpSearchField.getText();
    	        String date = OwnerFitFlow.this.tpDateLabel.getText();

    	        boolean checkID = false;

    	        // Database operations
    	        try (Connection con = ConnectionProvider.getCon()) {
    	            // First Query: Check if member exists
    	            String memberQuery = "SELECT * FROM member WHERE id = ?";
    	            try (PreparedStatement pst = con.prepareStatement(memberQuery)) {
    	                pst.setString(1, id);
    	                try (ResultSet rst = pst.executeQuery()) {
    	                    if (rst.next()) {
    	                        checkID = true;
    	                        OwnerFitFlow.this.tpEmailField.setText(rst.getString("email"));
    	                        OwnerFitFlow.this.tpNameField.setText(rst.getString("name"));
    	                        OwnerFitFlow.this.tpMnField.setText(rst.getString("mobile_number"));
    	                        tpPaymentTimeField.setSelectedItem(rst.getString("payment_time"));
    	                    } else {
    	                        JOptionPane.showMessageDialog(
    	                                null,
    	                                "Member ID not found\n\nTry: Ask them to apply for a membership or manually input the trainee's information."
    	                        );
    	                    }
    	                }
    	            }

    	            // Second Query: Check if the trainee has already paid
    	            if (checkID) {
    	                String paymentQuery = "SELECT * FROM payments WHERE date = ? AND member_id = ?";
    	                try (PreparedStatement psl = con.prepareStatement(paymentQuery)) {
    	                    psl.setString(1, date);
    	                    psl.setString(2, id);
    	                    try (ResultSet rsl = psl.executeQuery()) {
    	                        if (rsl.next()) {
    	                            tpSaveButton.setVisible(false);
    	                            JOptionPane.showMessageDialog(null, "This trainee is already paid");
    	                        }
    	                    }
    	                }
    	            }
    	        } catch (SQLException ex) {
    	            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
    	            ex.printStackTrace();
    	        }
    	    }
    	});

      tpSearchButton.setIcon(new ImageIcon(OwnerFitFlow.class.getResource("/Resources/magnifying-glass.png")));
      tpSearchButton.setFont(new Font("Segoe UI", 1, 18));
      membershipPaymentPanel.add(tpSearchButton);
      JLabel lblDate = new JLabel("Date");
      lblDate.setForeground(new Color(255, 215, 0));
      lblDate.setBounds(30, 128, 60, 24);
      lblDate.setFont(new Font("Segoe UI", 1, 18));
      membershipPaymentPanel.add(lblDate);
      JLabel dateLabel = new JLabel("");
      dateLabel.setForeground(new Color(255, 215, 0));
      dateLabel.setBounds(65, 128, 150, 24);
      dateLabel.setFont(new Font("Segoe UI", 1, 18));
      membershipPaymentPanel.add(dateLabel);
      JLabel lblName = new JLabel("Name");
      lblName.setForeground(new Color(255, 215, 0));
      lblName.setBounds(30, 174, 60, 24);
      lblName.setFont(new Font("Segoe UI", 1, 18));
      membershipPaymentPanel.add(lblName);
      this.tpNameField = new JTextField();
      this.tpNameField.setForeground(Color.WHITE);
      this.tpNameField.setBounds(30, 211, 286, 35);
      this.tpNameField.setBackground(Color.DARK_GRAY);
      this.tpNameField.setFont(new Font("Segoe UI", 1, 18));
      this.tpNameField.setColumns(10);
      membershipPaymentPanel.add(this.tpNameField);
      JLabel lblMobileNumber = new JLabel("Mobile Number");
      lblMobileNumber.setForeground(new Color(255, 215, 0));
      lblMobileNumber.setBounds(30, 261, 150, 24);
      lblMobileNumber.setFont(new Font("Segoe UI", 1, 18));
      membershipPaymentPanel.add(lblMobileNumber);
      this.tpMnField = new JTextField();
      this.tpMnField.setForeground(Color.WHITE);
      this.tpMnField.setBounds(30, 296, 286, 35);
      this.tpMnField.setBackground(Color.DARK_GRAY);
      this.tpMnField.setFont(new Font("Segoe UI", 1, 18));
      this.tpMnField.setColumns(10);
      membershipPaymentPanel.add(this.tpMnField);
      JLabel lblEmail = new JLabel("Email");
      lblEmail.setForeground(new Color(255, 215, 0));
      lblEmail.setBounds(30, 353, 120, 24);
      lblEmail.setFont(new Font("Segoe UI", 1, 18));
      membershipPaymentPanel.add(lblEmail);
      this.tpEmailField = new JTextField();
      this.tpEmailField.setForeground(Color.WHITE);
      this.tpEmailField.setBounds(30, 384, 286, 35);
      this.tpEmailField.setBackground(Color.DARK_GRAY);
      this.tpEmailField.setFont(new Font("Segoe UI", 1, 18));
      this.tpEmailField.setColumns(10);
      membershipPaymentPanel.add(this.tpEmailField);
      JLabel lblAmountToPay = new JLabel("Amount to Pay");
      lblAmountToPay.setForeground(new Color(255, 215, 0));
      lblAmountToPay.setBounds(30, 425, 150, 24);
      lblAmountToPay.setFont(new Font("Segoe UI", 1, 18));
      membershipPaymentPanel.add(lblAmountToPay);
      this.tpPaymentField = new JTextField();
      this.tpPaymentField.setForeground(Color.WHITE);
      this.tpPaymentField.setHorizontalAlignment(0);
      this.tpPaymentField.setBounds(51, 460, 100, 35);
      this.tpPaymentField.setBackground(Color.DARK_GRAY);
      this.tpPaymentField.setFont(new Font("Segoe UI", 1, 18));
      this.tpPaymentField.setColumns(10);
      membershipPaymentPanel.add(this.tpPaymentField);
      JLabel pesoSign = new JLabel("₱");
      pesoSign.setForeground(new Color(255, 215, 0));
      pesoSign.setBounds(30, 463, 60, 24);
      pesoSign.setFont(new Font("Segoe UI", 1, 18));
      membershipPaymentPanel.add(pesoSign);
      JButton tpResetButton1 = new JButton("Reset");
      tpResetButton1.setFocusable(false);
      tpResetButton1.setBackground(new Color(255, 204, 51));
      tpResetButton1.setBorder(new LineBorder(new Color(51, 51, 51), 2, true));
      tpResetButton1.setForeground(new Color(0, 0, 0));
      tpResetButton1.setBounds(542, 501, 110, 30);
      tpResetButton1.addActionListener(new ActionListener() {
         @Override
		public void actionPerformed(ActionEvent e) {
            OwnerFitFlow.this.tpSearchField.setText("");
            OwnerFitFlow.this.tpNameField.setText("");
            OwnerFitFlow.this.tpMnField.setText("");
            OwnerFitFlow.this.tpEmailField.setText("");
            OwnerFitFlow.this.tpPaymentField.setText("");
            tpPaymentTimeField.setSelectedItem("Please Select");
            tpSaveButton.setVisible(true);
         }
      });
      tpResetButton1.setIcon(new ImageIcon(OwnerFitFlow.class.getResource("/Resources/undo.png")));
      tpResetButton1.setFont(new Font("Segoe UI", 1, 18));
      membershipPaymentPanel.add(tpResetButton1);
      JLabel paymentTFlbl = new JLabel("Payment Session");
      paymentTFlbl.setForeground(new Color(255, 215, 0));
      paymentTFlbl.setBounds(30, 504, 160, 24);
      paymentTFlbl.setFont(new Font("Segoe UI", 1, 18));
      membershipPaymentPanel.add(paymentTFlbl);
      JButton tpDeleteButton = new JButton("Delete");
      tpDeleteButton.setFocusable(false);
      tpDeleteButton.setBackground(new Color(255, 204, 51));
      tpDeleteButton.setBorder(new LineBorder(new Color(51, 51, 51), 2, true));
      tpDeleteButton.setForeground(new Color(0, 0, 0));
      tpDeleteButton.setBounds(695, 501, 110, 30);
      tpDeleteButton.addActionListener(new ActionListener() {
    	    @Override
			public void actionPerformed(ActionEvent e) {
    	        String confirm = JOptionPane.showInputDialog((Component) null, "Please type \"Confirm\" to continue");
    	        if (confirm != null && !confirm.isEmpty()) {
    	            if (confirm.equals("Confirm")) {
    	                int selectedRow = OwnerFitFlow.this.tpTable.getSelectedRow();
    	                if (selectedRow != -1) {
    	                    int confirmation = JOptionPane.showConfirmDialog((Component) null,
    	                            "Do you really want to delete this payment?", "Select", JOptionPane.YES_NO_OPTION);

    	                    if (confirmation == JOptionPane.YES_OPTION) {
    	                        String date = (String) OwnerFitFlow.this.tpTable.getValueAt(selectedRow, 1);
    	                        String name = (String) OwnerFitFlow.this.tpTable.getValueAt(selectedRow, 0);

    	                        String query = "DELETE FROM payments WHERE date = ? AND member_name = ?";

    	                        try (Connection con1 = ConnectionProvider.getCon();
    	                             PreparedStatement ps = con1.prepareStatement(query)) {

    	                            ps.setString(1, date);
    	                            ps.setString(2, name);

    	                            int rowsDeleted = ps.executeUpdate();
    	                            if (rowsDeleted > 0) {
    	                                DefaultTableModel dtm = (DefaultTableModel) OwnerFitFlow.this.tpTable.getModel();
    	                                dtm.removeRow(selectedRow);
    	                                JOptionPane.showMessageDialog((Component) null, "Payment deleted successfully");
    	                                System.out.println(rowsDeleted + " row(s) deleted");
    	                            } else {
    	                                JOptionPane.showMessageDialog((Component) null, "No matching record found to delete.");
    	                            }

    	                        } catch (SQLException ex) {
    	                            ex.printStackTrace();
    	                            JOptionPane.showMessageDialog((Component) null, "Error: " + ex.getMessage());
    	                        }
    	                    }
    	                } else {
    	                    JOptionPane.showMessageDialog((Component) null, "No row selected");
    	                }
    	            } else {
    	                JOptionPane.showMessageDialog((Component) null, "Incorrect confirmation input");
    	            }
    	        }
    	    }
    	});

      tpDeleteButton.setIcon(new ImageIcon(OwnerFitFlow.class.getResource("/Resources/trash.png")));
      tpDeleteButton.setFont(new Font("Segoe UI", 1, 18));
      membershipPaymentPanel.add(tpDeleteButton);
      this.tpDateSorter = new JComboBox();
      this.tpDateSorter.setForeground(Color.WHITE);
      this.tpDateSorter.setBackground(Color.DARK_GRAY);
      this.tpDateSorter.setBounds(362, 90, 100, 24);
      this.tpDateSorter.setBorder(new LineBorder(new Color(51, 51, 51), 2, true));
      membershipPaymentPanel.add(this.tpDateSorter);
      this.tpDateSorter.addItem("All");
      this.tpDateSorter.addItem("Today");
      this.tpDateSorter.addItem("This Week");
      this.tpDateSorter.addItem("Monthly");
      this.tpDateSorter.addItem("Yearly");
      this.tpMonthSorter = new JComboBox();
      this.tpMonthSorter.setForeground(Color.WHITE);
      this.tpMonthSorter.setBackground(Color.DARK_GRAY);
      this.tpMonthSorter.setVisible(false);
      this.tpMonthSorter.setBorder(new LineBorder(new Color(51, 51, 51), 2, true));
      this.tpMonthSorter.setBounds(499, 90, 100, 24);
      membershipPaymentPanel.add(this.tpMonthSorter);
      String[] months = new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
      String[] var52 = months;
      int var51 = months.length;

      for(int var50 = 0; var50 < var51; ++var50) {
         String month = var52[var50];
         this.tpMonthSorter.addItem(month);
      }

      this.tpYearSorter = new JComboBox();
      this.tpYearSorter.setForeground(Color.WHITE);
      this.tpYearSorter.setBackground(Color.DARK_GRAY);
      this.tpYearSorter.setVisible(false);
      this.tpYearSorter.setBorder(new LineBorder(new Color(51, 51, 51), 2, true));
      this.tpYearSorter.setBounds(637, 90, 100, 24);
      membershipPaymentPanel.add(this.tpYearSorter);

      for(int year = 2023; year <= 2030; ++year) {
         this.tpYearSorter.addItem(String.valueOf(year));
      }

      this.tpDateSorter.addActionListener(new ActionListener() {
         @Override
		public void actionPerformed(ActionEvent e) {
            String selectedOption = (String)OwnerFitFlow.this.tpDateSorter.getSelectedItem();
            switch(selectedOption.hashCode()) {
            case -1650694486:
               if (selectedOption.equals("Yearly")) {
                  OwnerFitFlow.this.tpMonthSorter.setVisible(false);
                  OwnerFitFlow.this.tpYearSorter.setVisible(true);
                  OwnerFitFlow.this.showYearlyPayments();
               }
               break;
            case -1393678355:
               if (selectedOption.equals("Monthly")) {
                  OwnerFitFlow.this.tpMonthSorter.setVisible(true);
                  OwnerFitFlow.this.tpYearSorter.setVisible(true);
                  OwnerFitFlow.this.showMonthlyPayments();
               }
               break;
            case 65921:
               if (selectedOption.equals("All")) {
                  OwnerFitFlow.this.tableDetails();
                  OwnerFitFlow.this.tpMonthSorter.setVisible(false);
                  OwnerFitFlow.this.tpYearSorter.setVisible(false);
               }
               break;
            case 80981793:
               if (selectedOption.equals("Today")) {
                  OwnerFitFlow.this.showTodayPayments();
                  OwnerFitFlow.this.tpMonthSorter.setVisible(false);
                  OwnerFitFlow.this.tpYearSorter.setVisible(false);
               }
               break;
            case 1384532022:
               if (selectedOption.equals("This Week")) {
                  OwnerFitFlow.this.showThisWeekPayments();
                  OwnerFitFlow.this.tpMonthSorter.setVisible(false);
                  OwnerFitFlow.this.tpYearSorter.setVisible(false);
               }
            }

         }
      });
      this.gymReports = new JPanel();
      this.gymReports.setBackground(new Color(0, 0, 0));
      this.tabbedPane.addTab("Gym Reports", new ImageIcon(OwnerFitFlow.class.getResource("/Resources/sales.png")), this.gymReports, (String)null);
      this.gymReports.setLayout((LayoutManager)null);
      JPanel dashboardPanel = new JPanel();
      dashboardPanel.setBackground(Color.DARK_GRAY);
      dashboardPanel.setBounds(10, 0, 169, 590);
      this.membershipButton = new JButton("Membership Report");
      this.membershipButton.setBackground(new Color(255, 204, 51));
      this.membershipButton.setBorder(new LineBorder(new Color(51, 51, 51), 2, true));
      this.membershipButton.setBounds(12, 81, 147, 23);
      this.paymentTypeButton = new JButton("Payment Type Report");
      this.paymentTypeButton.setBackground(new Color(255, 204, 51));
      this.paymentTypeButton.setBorder(new LineBorder(new Color(51, 51, 51), 2, true));
      this.paymentTypeButton.setBounds(12, 137, 147, 23);
      this.ageDistributionButton = new JButton("Age Distribution");
      this.ageDistributionButton.setBounds(12, 195, 147, 23);
      this.ageDistributionButton.setBorder(new LineBorder(new Color(51, 51, 51), 2, true));
      this.ageDistributionButton.setBackground(new Color(255, 204, 51));
      dashboardPanel.add(this.ageDistributionButton);
      this.addressDistributionButton = new JButton("Address Distribution");
      this.addressDistributionButton.setBackground(new Color(255, 204, 51));
      this.addressDistributionButton.setBorder(new LineBorder(new Color(51, 51, 51), 2, true));
      this.addressDistributionButton.setBounds(12, 250, 147, 23);
      dashboardPanel.add(this.addressDistributionButton);
      dashboardPanel.setLayout((LayoutManager)null);
      dashboardPanel.add(this.membershipButton);
      dashboardPanel.add(this.paymentTypeButton);
      this.gymReports.add(dashboardPanel);
      this.genderDistributionButton = new JButton("Gender Distribution");
      this.genderDistributionButton.setBorder(new LineBorder(new Color(51, 51, 51), 2, true));
      this.genderDistributionButton.setBackground(new Color(255, 204, 51));
      this.genderDistributionButton.setBounds(12, 300, 147, 23);
      dashboardPanel.add(this.genderDistributionButton);
      this.cardPanel.setBackground(Color.BLACK);
      this.cardPanel.setBounds(189, 63, 717, 414);
      this.gymReports.add(this.cardPanel);
      final JLabel yearLabel = new JLabel("Select Year:");
      yearLabel.setFont(new Font("Segoe UI", 1, 13));
      yearLabel.setForeground(new Color(255, 215, 0));
      this.yearComboBox = new JComboBox(this.getYearsFromDatabase());
      this.yearComboBox.setForeground(Color.WHITE);
      this.yearComboBox.setBackground(Color.DARK_GRAY);
      this.yearComboBox.setBorder(new LineBorder(new Color(51, 51, 51), 2, true));
      this.yearComboBox.setFont(new Font("Segoe UI", 1, 14));
      yearLabel.setBounds(189, 29, 100, 30);
      this.yearComboBox.setBounds(271, 29, 131, 30);
      this.gymReports.add(yearLabel);
      this.gymReports.add(this.yearComboBox);
      this.yearComboBox.addActionListener(new ActionListener() {
         @Override
		public void actionPerformed(ActionEvent e) {
            OwnerFitFlow.this.updateMembershipChart();
         }
      });
      this.membershipButton.addActionListener(new ActionListener() {
         @Override
		public void actionPerformed(ActionEvent e) {
            OwnerFitFlow.this.cardLayout.show(OwnerFitFlow.this.cardPanel, "Membership");
            OwnerFitFlow.this.updateMembershipChart();
            yearLabel.setVisible(true);
            OwnerFitFlow.this.yearComboBox.setVisible(true);
         }
      });
      this.paymentTypeButton.addActionListener(new ActionListener() {
         @Override
		public void actionPerformed(ActionEvent e) {
            OwnerFitFlow.this.cardLayout.show(OwnerFitFlow.this.cardPanel, "PaymentType");
            OwnerFitFlow.this.updatePaymentTypeChart();
         }
      });
      this.ageDistributionButton.addActionListener(new ActionListener() {
         @Override
		public void actionPerformed(ActionEvent e) {
            OwnerFitFlow.this.cardLayout.show(OwnerFitFlow.this.cardPanel, "AgeDistribution");
            OwnerFitFlow.this.updateAgeDistributionChart();
         }
      });
      this.addressDistributionButton.addActionListener(new ActionListener() {
         @Override
		public void actionPerformed(ActionEvent e) {
            OwnerFitFlow.this.cardLayout.show(OwnerFitFlow.this.cardPanel, "AddressDistribution");
            OwnerFitFlow.this.updateAddressDistributionChart();
         }
      });
      this.genderDistributionButton.addActionListener(new ActionListener() {
         @Override
		public void actionPerformed(ActionEvent e) {
            OwnerFitFlow.this.cardLayout.show(OwnerFitFlow.this.cardPanel, "GenderDistribution");
            OwnerFitFlow.this.updateGenderDistributionChart();
         }
      });
      this.cardPanel.add(this.createMembershipChartPanel(), "Membership");
      this.cardPanel.add(this.createPaymentTypeChartPanel(), "PaymentType");
      this.cardPanel.add(this.createAgeDistributionChartPanel(), "AgeDistribution");
      this.cardPanel.add(this.createAddressDistributionChartPanel(), "AddressDistribution");
      this.cardPanel.add(this.createGenderDistributionChartPanel(), "GenderDistribution");
      this.cardLayout.show(this.cardPanel, "Membership");
      JScrollPane scrollPane = new JScrollPane();
      scrollPane.setVerticalScrollBarPolicy(22);
      scrollPane.setHorizontalScrollBarPolicy(31);
      scrollPane.setBounds(189, 476, 717, 92);
      this.gymReports.add(scrollPane);
      this.textArea = new JTextArea();
      this.textArea.setWrapStyleWord(true);
      this.textArea.setLineWrap(true);
      this.textArea.setEditable(false);
      this.textArea.setFont(new Font("Segoe UI", 0, 13));
      scrollPane.setViewportView(this.textArea);
      JButton toPOSButton = new JButton("POS");
      toPOSButton.setBounds(1065, 7, 107, 45);
      toPOSButton.setFocusable(false);
      toPOSButton.addActionListener(new ActionListener() {
         @Override
		public void actionPerformed(ActionEvent e) {
            AdminPOS adminPOS = new AdminPOS(0, username, userType, loggedInUserId);
            adminPOS.setVisible(true);
            OwnerFitFlow.this.setVisible(false);
         }
      });
      toPOSButton.setBackground(new Color(224, 177, 25));
      toPOSButton.setIcon(new ImageIcon(OwnerFitFlow.class.getResource("/Resources/POS.png")));
      toPOSButton.setFont(new Font("Segoe UI", 1, 17));
      this.getContentPane().add(toPOSButton);
      JLabel userLabel = new JLabel("Logged in as: " + username + " (" + userType + ")");
      userLabel.setForeground(new Color(0, 0, 0));
      userLabel.setFont(new Font("Segoe UI", 1, 18));
      userLabel.setBounds(7, 35, 369, 23);
      this.getContentPane().add(userLabel);
      JLabel power = new JLabel("Powered by: \nGroup 3 - Lyceum de San Pablo");
      power.setFont(new Font("Franklin Gothic Medium", 3, 15));
      power.setBounds(747, 31, 317, 16);
      this.getContentPane().add(power);
   }

   private void checkForUnpaidTrainees() {
	    for (int i = 0; i < this.model.getRowCount(); ++i) {
	        String membershipFee = (String) this.model.getValueAt(i, 9);
	        if ("Unpaid".equalsIgnoreCase(membershipFee)) {
	            String name = (String) this.model.getValueAt(i, 2);
	            int response = JOptionPane.showConfirmDialog((Component) null, "Have " + name + " paid the membership fee?", "Unpaid Membership Fee", 0, 3);
	            if (response == 0) {
	                int traineeId = (Integer) this.model.getValueAt(i, 0);

	                try (Connection con = ConnectionProvider.getCon()) {
	                    String updateQuery = "UPDATE member SET MembershipFee = ? WHERE id = ?";
	                    try (PreparedStatement pstmt = con.prepareStatement(updateQuery)) {
	                        pstmt.setString(1, "Paid");
	                        pstmt.setInt(2, traineeId);
	                        pstmt.executeUpdate();
	                    }

	                    JOptionPane.showMessageDialog((Component) null, "Payment Status Updated Successfully", "Success", 1);
	                    this.model.setValueAt("Paid", i, 9);

	                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	                    StringBuilder membershipReceipt = new StringBuilder();
	                    membershipReceipt.append("--------------------------------\n")
	                            .append("        Idol's Fitness Gym\n")
	                            .append("     M. Leonor St, II-E, SPC\n")
	                            .append("--------------------------------\n")
	                            .append("    ").append(timestamp).append("\n")
	                            .append("--------------------------------\n")
	                            .append("This receipt is the proof that  ").append(name).append(" is a member at IDOL'S FITNESS GYM\n")
	                            .append("--------------------------------\n")
	                            .append("THIS WILL SERVE AS PROOF OF YOUR MEMBERSHIP\n")
	                            .append("--------------------------------\n")
	                            .append("           FitFlow System\n")
	                            .append("          Made by Group 3\n")
	                            .append("--------------------------------\n");

	                    this.receiptArea = new JTextArea(membershipReceipt.toString());
	                    this.receiptArea.setSize(265, 250);
	                    this.receiptArea.setEditable(false);
	                    JScrollPane scrollPane = new JScrollPane(this.receiptArea);
	                    scrollPane.setPreferredSize(new Dimension(265, 265));

	                    Object[] options = new Object[]{"Print Receipt"};
	                    int option = JOptionPane.showOptionDialog((Component) null, scrollPane, "Receipt", -1, -1, null, options, options[0]);
	                    if (option == 0) {
	                        this.printReceipt1(membershipReceipt.toString());
	                    }

	                } catch (SQLException ex) {
	                    JOptionPane.showMessageDialog((Component) null, ex.getMessage(), "Error", 0);
	                }

	            } else if (response == 1) {
	                int traineeId = (Integer) this.model.getValueAt(i, 0);
	                int deleteResponse = JOptionPane.showConfirmDialog((Component) null, "Do you want to delete the unpaid trainee with ID: " + traineeId + "?", "Delete Unpaid Trainee", 0, 2);

	                if (deleteResponse == 0) {
	                    try (Connection con = ConnectionProvider.getCon()) {
	                        String deleteQuery = "DELETE FROM member WHERE id = ?";
	                        try (PreparedStatement pstmt = con.prepareStatement(deleteQuery)) {
	                            pstmt.setInt(1, traineeId);
	                            pstmt.executeUpdate();
	                        }

	                        JOptionPane.showMessageDialog((Component) null, "Unpaid Trainee Successfully Deleted", "Success", 1);
	                        this.model.removeRow(i);
	                        --i;

	                    } catch (SQLException ex) {
	                        JOptionPane.showMessageDialog((Component) null, ex.getMessage());
	                    }
	                }
	            }
	            break;
	        }
	    }
	}


   private JPanel createAddressDistributionChartPanel() {
      DefaultPieDataset dataset = new DefaultPieDataset();
      JFreeChart chart = ChartFactory.createPieChart("Address Distribution", dataset, true, true, false);
      ChartPanel chartPanel = new ChartPanel(chart);
      chartPanel.setBackground(Color.BLACK);
      return chartPanel;
   }

   private JPanel createAgeDistributionChartPanel() {
      DefaultCategoryDataset dataset = new DefaultCategoryDataset();
      JFreeChart chart = ChartFactory.createBarChart("Age Distribution of Members", "Age Range", "Number of Members", dataset, PlotOrientation.VERTICAL, true, true, false);
      ChartPanel chartPanel = new ChartPanel(chart);
      chartPanel.setBackground(Color.BLACK);
      return chartPanel;
   }

   private JPanel createGenderDistributionChartPanel() {
      DefaultPieDataset dataset = new DefaultPieDataset();
      JFreeChart chart = ChartFactory.createPieChart("Gender Distribution", dataset, true, true, false);
      ChartPanel chartPanel = new ChartPanel(chart);
      chartPanel.setBackground(Color.BLACK);
      return chartPanel;
   }

   private JPanel createMembershipChartPanel() {
      DefaultCategoryDataset dataset = new DefaultCategoryDataset();
      JFreeChart chart = ChartFactory.createBarChart("Membership Payments by Month", "Month", "Number of Payments", dataset);
      ChartPanel chartPanel = new ChartPanel(chart);
      chartPanel.setBackground(Color.BLACK);
      return chartPanel;
   }

   private JPanel createPaymentTypeChartPanel() {
      DefaultPieDataset dataset = new DefaultPieDataset();
      JFreeChart chart = ChartFactory.createPieChart("Payment Type Distribution", dataset, true, true, false);
      ChartPanel chartPanel = new ChartPanel(chart);
      chartPanel.setBackground(Color.BLACK);
      return chartPanel;
   }

   private int getActiveMembersCount() {
	    int count = 0;
	    String status = "Active";
	    String query = "SELECT COUNT(*) AS count FROM member WHERE status = ?";

	    try (Connection con = ConnectionProvider.getCon();
	         PreparedStatement pst = con.prepareStatement(query)) {

	        pst.setString(1, status);

	        try (ResultSet rs = pst.executeQuery()) {
	            if (rs.next()) {
	                count = rs.getInt("count");
	            }
	        }

	    } catch (SQLException ex) {
	        ex.printStackTrace(); // Log the exception, replace this with a logging mechanism if needed
	    }

	    return count;
	}


   private String[] getYearsFromDatabase() {
	    ArrayList<String> yearsList = new ArrayList<>();
	    String query = "SELECT DISTINCT YEAR(join_date) AS year FROM member ORDER BY year ASC";

	    try (Connection con = ConnectionProvider.getCon();
	         Statement st = con.createStatement();
	         ResultSet rs = st.executeQuery(query)) {

	        while (rs.next()) {
	            yearsList.add(rs.getString("year"));
	        }

	    } catch (SQLException ex) {
	        ex.printStackTrace(); // Log the exception, you can replace this with a more sophisticated logging mechanism
	    }

	    // If the yearsList is empty, return a default set of years, otherwise return the years from the database
	    return yearsList.isEmpty()
	        ? new String[]{"2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030"}
	        : yearsList.toArray(new String[0]);
	}


   private boolean isPaymentAllowed(String memberName, String paymentTime) {
	    String sql;
	    try (Connection con = ConnectionProvider.getCon()) {
	        PreparedStatement ps;
	        ResultSet rs;

	        // Determine the SQL query based on paymentTime
	        if (paymentTime.equals("By Session")) {
	            sql = "SELECT * FROM payments WHERE member_name = ? AND payment_time = ? AND DATE(date) = CURDATE()";
	        } else if (paymentTime.equals("Monthly")) {
	            sql = "SELECT * FROM payments WHERE member_name = ? AND payment_time = ? AND YEAR(date) = YEAR(CURDATE()) AND MONTH(date) = MONTH(CURDATE())";
	        } else {
	            return true; // If paymentTime is not "By Session" or "Monthly", allow the payment.
	        }

	        ps = con.prepareStatement(sql);
	        ps.setString(1, memberName);
	        ps.setString(2, paymentTime);
	        rs = ps.executeQuery();

	        if (rs.next()) {
	            // Payment already made, show appropriate message
	            if (paymentTime.equals("By Session")) {
	                JOptionPane.showMessageDialog(null, "This trainee is already paid today");
	            } else if (paymentTime.equals("Monthly")) {
	                JOptionPane.showMessageDialog(null, "This trainee is already paid this month");
	            }
	            return false; // Payment not allowed
	        }

	        return true; // Payment allowed

	    } catch (Exception ex) {
	        // Log or show error message if something goes wrong
	        JOptionPane.showMessageDialog(null, ex.getMessage());
	        return true; // In case of error, assume payment is allowed (fallback)
	    }
	}


   public void loadDataTable() {
	    this.model1.setRowCount(0);

	    String sql = "SELECT * FROM admincred";

	    try (Connection con = ConnectionProvider.getCon();
	         Statement st = con.createStatement();
	         ResultSet rst = st.executeQuery(sql)) {

	        if (this.model1.getColumnCount() == 0) {
	            this.model1.addColumn("User ID");
	            this.model1.addColumn("Name");
	            this.model1.addColumn("Username");
	            this.model1.addColumn("User Type");
	        }

	        while (rst.next()) {
	            this.model1.addRow(new Object[]{
	                rst.getString(1),
	                rst.getString(2),
	                rst.getString(3),
	                rst.getString(4)
	            });
	        }

	    } catch (SQLException var30) {
	        JOptionPane.showMessageDialog(null, var30);
	    }
	}




   private void printReceipt(final String receiptText) {
      PrinterJob printerJob = PrinterJob.getPrinterJob();
      printerJob.setPrintable(new Printable() {
         @Override
		public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) {
            if (pageIndex > 0) {
               return 1;
            } else {
               Graphics2D g2d = (Graphics2D)graphics;
               g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
               Font font = new Font("Monospaced", 0, 10);
               g2d.setFont(font);
               String[] lines = receiptText.split("\n");
               int y = 10;
               String[] var11 = lines;
               int var10 = lines.length;

               for(int var9 = 0; var9 < var10; ++var9) {
                  String line = var11[var9];
                  g2d.drawString(line, 0, y);
                  y += g2d.getFontMetrics().getHeight();
               }

               return 0;
            }
         }
      });
      boolean doPrint = printerJob.printDialog();
      if (doPrint) {
         try {
            printerJob.print();
         } catch (PrinterException var5) {
            JOptionPane.showMessageDialog((Component)null, "Error printing receipt: " + var5.getMessage(), "Print Error", 0);
         }
      }

   }

   private void printReceipt1(final String receiptText) {
      PrinterJob printerJob = PrinterJob.getPrinterJob();
      printerJob.setPrintable(new Printable() {
         @Override
		public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) {
            if (pageIndex > 0) {
               return 1;
            } else {
               Graphics2D g2d = (Graphics2D)graphics;
               g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
               Font font = new Font("Monospaced", 0, 10);
               g2d.setFont(font);
               String[] lines = receiptText.split("\n");
               int y = 10;
               String[] var11 = lines;
               int var10 = lines.length;

               for(int var9 = 0; var9 < var10; ++var9) {
                  String line = var11[var9];
                  g2d.drawString(line, 0, y);
                  y += g2d.getFontMetrics().getHeight();
               }

               return 0;
            }
         }
      });
      boolean doPrint = printerJob.printDialog();
      if (doPrint) {
         try {
            printerJob.print();
         } catch (PrinterException var5) {
            JOptionPane.showMessageDialog((Component)null, "Error printing receipt: " + var5.getMessage(), "Print Error", 0);
         }
      }

   }

   public void setDateLabel() {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      this.tpDateLabel.setText(sdf.format(new Date()));
   }

   public void showMonthlyPayments() {
	    this.tpMonthSorter.addActionListener(new ActionListener() {
	        @Override
			public void actionPerformed(ActionEvent e) {
	            DefaultTableModel dtm = (DefaultTableModel) OwnerFitFlow.this.tpTable.getModel();
	            dtm.setRowCount(0);
	            int monthIndex = OwnerFitFlow.this.tpMonthSorter.getSelectedIndex() + 1;
	            String selectedYear = (String) OwnerFitFlow.this.tpYearSorter.getSelectedItem();

	            String sql = "SELECT * FROM payments WHERE MONTH(CONVERT_TZ(date, '+00:00', '+08:00')) = ? AND YEAR(CONVERT_TZ(date, '+00:00', '+08:00')) = ?";

	            try (Connection con = ConnectionProvider.getCon();
	                 PreparedStatement pst = con.prepareStatement(sql)) {

	                pst.setInt(1, monthIndex);
	                pst.setInt(2, Integer.parseInt(selectedYear));

	                try (ResultSet rst = pst.executeQuery()) {
	                    while (rst.next()) {
	                        dtm.addRow(new Object[]{rst.getString(3), rst.getString(2), rst.getString(4), rst.getString(5)});
	                    }
	                }

	            } catch (SQLException var47) {
	                JOptionPane.showMessageDialog(null, var47);
	            }
	        }
	    });
	}


   public void showThisWeekPayments() {
	    DefaultTableModel dtm = (DefaultTableModel) this.tpTable.getModel();
	    dtm.setRowCount(0);

	    String sql = "SELECT * FROM payments WHERE YEARWEEK(CONVERT_TZ(date, '+00:00', '+08:00'), 1) = YEARWEEK(CURDATE(), 1)";

	    try (Connection con = ConnectionProvider.getCon();
	         Statement st = con.createStatement();
	         ResultSet rst = st.executeQuery(sql)) {

	        while (rst.next()) {
	            dtm.addRow(new Object[]{rst.getString(3), rst.getString(2), rst.getString(4), rst.getString(5)});
	        }

	    } catch (SQLException var31) {
	        JOptionPane.showMessageDialog(null, var31);
	    }
	}


   public void showTodayPayments() {
	    DefaultTableModel dtm = (DefaultTableModel) this.tpTable.getModel();
	    dtm.setRowCount(0);

	    String sql = "SELECT * FROM payments WHERE DATE(date) = CURDATE()";

	    try (Connection con = ConnectionProvider.getCon();
	         Statement st = con.createStatement();
	         ResultSet rst = st.executeQuery(sql)) {

	        while (rst.next()) {
	            dtm.addRow(new Object[]{rst.getString(3), rst.getString(2), rst.getString(4), rst.getString(5)});
	        }

	    } catch (SQLException var31) {
	        JOptionPane.showMessageDialog(null, var31);
	    }
	}


   public void showYearlyPayments() {
	    this.tpYearSorter.addActionListener(new ActionListener() {
	        @Override
			public void actionPerformed(ActionEvent e) {
	            DefaultTableModel dtm = (DefaultTableModel) OwnerFitFlow.this.tpTable.getModel();
	            dtm.setRowCount(0);
	            String selectedYear = (String) OwnerFitFlow.this.tpYearSorter.getSelectedItem();

	            String sql = "SELECT * FROM payments WHERE YEAR(CONVERT_TZ(date, '+00:00', '+08:00')) = ?";

	            try (Connection con = ConnectionProvider.getCon();
	                 PreparedStatement pst = con.prepareStatement(sql)) {

	                pst.setInt(1, Integer.parseInt(selectedYear));

	                try (ResultSet rst = pst.executeQuery()) {
	                    while (rst.next()) {
	                        dtm.addRow(new Object[]{rst.getString(3), rst.getString(2), rst.getString(4), rst.getString(5)});
	                    }
	                }

	            } catch (SQLException var46) {
	                JOptionPane.showMessageDialog(null, var46);
	            }
	        }
	    });
	}


   public void tableDetails() {
	    DefaultTableModel dtm = (DefaultTableModel) this.tpTable.getModel();
	    dtm.setRowCount(0);

	    String sql = "SELECT * FROM payments";

	    try (Connection con = ConnectionProvider.getCon();
	         Statement st = con.createStatement();
	         ResultSet rst = st.executeQuery(sql)) {

	        if (dtm.getColumnCount() == 0) {
	            dtm.addColumn("Name");
	            dtm.addColumn("Date");
	            dtm.addColumn("Amount");
	            dtm.addColumn("Payment Session");
	        }

	        while (rst.next()) {
	            dtm.addRow(new Object[]{rst.getString(3), rst.getString(2), rst.getString(4), rst.getString(5)});
	        }

	    } catch (SQLException var31) {
	        JOptionPane.showMessageDialog(null, var31);
	    }
	}


   private void updateActiveMembersLabel() {
      int activeMembersCount = this.getActiveMembersCount();
      this.currentTrainees.setText("Current Active Trainees: " + activeMembersCount);
   }

   private void updateAddressDistributionChart() {
	    DefaultPieDataset dataset = new DefaultPieDataset();
	    LinkedHashMap<String, Integer> addressData = new LinkedHashMap<>();

	    ChartPanel chartPanel;
	    String sql = "SELECT address, COUNT(*) AS count FROM member GROUP BY address";

	    try (Connection con = ConnectionProvider.getCon();
	         PreparedStatement pst = con.prepareStatement(sql)) {

	        try (ResultSet rs = pst.executeQuery()) {
	            while (rs.next()) {
	                String address = rs.getString("address");
	                int count = rs.getInt("count");
	                dataset.setValue(address, count);
	                addressData.put(address, count);
	            }
	        }

	    } catch (SQLException var47) {
	        var47.printStackTrace();
	    }

	    JFreeChart chart = ChartFactory.createPieChart("Address Distribution", dataset, true, true, false);
	    chartPanel = new ChartPanel(chart);
	    this.cardPanel.add(chartPanel, "AddressDistribution");
	    this.cardLayout.show(this.cardPanel, "AddressDistribution");
	    this.writeTextualAnalysisForAddressDistribution(addressData);
	}


   private void updateAgeDistributionChart() {
	    String selectedYear = this.yearComboBox.getSelectedItem().toString();
	    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	    LinkedHashMap<String, Integer> ageData = new LinkedHashMap<>();

	    ChartPanel chartPanel;
	    String sql = "SELECT CASE " +
	                 "WHEN age BETWEEN 18 AND 25 THEN '18-25' " +
	                 "WHEN age BETWEEN 26 AND 35 THEN '26-35' " +
	                 "WHEN age BETWEEN 36 AND 45 THEN '36-45' " +
	                 "WHEN age BETWEEN 46 AND 60 THEN '46-60' " +
	                 "ELSE '60+' END AS age_range, COUNT(*) AS count " +
	                 "FROM member WHERE YEAR(join_date) = ? GROUP BY age_range";

	    try (Connection con = ConnectionProvider.getCon();
	         PreparedStatement pst = con.prepareStatement(sql)) {

	        pst.setString(1, selectedYear);

	        try (ResultSet rs = pst.executeQuery()) {
	            while (rs.next()) {
	                String ageRange = rs.getString("age_range");
	                int count = rs.getInt("count");
	                dataset.setValue(count, "Members", ageRange);
	                ageData.put(ageRange, count);
	            }
	        }

	    } catch (SQLException var48) {
	        var48.printStackTrace();
	    }

	    JFreeChart chart = ChartFactory.createBarChart(
	            "Age Distribution of Members in " + selectedYear,
	            "Age Range",
	            "Number of Members",
	            dataset,
	            PlotOrientation.VERTICAL,
	            true,
	            true,
	            false
	    );

	    chartPanel = new ChartPanel(chart);
	    this.cardPanel.add(chartPanel, "AgeDistribution");
	    this.cardLayout.show(this.cardPanel, "AgeDistribution");
	    this.writeTextualAnalysisForAgeDistribution(ageData, selectedYear);
	}


   private void updateGenderDistributionChart() {
	    DefaultPieDataset dataset = new DefaultPieDataset();
	    LinkedHashMap<String, Integer> genderData = new LinkedHashMap<>();

	    ChartPanel chartPanel;
	    String sql = "SELECT sex, COUNT(*) AS count FROM member GROUP BY sex";

	    try (Connection con = ConnectionProvider.getCon();
	         PreparedStatement pst = con.prepareStatement(sql)) {

	        try (ResultSet rs = pst.executeQuery()) {
	            while (rs.next()) {
	                String gender = rs.getString("sex");
	                int count = rs.getInt("count");
	                dataset.setValue(gender, count);
	                genderData.put(gender, count);
	            }
	        }

	    } catch (SQLException var47) {
	        var47.printStackTrace();
	    }

	    JFreeChart chart = ChartFactory.createPieChart("Gender Distribution", dataset, true, true, false);
	    chartPanel = new ChartPanel(chart);
	    this.cardPanel.add(chartPanel, "AddressDistribution");
	    this.cardLayout.show(this.cardPanel, "AddressDistribution");
	    this.writeTextualAnalysisForGenderDistribution(genderData);
	}


   private void updateMembershipChart() {
	    String selectedYear = this.yearComboBox.getSelectedItem().toString();
	    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	    int totalMemberships = 0;
	    LinkedHashMap<String, Integer> monthData = new LinkedHashMap<>();

	    ChartPanel chartPanel;
	    String sql = "SELECT MONTHNAME(join_date) AS month, COUNT(*) AS count FROM member WHERE YEAR(join_date) = ? GROUP BY month ORDER BY MONTH(join_date)";

	    try (Connection con = ConnectionProvider.getCon();
	         PreparedStatement pst = con.prepareStatement(sql)) {

	        pst.setString(1, selectedYear);
	        try (ResultSet rs = pst.executeQuery()) {
	            while (rs.next()) {
	                String month = rs.getString("month");
	                int count = rs.getInt("count");
	                dataset.addValue(count, "Memberships", month);
	                totalMemberships += count;
	                monthData.put(month, count);
	            }
	        }

	    } catch (SQLException var49) {
	        var49.printStackTrace();
	    }

	    JFreeChart chart = ChartFactory.createBarChart("Memberships in " + selectedYear, "Month", "Number of Memberships", dataset);
	    chartPanel = new ChartPanel(chart);
	    this.cardPanel.add(chartPanel, "Membership");
	    this.cardLayout.show(this.cardPanel, "Membership");
	    this.writeTextualAnalysisForMembership(monthData, selectedYear, totalMemberships);
	}


   private void updatePaymentTypeChart() {
	    String selectedYear = this.yearComboBox.getSelectedItem().toString();
	    DefaultPieDataset dataset = new DefaultPieDataset();
	    LinkedHashMap<String, Integer> paymentData = new LinkedHashMap<>();

	    ChartPanel chartPanel;

	    String sql = "SELECT payment_time, COUNT(*) AS count FROM member WHERE YEAR(join_date) = ? GROUP BY payment_time";

	    try (Connection con = ConnectionProvider.getCon();
	         PreparedStatement pst = con.prepareStatement(sql)) {

	        pst.setString(1, selectedYear);
	        try (ResultSet rs = pst.executeQuery()) {
	            while (rs.next()) {
	                String paymentType = rs.getString("payment_time");
	                int count = rs.getInt("count");
	                dataset.setValue(paymentType, count);
	                paymentData.put(paymentType, count);
	            }
	        }

	    } catch (SQLException var48) {
	        var48.printStackTrace();
	    }

	    JFreeChart chart = ChartFactory.createPieChart("Payment Type Distribution in " + selectedYear, dataset, true, true, false);
	    chartPanel = new ChartPanel(chart);
	    this.cardPanel.add(chartPanel, "PaymentType");
	    this.cardLayout.show(this.cardPanel, "PaymentType");
	    this.writeTextualAnalysisForPaymentType(paymentData, selectedYear);
	}


   private void updateTableData() {
	    this.model.setRowCount(0);

	    try (Connection con = ConnectionProvider.getCon();
	         PreparedStatement pstmt = con.prepareStatement("SELECT * FROM member");
	         ResultSet rst = pstmt.executeQuery()) {

	        ResultSetMetaData metaData = rst.getMetaData();
	        int columnCount = metaData.getColumnCount();
	        this.model.setColumnIdentifiers(new String[]{"Trainee ID", "Email", "Name", "Age", "Sex", "Address", "Mobile Number", "Payment Session", "Status", "Membership Fee", "Join Date"});

	        while (rst.next()) {
	            Object[] row = new Object[columnCount];
	            for (int i = 1; i <= columnCount; i++) {
	                row[i - 1] = rst.getObject(i);
	            }
	            this.model.addRow(row);
	        }

	    } catch (SQLException var34) {
	        JOptionPane.showMessageDialog(null, var34.getMessage());
	    }
	}


   private void writeTextualAnalysisForAddressDistribution(Map<String, Integer> addressData) {
      StringBuilder analysis = new StringBuilder();
      analysis.append("The analysis of address distribution among members shows the following breakdown: ");
      int totalMembers = 0;
      String mostCommonAddress = null;
      int mostCommonCount = 0;
      for (Entry<String, Integer> entry : addressData.entrySet()) {
         totalMembers += entry.getValue();
         if (entry.getValue() > mostCommonCount) {
            mostCommonAddress = entry.getKey();
            mostCommonCount = entry.getValue();
         }
      }

      if (mostCommonAddress != null) {
         analysis.append("The most common address among members was ").append(mostCommonAddress).append(", associated with ").append(mostCommonCount).append(" members. ");
         if (addressData.size() > 1) {
            analysis.append("Other addresses include ");
            int count = 0;
            for (Entry<String, Integer> entry : addressData.entrySet()) {
               if (!entry.getKey().equals(mostCommonAddress)) {
                  if (count > 0) {
                     analysis.append(", ");
                  }

                  analysis.append(entry.getKey()).append(" with ").append(entry.getValue()).append(" members");
                  ++count;
               }
            }

            analysis.append(". ");
         }
      } else {
         analysis.append("Unfortunately, there was no address data available for the members. ");
      }

      analysis.append("In total, there were ").append(totalMembers).append(" members recorded. ");
      this.textArea.setText(analysis.toString());
   }

   private void writeTextualAnalysisForAgeDistribution(Map<String, Integer> ageData, String selectedYear) {
      StringBuilder analysis = new StringBuilder();
      analysis.append("The age distribution analysis for the year ").append(selectedYear).append(" provides insights into the age demographics of gym members. ");
      int totalMembers = 0;
      String largestAgeRange = null;
      int largestCount = 0;
      for (Entry<String, Integer> entry : ageData.entrySet()) {
         totalMembers += entry.getValue();
         if (entry.getValue() > largestCount) {
            largestAgeRange = entry.getKey();
            largestCount = entry.getValue();
         }
      }

      if (largestAgeRange != null) {
         analysis.append("The most common age range is ").append(largestAgeRange).append(", with ").append(largestCount).append(" members. ");
         if (ageData.size() > 1) {
            analysis.append("Other age groups are also represented, such as ");
            int count = 0;
            for (Entry<String, Integer> entry : ageData.entrySet()) {
               if (!entry.getKey().equals(largestAgeRange)) {
                  if (count > 0) {
                     analysis.append(", ");
                  }

                  analysis.append(entry.getKey()).append(" with ").append(entry.getValue()).append(" members");
                  ++count;
               }
            }

            analysis.append(". ");
         }
      } else {
         analysis.append("No age distribution data is available for the selected year.");
      }

      analysis.append("In total, there were ").append(totalMembers).append(" members recorded for the year.");
      this.textArea.setText(analysis.toString());
   }

   private void writeTextualAnalysisForGenderDistribution(Map<String, Integer> genderData) {
	    StringBuilder analysis = new StringBuilder();
	    analysis.append("The analysis of gender distribution among members shows the following breakdown: ");

	    int totalMembers = 0;
	    String mostCommonGender = null;
	    int mostCommonCount = 0;

	    // Calculate total members and determine the most common gender
	    for (Map.Entry<String, Integer> entry : genderData.entrySet()) {
	        totalMembers += entry.getValue();
	        if (entry.getValue() > mostCommonCount) {
	            mostCommonGender = entry.getKey();
	            mostCommonCount = entry.getValue();
	        }
	    }

	    // Analyze and build textual output
	    if (mostCommonGender != null) {
	        analysis.append("The most common gender among members is ")
	                .append(mostCommonGender)
	                .append(", with ")
	                .append(mostCommonCount)
	                .append(" members. ");

	        // Include other genders if applicable
	        if (genderData.size() > 1) {
	            analysis.append("Other genders include ");
	            int count = 0;

	            for (Map.Entry<String, Integer> entry : genderData.entrySet()) {
	                if (!entry.getKey().equals(mostCommonGender)) {
	                    if (count > 0) {
	                        analysis.append(", ");
	                    }
	                    analysis.append(entry.getKey())
	                            .append(" with ")
	                            .append(entry.getValue())
	                            .append(" members");
	                    count++;
	                }
	            }

	            analysis.append(". ");
	        }
	    } else {
	        analysis.append("Unfortunately, there was no gender data available for the members. ");
	    }

	    // Total members summary
	    analysis.append("In total, there were ").append(totalMembers).append(" members recorded. ");

	    // Set analysis to the text area
	    this.textArea.setText(analysis.toString());
	}

   private void writeTextualAnalysisForMembership(Map<String, Integer> monthData, String selectedYear, int totalMemberships) {
	    StringBuilder analysis = new StringBuilder();

	    analysis.append("The membership data for the year ")
	            .append(selectedYear)
	            .append(" shows ")
	            .append(totalMemberships)
	            .append(" total new memberships. ");

	    if (!monthData.isEmpty()) {
	        // Find the month with the highest memberships
	        Map.Entry<String, Integer> highestMonth = null;

	        for (Map.Entry<String, Integer> entry : monthData.entrySet()) {
	            if (highestMonth == null || entry.getValue() > highestMonth.getValue()) {
	                highestMonth = entry;
	            }
	        }

	        // Append the most active month
	        if (highestMonth != null) {
	            analysis.append("The most active month for new memberships was ")
	                    .append(highestMonth.getKey())
	                    .append(", with a total of ")
	                    .append(highestMonth.getValue())
	                    .append(" memberships recorded. ");

	            // Compare other months
	            if (highestMonth.getValue() > 0 && monthData.size() > 1) {
	                analysis.append("In comparison, other months showed varying activity, with some months having fewer memberships. For instance, ");

	                int count = 0;
	                for (Map.Entry<String, Integer> entry : monthData.entrySet()) {
	                    if (!entry.getKey().equals(highestMonth.getKey())) {
	                        if (count > 0) {
	                            analysis.append(", ");
	                        }
	                        analysis.append(entry.getKey())
	                                .append(" had ")
	                                .append(entry.getValue())
	                                .append(" memberships");
	                        count++;
	                    }
	                }
	                analysis.append(". ");
	            }
	        }
	    } else {
	        // Handle no data case
	        analysis.append("There were no new memberships recorded for the selected year. ");
	    }

	    // Overall summary
	    analysis.append("Overall, the gym saw ")
	            .append(totalMemberships > 0
	                    ? "consistent membership growth, with notable activity throughout the year."
	                    : "no significant increase in memberships.");

	    // Set the text to the text area
	    this.textArea.setText(analysis.toString());
	}


   private void writeTextualAnalysisForPaymentType(Map<String, Integer> paymentData, String selectedYear) {
	    StringBuilder analysis = new StringBuilder();

	    analysis.append("The analysis of payment types for the year ")
	            .append(selectedYear)
	            .append(" shows the different preferences among members for paying their gym fees. ");

	    // Initialize variables
	    int totalPayments = 0;
	    String mostPreferred = null;
	    int mostPreferredCount = 0;

	    // Calculate total payments and determine the most preferred method
	    for (Map.Entry<String, Integer> entry : paymentData.entrySet()) {
	        int count = entry.getValue();
	        totalPayments += count;

	        if (count > mostPreferredCount) {
	            mostPreferred = entry.getKey();
	            mostPreferredCount = count;
	        }
	    }

	    if (mostPreferred != null) {
	        analysis.append("The most preferred payment method was ")
	                .append(mostPreferred)
	                .append(", used by ")
	                .append(mostPreferredCount)
	                .append(" members. ");

	        // Append other payment methods
	        if (paymentData.size() > 1) {
	            analysis.append("Other members opted for different payment methods, such as ");

	            int count = 0;
	            for (Map.Entry<String, Integer> entry : paymentData.entrySet()) {
	                if (!entry.getKey().equals(mostPreferred)) {
	                    if (count > 0) {
	                        analysis.append(", ");
	                    }

	                    analysis.append(entry.getKey())
	                            .append(" with ")
	                            .append(entry.getValue())
	                            .append(" payments");
	                    count++;
	                }
	            }
	            analysis.append(". ");
	        }
	    } else {
	        // No data case
	        analysis.append("Unfortunately, there was no payment data available for the selected year. ");
	    }

	    // Add total payments summary
	    analysis.append("In total, there were ")
	            .append(totalPayments)
	            .append(" payments recorded for the year. ");

	    // Update the text area
	    this.textArea.setText(analysis.toString());
	}

   public void populateMemberTable() {
	    String query = "SELECT * FROM member";

	    // Establish connection and execute query inside try-with-Resources
	    try (Connection con = ConnectionProvider.getCon();
	         PreparedStatement pstmt = con.prepareStatement(query);
	         ResultSet rst = pstmt.executeQuery()) {

	        // Set table column identifiers
	        ResultSetMetaData metaData = rst.getMetaData();
	        int columnCount = metaData.getColumnCount();
	        this.model.setColumnIdentifiers(new String[]{
	                "Trainee ID", "Email", "Name", "Age", "Sex", "Address",
	                "Mobile Number", "Payment Session", "Status", "Membership Fee"
	        });

	        // Populate table model with result set data
	        while (rst.next()) {
	            Object[] row = new Object[columnCount];
	            for (int i = 1; i <= columnCount; i++) {
	                row[i - 1] = rst.getObject(i);
	            }
	            this.model.addRow(row);
	        }
	    } catch (SQLException ex) {
	        // Handle SQL errors
	        JOptionPane.showMessageDialog(
	                null,
	                "Error retrieving data: " + ex.getMessage(),
	                "Database Error",
	                JOptionPane.ERROR_MESSAGE
	        );
	    }
	}

}