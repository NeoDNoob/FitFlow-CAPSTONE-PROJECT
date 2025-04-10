/* Decompiler 603ms, total 2349ms, lines 1267 */
package Main;

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
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
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

import Connectors.ConnectionProviderOFFLINE;

@SuppressWarnings("serial")
public class StaffFitFlowOffline extends JFrame {
   private String amount;
   private DefaultTableModel auditModel;
   private JLabel currentTrainees;
   private JPanel editTraineePanel;
   @SuppressWarnings("unused")
private int loggedInUserId;
   private DefaultTableModel model;
   private JTextField mtAgeField;
   private JTextField mtEmailField;
   private JTextField mtMnField;
   private JTextField mtNameField;
   private JTextField mtSearchField;
   private JTextField mtSexField;
   private JTextArea receiptArea;
   private JTabbedPane tabbedPane;
   private JTable tlTable;
   private JLabel tpDateLabel;
   private JTextField tpEmailField;
   private JTextField tpMnField;
   private JTextField tpNameField;
   private JTextField tpPaymentField;
   private JTextField tpSearchField;
   private JPanel traineePanel;
   @SuppressWarnings("unused")
private String username;
   @SuppressWarnings("unused")
private String userType;

   @SuppressWarnings({ "rawtypes", "unchecked" })
public StaffFitFlowOffline(int selectedIndex, final String username, final String userType, final int loggedInUserId) {
      this.username = username;
      this.userType = userType;
      this.loggedInUserId = loggedInUserId;
      this.setFont(new Font("Segoe UI", 0, 14));
      this.addWindowListener(new WindowAdapter() {
    	    @Override
			public void windowClosing(WindowEvent e) {
    	        String[] options = new String[]{"Exit", "Logout"};
    	        int choice = JOptionPane.showOptionDialog(null,
    	            "Do you want to Exit or Logout instead?", "Confirmation",
    	            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
    	            null, options, null);

    	        if (choice == 0 || choice == 1) {
    	            try (Connection connection = ConnectionProviderOFFLINE.getCon()) {
    	                String updateQuery = "UPDATE audit_trail SET logouttime = NOW() WHERE user_id = ? AND logouttime IS NULL";
    	                try (PreparedStatement ps = connection.prepareStatement(updateQuery)) {
    	                    ps.setInt(1, loggedInUserId);
    	                    int rowsUpdated = ps.executeUpdate();

    	                    if (rowsUpdated > 0) {
    	                        if (choice == 0) {
    	                            JOptionPane.showMessageDialog(null, "Logout recorded. Exiting application.");
    	                            System.exit(0);
    	                        } else {
    	                            JOptionPane.showMessageDialog(null, "Logout successful!");
    	                            new LoginForm();
    	                            StaffFitFlowOffline.this.dispose();
    	                        }
    	                    } else {
    	                        JOptionPane.showMessageDialog(null, "Error logging out. No active session found.");
    	                    }
    	                } catch (SQLException ex) {
    	                    JOptionPane.showMessageDialog(null, "Error executing logout query: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    	                }
    	            } catch (SQLException ex) {
    	                JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    	            }
    	        }
    	    }
    	});

      this.setDefaultCloseOperation(0);
      this.getContentPane().setForeground(new Color(255, 215, 0));
      this.setTitle("FitFlow Gym Management");
      this.setIconImage(Toolkit.getDefaultToolkit().getImage(StaffFitFlowOffline.class.getResource("/Resources/FitFlowIconPngResized.png")));
      this.getContentPane().setBackground(new Color(255, 223, 88));
      this.setSize(1200, 700);
      this.setResizable(false);
      this.setLocationRelativeTo((Component)null);
      new Font("Tahoma", 1, 25);
      this.tabbedPane = new JTabbedPane(2);
      this.tabbedPane.setTabLayoutPolicy(1);
      this.tabbedPane.setBounds(7, 58, 1170, 590);
      this.tabbedPane.setBorder(UIManager.getBorder("DesktopIcon.border"));
      this.tabbedPane.setFont(new Font("Segoe UI", 0, 25));
      this.tabbedPane.setBackground(new Color(255, 204, 51));
      this.tabbedPane.addChangeListener(new ChangeListener() {
         @Override
		public void stateChanged(ChangeEvent e) {
            JTabbedPane sourceTabbedPane = (JTabbedPane)e.getSource();
            int selectedIndex = sourceTabbedPane.getSelectedIndex();
            if (selectedIndex == 1) {
               StaffFitFlowOffline.this.updateTableData();
               StaffFitFlowOffline.this.model.fireTableStructureChanged();
               StaffFitFlowOffline.this.checkForUnpaidTrainees();
               StaffFitFlowOffline.this.updateActiveMembersLabel();
            } else if (selectedIndex == 2) {
               StaffFitFlowOffline.this.updateActiveMembersLabel();
            } else if (selectedIndex == 3) {
               StaffFitFlowOffline.this.setDateLabel();
            }

         }
      });
      this.getContentPane().setLayout((LayoutManager)null);
      this.getContentPane().add(this.tabbedPane);
      JPanel homePanel = new JPanel();
      homePanel.setBorder(UIManager.getBorder("TitledBorder.border"));
      homePanel.setBackground(new Color(0, 0, 0));
      homePanel.setLayout((LayoutManager)null);
      homePanel.setFont(new Font("Segoe UI", 1, 25));
      this.tabbedPane.addTab("Home ", new ImageIcon(StaffFitFlowOffline.class.getResource("/Resources/home-icon-silhouette.png")), homePanel, "Home");
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
      this.tabbedPane.addTab("Trainee List ", new ImageIcon(StaffFitFlowOffline.class.getResource("/Resources/group.png")), this.traineePanel, (String)null);
      JLabel traineeLabel = new JLabel("Trainee List");
      traineeLabel.setForeground(new Color(255, 215, 0));
      traineeLabel.setIcon(new ImageIcon(StaffFitFlowOffline.class.getResource("/Resources/users.png")));
      traineeLabel.setFont(new Font("Impact", 3, 47));
      traineeLabel.setBounds(485, 2, 340, 83);
      this.traineePanel.add(traineeLabel);
      JScrollPane tlScrollPane = new JScrollPane();
      tlScrollPane.setBackground(new Color(128, 128, 128));
      tlScrollPane.setBounds(56, 108, 801, 402);
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
      populateMemberTable(model);
      this.tlTable.setFillsViewportHeight(true);
      this.tlTable.setAutoscrolls(false);
      tlScrollPane.setViewportView(this.tlTable);
      this.currentTrainees = new JLabel("Current Active Trainees: 0");
      this.currentTrainees.setForeground(Color.YELLOW);
      this.currentTrainees.setFont(new Font("Segoe UI", 1, 15));
      this.currentTrainees.setBounds(71, 83, 218, 21);
      this.updateActiveMembersLabel();
      this.traineePanel.add(this.currentTrainees);
      this.editTraineePanel = new JPanel();
      this.editTraineePanel.setForeground(new Color(255, 215, 0));
      this.editTraineePanel.setLayout((LayoutManager)null);
      this.editTraineePanel.setBackground(new Color(0, 0, 0));
      this.tabbedPane.addTab("Manage Trainee ", new ImageIcon(StaffFitFlowOffline.class.getResource("/Resources/user-avatar.png")), this.editTraineePanel, (String)null);
      JLabel lblEditTrainee = new JLabel("Manage Trainee");
      lblEditTrainee.setForeground(new Color(255, 215, 0));
      lblEditTrainee.setBounds(440, 0, 422, 83);
      lblEditTrainee.setIcon(new ImageIcon(StaffFitFlowOffline.class.getResource("/Resources/pencil.png")));
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
      mtStatusField.setModel(new DefaultComboBoxModel(new String[]{"Select", "Active", "Inactive"}));
      mtStatusField.setFont(new Font("Segoe UI", 1, 18));
      mtStatusField.setBounds(476, 333, 115, 30);
      this.editTraineePanel.add(mtStatusField);
      String[] paymentTF = new String[]{"Please select", "By Session", "Monthly"};
      final JComboBox<String> mtPaymentTimeField = new JComboBox(paymentTF);
      mtPaymentTimeField.setForeground(Color.WHITE);
      mtPaymentTimeField.setBackground(Color.DARK_GRAY);
      mtPaymentTimeField.setModel(new DefaultComboBoxModel(new String[]{"Please select", "By Session", "Monthly"}));
      mtPaymentTimeField.setFont(new Font("Segoe UI", 1, 18));
      mtPaymentTimeField.setBounds(673, 333, 160, 30);
      this.editTraineePanel.add(mtPaymentTimeField);
      JButton mtSearchButton = new JButton("Search");
      mtSearchButton.setBorder(new LineBorder(new Color(51, 51, 51), 2, true));
      mtSearchButton.setFocusable(false);
      mtSearchButton.setForeground(new Color(0, 0, 0));
      mtSearchButton.setBackground(new Color(255, 204, 51));
      mtSearchButton.addActionListener(new ActionListener() {
    	    @Override
			public void actionPerformed(ActionEvent e) {
    	        boolean checkID = false;  // Changed to boolean for clarity
    	        String id = StaffFitFlowOffline.this.mtSearchField.getText();

    	        try (Connection con = ConnectionProviderOFFLINE.getCon()) {  // Using try-with-Resources to handle connection
    	            Statement st = con.createStatement();
    	            ResultSet rst = st.executeQuery("SELECT * FROM member WHERE id = '" + id + "'");

    	            while (rst.next()) {
    	                checkID = true;
    	                StaffFitFlowOffline.this.mtNameField.setText(rst.getString(3));
    	                StaffFitFlowOffline.this.mtAgeField.setText(rst.getString(4));
    	                StaffFitFlowOffline.this.mtSexField.setText(rst.getString(5));
    	                StaffFitFlowOffline.this.mtSexField.setEditable(false);  // Ensures this field is non-editable
    	                StaffFitFlowOffline.this.mtMnField.setText(rst.getString(7));
    	                StaffFitFlowOffline.this.mtEmailField.setText(rst.getString(2));
    	                String paymentTF = rst.getString(8);
    	                String status = rst.getString(9);
    	                mtPaymentTimeField.setSelectedItem(paymentTF);
    	                mtStatusField.setSelectedItem(status);
    	            }

    	            if (!checkID) {
    	                JOptionPane.showMessageDialog(null, "Member ID not found");
    	            }

    	        } catch (SQLException var9) {
    	            JOptionPane.showMessageDialog(null, "Error: " + var9.getMessage());
    	            var9.printStackTrace();
    	        }
    	    }
    	});

      mtSearchButton.setBounds(288, 84, 110, 30);
      mtSearchButton.setIcon(new ImageIcon(StaffFitFlowOffline.class.getResource("/Resources/magnifying-glass.png")));
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
      lblNewLabel_3.setBounds(64, 297, 46, 20);
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
      lblNewLabel_3_1.setBounds(476, 139, 150, 16);
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
      lblNewLabel_3_1_1.setBounds(474, 218, 90, 16);
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
      lblNewLabel_4.setBounds(476, 300, 66, 15);
      this.editTraineePanel.add(lblNewLabel_4);
      JButton mtUpdateButton = new JButton("Update");
      mtUpdateButton.setBorder(new LineBorder(new Color(51, 51, 51), 2, true));
      mtUpdateButton.setFocusable(false);
      mtUpdateButton.setForeground(new Color(0, 0, 0));
      mtUpdateButton.setBackground(new Color(255, 204, 51));
      mtUpdateButton.addActionListener(new ActionListener() {
         @Override
		public void actionPerformed(ActionEvent e) {
            String id = StaffFitFlowOffline.this.mtSearchField.getText();
            String name = StaffFitFlowOffline.this.mtNameField.getText();
            String age = StaffFitFlowOffline.this.mtAgeField.getText();
            String mobilenumber = StaffFitFlowOffline.this.mtMnField.getText();
            String email = StaffFitFlowOffline.this.mtEmailField.getText();
            String status = (String)mtStatusField.getSelectedItem();
            String paymentTF = (String)mtPaymentTimeField.getSelectedItem();

            try {
               Connection con = ConnectionProviderOFFLINE.getCon();
               PreparedStatement ps = con.prepareStatement("UPDATE member SET name=?, age=?, mobile_number=?, email=?, status=?, payment_time=? WHERE id=?");
               ps.setString(1, name);
               ps.setString(2, age);
               ps.setString(3, mobilenumber);
               ps.setString(4, email);
               ps.setString(5, status);
               ps.setString(6, paymentTF);
               ps.setString(7, id);
               ps.executeUpdate();
               JOptionPane.showMessageDialog((Component)null, "Trainee Information Updated Successfully");
               StaffFitFlowOffline.this.mtSearchField.setText("");
               StaffFitFlowOffline.this.mtSearchField.setEditable(true);
               StaffFitFlowOffline.this.mtNameField.setText("");
               StaffFitFlowOffline.this.mtAgeField.setText("");
               StaffFitFlowOffline.this.mtSexField.setText("");
               StaffFitFlowOffline.this.mtEmailField.setText("");
               StaffFitFlowOffline.this.mtMnField.setText("");
               StaffFitFlowOffline.this.mtEmailField.setText("");
               mtPaymentTimeField.setSelectedItem("Payment Session");
               mtStatusField.setSelectedItem("Select");
            } catch (Exception var11) {
               JOptionPane.showMessageDialog((Component)null, var11);
            }

         }
      });
      mtUpdateButton.setFont(new Font("Segoe UI", 1, 18));
      mtUpdateButton.setIcon(new ImageIcon(StaffFitFlowOffline.class.getResource("/Resources/changes.png")));
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
            String confirmation = JOptionPane.showInputDialog((Component)null, "Please type 'Confirm' to continue");
            if (confirmation != null && confirmation.equals("Confirm")) {
               int a = JOptionPane.showConfirmDialog((Component)null, "Do you really want to delete this trainee?", "Select", 0);
               if (a == 0) {
                  String id = StaffFitFlowOffline.this.mtSearchField.getText();

                  try {
                     Connection con = ConnectionProviderOFFLINE.getCon();
                     PreparedStatement pst = con.prepareStatement("DELETE FROM member WHERE id=?");
                     pst.setString(1, id);
                     pst.executeUpdate();
                     JOptionPane.showMessageDialog((Component)null, "Trainee Successfully Deleted");
                     StaffFitFlowOffline.this.mtSearchField.setText("");
                     StaffFitFlowOffline.this.mtSearchField.setEditable(true);
                     StaffFitFlowOffline.this.mtNameField.setText("");
                     StaffFitFlowOffline.this.mtAgeField.setText("");
                     StaffFitFlowOffline.this.mtSexField.setText("");
                     StaffFitFlowOffline.this.mtEmailField.setText("");
                     StaffFitFlowOffline.this.mtMnField.setText("");
                     StaffFitFlowOffline.this.mtEmailField.setText("");
                  } catch (Exception var7) {
                     JOptionPane.showMessageDialog((Component)null, var7);
                  }
               }
            } else {
               JOptionPane.showMessageDialog((Component)null, "Type 'Confirm' properly");
            }

         }
      });
      mtDeleteButton.setFont(new Font("Segoe UI", 1, 18));
      mtDeleteButton.setIcon(new ImageIcon(StaffFitFlowOffline.class.getResource("/Resources/trash.png")));
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
            StaffFitFlowOffline.this.mtSearchField.setText("");
            StaffFitFlowOffline.this.mtSearchField.setEditable(true);
            StaffFitFlowOffline.this.mtNameField.setText("");
            StaffFitFlowOffline.this.mtAgeField.setText("");
            StaffFitFlowOffline.this.mtSexField.setText("");
            StaffFitFlowOffline.this.mtEmailField.setText("");
            StaffFitFlowOffline.this.mtMnField.setText("");
            StaffFitFlowOffline.this.mtEmailField.setText("");
            mtStatusField.setSelectedItem("Select");
            mtPaymentTimeField.setSelectedItem("Please Select");
         }
      });
      mtResetButton.setFont(new Font("Segoe UI", 1, 18));
      mtResetButton.setIcon(new ImageIcon(StaffFitFlowOffline.class.getResource("/Resources/magnifying-glass.png")));
      mtResetButton.setBounds(256, 474, 120, 30);
      this.editTraineePanel.add(mtResetButton);
      JLabel paymentT = new JLabel("Payment Sesion");
      paymentT.setForeground(new Color(255, 215, 0));
      paymentT.setFont(new Font("Segoe UI", 1, 19));
      paymentT.setBounds(673, 295, 150, 25);
      this.editTraineePanel.add(paymentT);
      JPanel membershipPaymentPanel = new JPanel();
      membershipPaymentPanel.setLayout((LayoutManager)null);
      membershipPaymentPanel.setFont(new Font("Tahoma", 1, 20));
      membershipPaymentPanel.setBackground(new Color(0, 0, 0));
      this.tabbedPane.addTab("Trainee Payment ", new ImageIcon(StaffFitFlowOffline.class.getResource("/Resources/wallet-filled-money-tool.png")), membershipPaymentPanel, "Trainee Payment");
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
      this.tpDateLabel.setBounds(78, 128, 250, 24);
      this.tpDateLabel.setFont(new Font("Segoe UI", 1, 18));
      membershipPaymentPanel.add(this.tpDateLabel);
      membershipPaymentPanel.setForeground(new Color(0, 0, 0));
      JLabel lblPayment = new JLabel("Payment");
      lblPayment.setBounds(574, 0, 300, 83);
      lblPayment.setForeground(new Color(255, 215, 0));
      lblPayment.setIcon(new ImageIcon(StaffFitFlowOffline.class.getResource("/Resources/wallet.png")));
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
      tpPaymentTimeField.setBounds(480, 296, 155, 30);
      tpPaymentTimeField.setFont(new Font("Segoe UI", 1, 18));
      membershipPaymentPanel.add(tpPaymentTimeField);
      final JButton tpSaveButton = new JButton("Save");
      tpSaveButton.setFocusable(false);
      tpSaveButton.setBorder(new LineBorder(new Color(51, 51, 51), 2, true));
      tpSaveButton.setBackground(new Color(255, 204, 51));
      tpSaveButton.setForeground(new Color(0, 0, 0));
      tpSaveButton.setBounds(475, 386, 110, 30);
      tpSaveButton.addActionListener(new ActionListener() {
         @Override
		public void actionPerformed(ActionEvent e) {
            String memberName = StaffFitFlowOffline.this.tpNameField.getText();
            String paymentTime = (String)tpPaymentTimeField.getSelectedItem();
            if (StaffFitFlowOffline.this.isPaymentAllowed(memberName, paymentTime)) {
               String date = StaffFitFlowOffline.this.tpDateLabel.getText();
               StaffFitFlowOffline.this.amount = StaffFitFlowOffline.this.tpPaymentField.getText();

               try {
                  Connection con = ConnectionProviderOFFLINE.getCon();
                  PreparedStatement ps = con.prepareStatement("INSERT INTO payments (date, payment_time, amount, member_name) VALUES (?,?,?,?)");
                  ps.setString(1, date);
                  ps.setString(2, paymentTime);
                  ps.setString(3, StaffFitFlowOffline.this.amount);
                  ps.setString(4, memberName);
                  ps.executeUpdate();
                  JOptionPane.showMessageDialog((Component)null, "Payment Successful");
               } catch (Exception var10) {
                  JOptionPane.showMessageDialog((Component)null, var10);
               }

               StringBuilder receipt = new StringBuilder();
               receipt.append("--------------------------------\n");
               receipt.append("     IDOL'S FITNESS GYM\n");
               receipt.append("    M. Leonor St, II-B, SPC\n");
               receipt.append("--------------------------------\n");
               receipt.append("    Date: ").append(date).append("\n");
               receipt.append("    Name: ").append(memberName).append("\n");
               receipt.append("    Amount to Pay: ₱").append(StaffFitFlowOffline.this.amount).append("\n");
               receipt.append("    Payment Session: ").append(paymentTime).append("\n");
               receipt.append("--------------------------------\n");
               receipt.append("     FitFlow System\n");
               receipt.append("       By Group 3\n");
               receipt.append("--------------------------------\n");
               JTextArea receiptArea = new JTextArea(receipt.toString());
               receiptArea.setSize(265, 250);
               receiptArea.setEditable(false);
               JScrollPane scrollPane = new JScrollPane(receiptArea);
               scrollPane.setPreferredSize(new Dimension(265, 265));
               StaffFitFlowOffline.this.tpNameField.setText("");
               StaffFitFlowOffline.this.tpPaymentField.setText("");
               StaffFitFlowOffline.this.tpEmailField.setText("");
               StaffFitFlowOffline.this.tpMnField.setText("");
               tpPaymentTimeField.setSelectedItem("Please Select");
               Object[] options = new Object[]{"Print Receipt"};
               int result = JOptionPane.showOptionDialog((Component)null, scrollPane, "Receipt", -1, -1, (Icon)null, options, options[0]);
               if (result == 0) {
                  StaffFitFlowOffline.this.printReceipt(receipt.toString());
               }
            }

         }
      });
      tpSaveButton.setIcon(new ImageIcon(StaffFitFlowOffline.class.getResource("/Resources/diskette.png")));
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
    	        StaffFitFlowOffline.this.setDateLabel();
    	        boolean checkID = false;  // Changed type to boolean (instead of int)
    	        String id = StaffFitFlowOffline.this.tpSearchField.getText();
    	        String date = StaffFitFlowOffline.this.tpDateLabel.getText();

    	        try (Connection con = ConnectionProviderOFFLINE.getCon()) {  // Using try-with-Resources to handle connection
    	            PreparedStatement pst = con.prepareStatement("SELECT * FROM member WHERE id =?");
    	            pst.setString(1, id);

    	            try (ResultSet rst = pst.executeQuery()) {
    	                if (rst.next()) {
    	                    checkID = true;
    	                    StaffFitFlowOffline.this.tpEmailField.setText(rst.getString("email"));
    	                    StaffFitFlowOffline.this.tpNameField.setText(rst.getString("name"));
    	                    StaffFitFlowOffline.this.tpMnField.setText(rst.getString("mobile_number"));
    	                    tpPaymentTimeField.setSelectedItem(rst.getString("payment_time"));
    	                } else {
    	                    JOptionPane.showMessageDialog(null, "Member ID not found\n\nTry: Ask them to apply for a membership or manually input the trainee's information");
    	                }

    	                if (checkID) {
    	                    PreparedStatement psl = con.prepareStatement("SELECT * FROM payments WHERE date=? AND id=?");
    	                    psl.setString(1, date);
    	                    psl.setString(2, id);

    	                    try (ResultSet rsl = psl.executeQuery()) {
    	                        if (rsl.next()) {
    	                            tpSaveButton.setVisible(false);
    	                            JOptionPane.showMessageDialog(null, "This trainee is already paid");
    	                        }
    	                    } catch (SQLException var109) {
    	                        JOptionPane.showMessageDialog(null, "Error: " + var109.getMessage());
    	                    } finally {
    	                        if (psl != null)
								 {
									psl.close();  // Ensuring PreparedStatement is closed after use
								}
    	                    }
    	                }
    	            } catch (SQLException var113) {
    	                JOptionPane.showMessageDialog(null, "Error: " + var113.getMessage());
    	            } finally {
    	                if (pst != null)
						 {
							pst.close();  // Ensuring PreparedStatement is closed after use
						}
    	            }

    	        } catch (SQLException var117) {
    	            JOptionPane.showMessageDialog(null, "Error: " + var117.getMessage());
    	            var117.printStackTrace();
    	        }
    	    }
    	});

      tpSearchButton.setIcon(new ImageIcon(StaffFitFlowOffline.class.getResource("/Resources/magnifying-glass.png")));
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
      lblAmountToPay.setBounds(480, 174, 150, 24);
      lblAmountToPay.setFont(new Font("Segoe UI", 1, 18));
      membershipPaymentPanel.add(lblAmountToPay);
      this.tpPaymentField = new JTextField();
      this.tpPaymentField.setForeground(Color.WHITE);
      this.tpPaymentField.setHorizontalAlignment(0);
      this.tpPaymentField.setBounds(505, 206, 100, 35);
      this.tpPaymentField.setBackground(Color.DARK_GRAY);
      this.tpPaymentField.setFont(new Font("Segoe UI", 1, 18));
      this.tpPaymentField.setColumns(10);
      membershipPaymentPanel.add(this.tpPaymentField);
      JLabel pesoSign = new JLabel("₱");
      pesoSign.setForeground(new Color(255, 215, 0));
      pesoSign.setBounds(480, 211, 60, 24);
      pesoSign.setFont(new Font("Segoe UI", 1, 18));
      membershipPaymentPanel.add(pesoSign);
      JButton tpResetButton1 = new JButton("Reset");
      tpResetButton1.setFocusable(false);
      tpResetButton1.setBackground(new Color(255, 204, 51));
      tpResetButton1.setBorder(new LineBorder(new Color(51, 51, 51), 2, true));
      tpResetButton1.setForeground(new Color(0, 0, 0));
      tpResetButton1.setBounds(615, 386, 110, 30);
      tpResetButton1.addActionListener(new ActionListener() {
         @Override
		public void actionPerformed(ActionEvent e) {
            StaffFitFlowOffline.this.tpSearchField.setText("");
            StaffFitFlowOffline.this.tpNameField.setText("");
            StaffFitFlowOffline.this.tpMnField.setText("");
            StaffFitFlowOffline.this.tpEmailField.setText("");
            StaffFitFlowOffline.this.tpPaymentField.setText("");
            tpPaymentTimeField.setSelectedItem("Please Select");
            tpSaveButton.setVisible(true);
         }
      });
      tpResetButton1.setIcon(new ImageIcon(StaffFitFlowOffline.class.getResource("/Resources/undo.png")));
      tpResetButton1.setFont(new Font("Segoe UI", 1, 18));
      membershipPaymentPanel.add(tpResetButton1);
      JLabel paymentTFlbl = new JLabel("Payment Session");
      paymentTFlbl.setForeground(new Color(255, 215, 0));
      paymentTFlbl.setBounds(480, 261, 160, 24);
      paymentTFlbl.setFont(new Font("Segoe UI", 1, 18));
      membershipPaymentPanel.add(paymentTFlbl);
      this.auditModel = new DefaultTableModel();
      this.populateAuditTable();
      JButton toPOSButton = new JButton("POS");
      toPOSButton.setBounds(1065, 7, 107, 45);
      toPOSButton.setFocusable(false);
      toPOSButton.addActionListener(new ActionListener() {
         @Override
		public void actionPerformed(ActionEvent e) {
            StaffPOS StaffPOS = new StaffPOS(0, username, userType, loggedInUserId);
            StaffPOS.setVisible(true);
            StaffFitFlowOffline.this.setVisible(false);
         }
      });
      toPOSButton.setBackground(new Color(224, 177, 25));
      toPOSButton.setIcon(new ImageIcon(StaffFitFlowOffline.class.getResource("/Resources/POS.png")));
      toPOSButton.setFont(new Font("Segoe UI", 1, 17));
      this.getContentPane().add(toPOSButton);
      JLabel userLabel = new JLabel("Logged in as: " + username + " (" + userType + ")");
      userLabel.setFont(new Font("Segoe UI", 1, 18));
      userLabel.setBounds(7, 35, 369, 23);
      this.getContentPane().add(userLabel);
      JLabel power = new JLabel("Powered by: \nGroup 3 - Lyceum de San Pablo");
      power.setFont(new Font("Franklin Gothic Medium", 3, 15));
      power.setBounds(748, 36, 317, 16);
      this.getContentPane().add(power);
   }

   private void checkForUnpaidTrainees() {
	    for (int i = 0; i < this.model.getRowCount(); ++i) {
	        String membershipFee = (String) this.model.getValueAt(i, 9);
	        if ("Unpaid".equalsIgnoreCase(membershipFee)) {
	            String name = (String) this.model.getValueAt(i, 2);
	            int response = JOptionPane.showConfirmDialog(null, "Have " + name + " paid the membership fee?", "Unpaid Membership Fee", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
	            int traineeId;

	            if (response == JOptionPane.YES_OPTION) {
	                traineeId = (Integer) this.model.getValueAt(i, 0);
	                updatePaymentStatus(traineeId, name, i);
	            } else if (response == JOptionPane.NO_OPTION) {
	                traineeId = (Integer) this.model.getValueAt(i, 0);
	                int deleteResponse = JOptionPane.showConfirmDialog(null, "Do you want to delete the unpaid trainee with ID: " + traineeId + "?", "Delete Unpaid Trainee", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
	                if (deleteResponse == JOptionPane.YES_OPTION) {
	                    deleteTrainee(traineeId, i);
	                }
	            }
	            break;  // Exit after first match
	        }
	    }
	}

	private void updatePaymentStatus(int traineeId, String name, int rowIndex) {
	    String updateQuery = "UPDATE member SET MembershipFee = ? WHERE id = ?";
	    try (Connection con = ConnectionProviderOFFLINE.getCon(); PreparedStatement pstmt = con.prepareStatement(updateQuery)) {
	        pstmt.setString(1, "Paid");
	        pstmt.setInt(2, traineeId);
	        pstmt.executeUpdate();

	        JOptionPane.showMessageDialog(null, "Payment Status Updated Successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
	        this.model.setValueAt("Paid", rowIndex, 9);

	        printReceipt(name);
	    } catch (SQLException e) {
	        JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}

	private void printReceipt(String name) {
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
	    JOptionPane.showOptionDialog(null, scrollPane, "Receipt", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

	    // Now, let's print the receipt.
	    PrinterJob printerJob = PrinterJob.getPrinterJob();
	    printerJob.setPrintable(new Printable() {
	        @Override
			public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
	            if (pageIndex > 0) {
	                return NO_SUCH_PAGE; // Only one page to print.
	            }

	            Graphics2D g2d = (Graphics2D) graphics;
	            g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
	            g2d.setFont(new Font("Monospaced", Font.PLAIN, 10));

	            // Get the content of the receipt
	            String receiptContent = membershipReceipt.toString();
	            String[] lines = receiptContent.split("\n");

	            int yPosition = 20;
	            for (String line : lines) {
	                g2d.drawString(line, 10, yPosition);  // Draw each line
	                yPosition += 15; // Move down for the next line
	            }

	            return PAGE_EXISTS;
	        }
	    });

	    // Show the print dialog to the user
	    boolean userAccepted = printerJob.printDialog();
	    if (userAccepted) {
	        try {
	            printerJob.print();  // Print the job
	        } catch (PrinterException e) {
	            JOptionPane.showMessageDialog(null, "Error printing receipt: " + e.getMessage());
	        }
	    }
	}

	private void deleteTrainee(int traineeId, int rowIndex) {
	    String deleteQuery = "DELETE FROM member WHERE id = ?";
	    try (Connection con = ConnectionProviderOFFLINE.getCon(); PreparedStatement pstmt = con.prepareStatement(deleteQuery)) {
	        pstmt.setInt(1, traineeId);
	        pstmt.executeUpdate();
	        JOptionPane.showMessageDialog(null, "Unpaid Trainee Successfully Deleted", "Success", JOptionPane.INFORMATION_MESSAGE);
	        this.model.removeRow(rowIndex);
	    } catch (SQLException e) {
	        JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}


   private int getActiveMembersCount() {
	    int count = 0;
	    String status = "Active";

	    String query = "SELECT COUNT(*) AS count FROM member WHERE status = ?";

	    try (Connection con = ConnectionProviderOFFLINE.getCon();
	         PreparedStatement pst = con.prepareStatement(query)) {

	        pst.setString(1, status);

	        try (ResultSet rs = pst.executeQuery()) {
	            if (rs.next()) {
	                count = rs.getInt("count");
	            }
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return count;
	}


   private boolean isPaymentAllowed(String memberName, String paymentTime) {
      try {
         Connection con = ConnectionProviderOFFLINE.getCon();
         PreparedStatement ps;
         ResultSet rs;
         if (paymentTime.equals("By Session")) {
            ps = con.prepareStatement("SELECT * FROM payments WHERE member_name = ? AND payment_time = ? AND DATE(date) = CURDATE()");
            ps.setString(1, memberName);
            ps.setString(2, paymentTime);
            rs = ps.executeQuery();
         } else {
            if (!paymentTime.equals("Monthly")) {
               return true;
            }

            ps = con.prepareStatement("SELECT * FROM payments WHERE member_name = ? AND payment_time = ? AND YEAR(date) = YEAR(CURDATE()) AND MONTH(date) = MONTH(CURDATE())");
            ps.setString(1, memberName);
            ps.setString(2, paymentTime);
            rs = ps.executeQuery();
         }

         if (rs.next()) {
            if (paymentTime.equals("By Session")) {
               JOptionPane.showMessageDialog((Component)null, "This trainee is already paid today");
            } else if (paymentTime.equals("Monthly")) {
               JOptionPane.showMessageDialog((Component)null, "This trainee is already paid this month");
            }

            return false;
         }
      } catch (Exception var6) {
         JOptionPane.showMessageDialog((Component)null, var6);
      }

      return true;
   }

   private void populateAuditTable() {
	    String query = "SELECT username, logintime, logouttime FROM audit_trail";

	    try (Connection con = ConnectionProviderOFFLINE.getCon();
	         PreparedStatement pstmt = con.prepareStatement(query);
	         ResultSet rst = pstmt.executeQuery()) {

	        this.auditModel.setColumnIdentifiers(new String[]{"Username", "Login Time", "Logout Time"});

	        while (rst.next()) {
	            Object[] row = new Object[]{
	                rst.getString("username"),
	                rst.getTimestamp("logintime"),
	                rst.getTimestamp("logouttime")
	            };
	            this.auditModel.addRow(row);
	        }

	    } catch (SQLException e) {
	        JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
	    }
	}


   public void setDateLabel() {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      this.tpDateLabel.setText(sdf.format(new Date()));
   }

   private void updateActiveMembersLabel() {
      int activeMembersCount = this.getActiveMembersCount();
      this.currentTrainees.setText("Current Active Trainees: " + activeMembersCount);
   }

   private void updateTableData() {
	    this.model.setRowCount(0);

	    String query = "SELECT * FROM member";

	    try (Connection con = ConnectionProviderOFFLINE.getCon();
	         PreparedStatement pstmt = con.prepareStatement(query);
	         ResultSet rst = pstmt.executeQuery()) {

	        ResultSetMetaData metaData = rst.getMetaData();
	        int columnCount = metaData.getColumnCount();
	        this.model.setColumnIdentifiers(new String[]{
	            "Trainee ID", "Email", "Name", "Age", "Sex", "Address", "Mobile Number",
	            "Payment Session", "Status", "Membership Fee"
	        });

	        while (rst.next()) {
	            Object[] row = new Object[columnCount];
	            for (int i = 1; i <= columnCount; ++i) {
	                row[i - 1] = rst.getObject(i);
	            }
	            this.model.addRow(row);
	        }

	    } catch (SQLException e) {
	        JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
	    }
	}
   private void populateMemberTable(DefaultTableModel model) {
	    try (Connection con = ConnectionProviderOFFLINE.getCon()) {  // Using try-with-Resources for connection
	        String query = "SELECT * FROM member";
	        try (PreparedStatement pstmt = con.prepareStatement(query);
	             ResultSet rst = pstmt.executeQuery()) {

	            ResultSetMetaData metaData = rst.getMetaData();
	            int columnCount = metaData.getColumnCount();

	            // Set column names for the table model
	            model.setColumnIdentifiers(new String[]{
	                "Trainee ID", "Email", "Name", "Age", "Sex", "Address", "Mobile Number",
	                "Payment Session", "Status", "Membership Fee"
	            });

	            // Populate rows with data from the result set
	            while (rst.next()) {
	                Object[] row = new Object[columnCount];
	                for (int i = 1; i <= columnCount; ++i) {
	                    row[i - 1] = rst.getObject(i);
	                }
	                model.addRow(row);
	            }

	        } catch (SQLException var55) {
	            JOptionPane.showMessageDialog(null, "Error: " + var55.getMessage());
	        }
	    } catch (SQLException var56) {
	        JOptionPane.showMessageDialog(null, "Connection Error: " + var56.getMessage());
	    }
	}


}