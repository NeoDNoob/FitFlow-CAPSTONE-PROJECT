/* Decompiler 155ms, total 740ms, lines 190 */
package Main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import Connectors.ConnectionProvider;

@SuppressWarnings("serial")
public class AddUser extends JFrame {
   private JPasswordField passwordField;
   private JTextField usernameField;
   private JTextField nameField;

   @SuppressWarnings({ "unchecked", "rawtypes" })
AddUser() {
      this.setIconImage(Toolkit.getDefaultToolkit().getImage(AddUser.class.getResource("/Resources/FitFlowIconPngResized.png")));
      this.setTitle("Create New User");
      this.setType(Type.UTILITY);
      this.setSize(440, 447);
      this.setLocationRelativeTo((Component)null);
      this.getContentPane().setBackground(Color.BLACK);
      this.getContentPane().setForeground(new Color(0, 0, 0));
      this.setResizable(false);
      this.getContentPane().setLayout((LayoutManager)null);
      getContentPane().setLayout(null);
      getContentPane().setLayout(null);
      JLabel lblNewLabel = new JLabel("CREATE NEW USER");
      lblNewLabel.setForeground(new Color(255, 215, 0));
      lblNewLabel.setFont(new Font("Impact", 0, 20));
      lblNewLabel.setBounds(130, 18, 170, 21);
      this.getContentPane().add(lblNewLabel);
      JLabel usernameLabel = new JLabel("Username");
      usernameLabel.setForeground(new Color(255, 255, 255));
      usernameLabel.setFont(new Font("Segoe UI", 1, 14));
      usernameLabel.setBounds(87, 127, 104, 16);
      this.getContentPane().add(usernameLabel);
      JLabel passLabel = new JLabel("Password");
      passLabel.setForeground(new Color(255, 255, 255));
      passLabel.setFont(new Font("Segoe UI", 1, 14));
      passLabel.setBounds(87, 195, 104, 16);
      this.getContentPane().add(passLabel);
      this.passwordField = new JPasswordField();
      this.passwordField.setForeground(Color.WHITE);
      this.passwordField.setBackground(Color.DARK_GRAY);
      this.passwordField.setFont(new Font("Segoe UI", 1, 16));
      this.passwordField.setBounds(87, 222, 213, 30);
      this.getContentPane().add(this.passwordField);
      final JComboBox userTypeField = new JComboBox();
      userTypeField.setBackground(Color.DARK_GRAY);
      userTypeField.setForeground(Color.WHITE);
      userTypeField.setFont(new Font("Segoe UI", 1, 14));
      userTypeField.setModel(new DefaultComboBoxModel(new String[]{"Select", "Admin", "Owner", "Staff"}));
      userTypeField.setBounds(87, 300, 80, 30);
      this.getContentPane().add(userTypeField);
      JButton createButton = new JButton("Create");
      createButton.setForeground(Color.BLACK);
      createButton.setBackground(new Color(255, 204, 51));
      createButton.setBorder(new LineBorder(new Color(51, 51, 51), 2, true));
      createButton.setIcon(new ImageIcon(AddUser.class.getResource("/resources/diskette.png")));
      createButton.addActionListener(new ActionListener() {
    	    @Override
			public void actionPerformed(ActionEvent e) {
    	        String newUsername = AddUser.this.usernameField.getText().trim();
    	        @SuppressWarnings("deprecation")
    	        String newPassword = AddUser.this.passwordField.getText().trim();
    	        String userType = userTypeField.getSelectedItem().toString();
    	        String newName = AddUser.this.nameField.getText().trim(); // Get name from nameField

    	        if (!newUsername.isEmpty() && !newPassword.isEmpty() && !userType.equals("Select") && !newName.isEmpty()) {
    	            // Modified SQL query to include the 'name' field
    	            String sql = "INSERT INTO admincred (username, password, userType, name) VALUES (?, ?, ?, ?)";

    	            try (Connection con = ConnectionProvider.getCon();
    	                 PreparedStatement pstmt = con.prepareStatement(sql)) {

    	                // Set parameters
    	                pstmt.setString(1, newUsername);
    	                pstmt.setString(2, newPassword);
    	                pstmt.setString(3, userType);
    	                pstmt.setString(4, newName); // Set the 'name' parameter

    	                // Execute update
    	                int rowsAffected = pstmt.executeUpdate();

    	                if (rowsAffected > 0) {
    	                    JOptionPane.showMessageDialog(null, "User created successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
    	                    AddUser.this.dispose();
    	                } else {
    	                    JOptionPane.showMessageDialog(null, "Failed to create the user. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
    	                }

    	            } catch (SQLException ex) {
    	                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    	            }

    	        } else {
    	            JOptionPane.showMessageDialog(null, "Please fill in all the fields.", "Error", JOptionPane.ERROR_MESSAGE);
    	        }
    	    }
    	});

      createButton.setFont(new Font("Segoe UI", 1, 14));
      createButton.setBounds(87, 341, 104, 35);
      this.getContentPane().add(createButton);
      JButton btnCancel = new JButton("Cancel");
      btnCancel.setForeground(Color.BLACK);
      btnCancel.setBackground(new Color(255, 204, 51));
      btnCancel.setBorder(new LineBorder(new Color(51, 51, 51), 2, true));
      btnCancel.setIcon(new ImageIcon(AddUser.class.getResource("/resources/cancel.png")));
      btnCancel.addActionListener(new ActionListener() {
         @Override
		public void actionPerformed(ActionEvent e) {
            AddUser.this.dispose();
         }
      });
      btnCancel.setFont(new Font("Segoe UI", 1, 14));
      btnCancel.setBounds(211, 341, 104, 35);
      this.getContentPane().add(btnCancel);
      final JCheckBox showPassword = new JCheckBox();
      showPassword.setFocusable(false);
      showPassword.setText("Show Password");
      showPassword.setFont(new Font("Segoe UI", 1, 12));
      showPassword.setForeground(Color.WHITE);
      showPassword.setBackground(new Color(0, 0, 0));
      showPassword.setToolTipText("Show/Hide Password");
      showPassword.setBounds(82, 266, 130, 14);
      this.getContentPane().add(showPassword);
      JLabel lblNewLabel_1_1_1 = new JLabel("User Type");
      lblNewLabel_1_1_1.setFont(new Font("Tahoma", 1, 14));
      lblNewLabel_1_1_1.setBounds(87, 195, 104, 16);
      this.getContentPane().add(lblNewLabel_1_1_1);

      usernameField = new JTextField();
      usernameField.setForeground(Color.WHITE);
      usernameField.setFont(new Font("Segoe UI", Font.BOLD, 16));
      usernameField.setColumns(10);
      usernameField.setBackground(Color.DARK_GRAY);
      usernameField.setBounds(87, 154, 213, 30);
      getContentPane().add(usernameField);

      nameField = new JTextField();
      nameField.setForeground(Color.WHITE);
      nameField.setFont(new Font("Segoe UI", Font.BOLD, 16));
      nameField.setColumns(10);
      nameField.setBackground(Color.DARK_GRAY);
      nameField.setBounds(87, 91, 213, 30);
      getContentPane().add(nameField);

      JLabel nameLabel = new JLabel("Name");
      nameLabel.setForeground(Color.WHITE);
      nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
      nameLabel.setBounds(87, 67, 104, 16);
      getContentPane().add(nameLabel);
      showPassword.addActionListener(new ActionListener() {
         @Override
		public void actionPerformed(ActionEvent e) {
            if (showPassword.isSelected()) {
               AddUser.this.passwordField.setEchoChar('\u0000');
            } else {
               AddUser.this.passwordField.setEchoChar('●');
            }

         }
      });
   }
}