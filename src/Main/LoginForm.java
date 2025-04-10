/* Decompiler 191ms, total 625ms, lines 356 */
package Main;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

import com.formdev.flatlaf.FlatLightLaf;

import Connectors.ConnectionProvider;

@SuppressWarnings("serial")
public class LoginForm extends JFrame {
   LoginForm() {
      this.addWindowListener(new WindowAdapter() {
         @Override
		public void windowClosing(WindowEvent e) {
            int choice = JOptionPane.showConfirmDialog((Component)null, "Do you really want to exit FitFlow?", "Exit Confirmation", 0, 3);
            if (choice == 0) {
               System.exit(0);
            }

         }
      });
      this.setDefaultCloseOperation(0);

      try {
         UIManager.setLookAndFeel(new FlatLightLaf());
      } catch (Exception var16) {
         var16.printStackTrace();
      }

      UIManager.put("flatlaf.button.arc", "20,20");
      UIManager.put("flatlaf.scrollPane.arc", "20,20");
      UIManager.put("flatlaf.progressBar.arc", "20,20");
      this.setTitle("FitFlow - Login");
      this.setSize(1000, 650);
      this.setResizable(false);
      this.setLocationRelativeTo((Component)null);
      this.getContentPane().setBackground(new Color(255, 223, 88));
      this.setBackground(new Color(255, 223, 88));
      this.setIconImage(Toolkit.getDefaultToolkit().getImage(LoginForm.class.getResource("/Resources/FitFlowIconPngResized.png")));
      this.getContentPane().setLayout((LayoutManager)null);
      JLabel loginImage = new JLabel();
      loginImage.setBounds(317, 5, 350, 600);
      loginImage.setIcon(new ImageIcon(LoginForm.class.getResource("/Resources/idolsImage.png")));
      this.getContentPane().add(loginImage);
      JLabel loginText = new JLabel("LOGIN TO FITFLOW");
      loginText.setHorizontalAlignment(0);
      loginText.setFont(new Font("Segoe UI Black", 1, 22));
      loginText.setForeground(new Color(255, 215, 0));
      loginText.setBounds(42, 150, 251, 38);
      loginImage.add(loginText);
      JLabel userLabel = new JLabel("Username");
      userLabel.setFont(new Font("Segoe UI", 1, 18));
      userLabel.setForeground(new Color(255, 255, 255));
      userLabel.setBounds(75, 210, 150, 27);
      loginImage.add(userLabel);
      final JTextField txtrUsername = new JTextField();
      txtrUsername.setForeground(Color.WHITE);
      txtrUsername.setToolTipText("Input username");
      txtrUsername.requestFocus();
      txtrUsername.setBorder(new LineBorder(new Color(51, 51, 51), 2, true));
      txtrUsername.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
      txtrUsername.setBackground(Color.DARK_GRAY);
      txtrUsername.setFont(new Font("Segoe UI", 1, 18));
      txtrUsername.setBounds(75, 240, 180, 35);
      loginImage.add(txtrUsername);
      JLabel passLabel = new JLabel("Password");
      passLabel.setFont(new Font("Segoe UI", 1, 18));
      passLabel.setForeground(new Color(255, 255, 255));
      passLabel.setBounds(75, 290, 150, 27);
      loginImage.add(passLabel);
      final JPasswordField userPass = new JPasswordField();
      userPass.setForeground(Color.WHITE);
      userPass.setEchoChar('●');
      userPass.setToolTipText("Input your password");
      userPass.setBackground(Color.DARK_GRAY);
      userPass.setBorder(new LineBorder(new Color(51, 51, 51), 2, true));
      userPass.setFont(new Font("Tahoma", 1, 18));
      userPass.setBounds(75, 320, 180, 35);
      loginImage.add(userPass);
      final JButton loginButton = new JButton("LOGIN");
      loginButton.setBorder(new LineBorder(new Color(51, 51, 51), 2, true));
      txtrUsername.addKeyListener(new KeyAdapter() {
         @Override
		public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == 10) {
               loginButton.doClick();
            }

         }
      });
      userPass.addKeyListener(new KeyAdapter() {
         @Override
		public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == 10) {
               loginButton.doClick();
            }

         }
      });
      loginButton.setForeground(new Color(0, 0, 0));
      loginButton.setBackground(new Color(255, 204, 51));
      loginButton.setFont(new Font("Segoe UI", 1, 13));
      loginButton.setBounds(80, 380, 170, 23);
      loginButton.setBorderPainted(false);
      loginButton.setFocusable(false);
      loginImage.add(loginButton);
      JButton resetButton = new JButton();
      resetButton.setText("RESET");
      resetButton.setForeground(new Color(0, 0, 0));
      resetButton.setBackground(new Color(255, 204, 51));
      resetButton.setFont(new Font("Segoe UI", 1, 13));
      resetButton.setBounds(90, 420, 150, 23);
      resetButton.setFocusable(false);
      resetButton.setBorder(new LineBorder(new Color(51, 51, 51), 2, true));
      resetButton.setBorderPainted(false);
      loginImage.add(resetButton);
      resetButton.addActionListener(new ActionListener() {
         @Override
		public void actionPerformed(ActionEvent e) {
            txtrUsername.setText("");
            userPass.setText("");
         }
      });
      JButton forgotPass = new JButton();
      forgotPass.setForeground(new Color(0, 0, 0));
      forgotPass.setBackground(new Color(255, 215, 0));
      forgotPass.setFont(new Font("Tahoma", 1, 13));
      forgotPass.setBounds(75, 470, 45, 45);
      forgotPass.setFocusable(false);
      forgotPass.setIcon(new ImageIcon(LoginForm.class.getResource("/Resources/forgot-password.png")));
      forgotPass.setOpaque(false);
      forgotPass.setBorderPainted(false);
      forgotPass.setContentAreaFilled(false);
      forgotPass.setFocusPainted(false);
      forgotPass.setOpaque(false);
      loginImage.add(forgotPass);
      JLabel forgotPLabel = new JLabel("Forgot Password");
      forgotPLabel.setFont(new Font("Segoe UI", 1, 14));
      forgotPLabel.setForeground(new Color(255, 255, 255));
      forgotPLabel.setBounds(112, 480, 150, 27);
      loginImage.add(forgotPLabel);
      JButton offlineMode = new JButton();
      offlineMode.addActionListener(new ActionListener() {
         @Override
		public void actionPerformed(ActionEvent e) {
            int conf = JOptionPane.showConfirmDialog((Component)null, "Are you sure you want to switch to Offline mode?", "Confirmation", 0);
            if (conf == 0) {
               new LoginFormOffline();
               LoginForm.this.dispose();
            }

         }
      });
      offlineMode.setForeground(new Color(0, 0, 0));
      offlineMode.setBackground(new Color(255, 215, 0));
      offlineMode.setFont(new Font("Tahoma", 1, 13));
      offlineMode.setBounds(75, 520, 45, 45);
      offlineMode.setFocusable(false);
      offlineMode.setIcon(new ImageIcon(LoginForm.class.getResource("/Resources/no-wifi.png")));
      offlineMode.setOpaque(false);
      offlineMode.setBorderPainted(false);
      offlineMode.setContentAreaFilled(false);
      offlineMode.setFocusPainted(false);
      offlineMode.setOpaque(false);
      loginImage.add(offlineMode);
      JLabel offLabel = new JLabel("Offline Mode");
      offLabel.setFont(new Font("Segoe UI", 1, 14));
      offLabel.setForeground(new Color(255, 255, 255));
      offLabel.setBounds(114, 530, 150, 27);
      loginImage.add(offLabel);
      forgotPass.addActionListener(new ActionListener() {
         @Override
		public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog((Component)null, "Contact the admin for password retrieveal and reset", "Forgot Passowrd", 1);
         }
      });
      final JCheckBox showPassword = new JCheckBox();
      showPassword.setForeground(new Color(255, 255, 255));
      showPassword.setOpaque(false);
      showPassword.setBounds(260, 324, 80, 23);
      showPassword.setFocusable(false);
      showPassword.setIcon(new ImageIcon(AdminFitFlow.class.getResource("/Resources/hidden.png")));
      showPassword.setSelectedIcon(new ImageIcon(AdminFitFlow.class.getResource("/Resources/eye.png")));
      loginImage.add(showPassword);
      JButton feedbackButton = new JButton("");
      feedbackButton.addActionListener(new ActionListener() {
         @Override
		public void actionPerformed(ActionEvent e) {
            String email = "neogenesisgarcia23@gmail.com";
            String name = "Neo Genesis Garcia";
            JOptionPane.showMessageDialog((Component)null, "For Feedbacks and comments, contact " + name + "\n\nEmail: " + email, "Feedback", 1);
         }
      });
      feedbackButton.setIcon(new ImageIcon(LoginForm.class.getResource("/Resources/feedbackblack.png")));
      feedbackButton.setBackground(new Color(255, 223, 88));
      feedbackButton.setBounds(929, 555, 45, 45);
      this.getContentPane().add(feedbackButton);
      JLabel power = new JLabel("Powered by: \nGroup 3 - Lyceum de San Pablo");
      power.setFont(new Font("Franklin Gothic Medium", 3, 15));
      power.setBounds(6, 578, 317, 16);
      this.getContentPane().add(power);
      showPassword.addActionListener(new ActionListener() {
         @Override
		public void actionPerformed(ActionEvent e) {
            if (showPassword.isSelected()) {
               userPass.setEchoChar('\u0000');
            } else {
               userPass.setEchoChar('●');
            }

         }
      });
      loginButton.addActionListener(new ActionListener() {
    	    @Override
			public void actionPerformed(ActionEvent e) {
    	        String username = txtrUsername.getText();
    	        String password = new String(userPass.getPassword());
    	        String query = "SELECT * FROM admincred WHERE username=? AND password=?";

    	        try (Connection connection = ConnectionProvider.getCon();
    	             PreparedStatement ps = connection.prepareStatement(query)) {

    	            ps.setString(1, username);
    	            ps.setString(2, password);

    	            try (ResultSet rs = ps.executeQuery()) {
    	                if (rs.next()) {
    	                    String loggedInUsername = rs.getString("username");
    	                    String userType = rs.getString("userType");
    	                    int loggedInUserId = rs.getInt("id");

    	                    String insertQuery = "INSERT INTO audit_trail (user_id, username, logintime) VALUES (?, ?, NOW())";
    	                    try (PreparedStatement insertLoginTime = connection.prepareStatement(insertQuery)) {
    	                        insertLoginTime.setInt(1, loggedInUserId);
    	                        insertLoginTime.setString(2, loggedInUsername);
    	                        insertLoginTime.executeUpdate();
    	                    }

    	                    JOptionPane.showMessageDialog((Component) null, "Login Successful!");

    	                    int selectedIndex = 0;
    	                    if ("Owner".equals(userType)) {
    	                        new OwnerFitFlow(selectedIndex, loggedInUsername, userType, loggedInUserId).setVisible(true);
    	                    } else if ("Staff".equals(userType)) {
    	                        new StaffFitFlow(selectedIndex, loggedInUsername, userType, loggedInUserId).setVisible(true);
    	                    } else {
    	                        new AdminFitFlow(selectedIndex, loggedInUsername, userType, loggedInUserId).setVisible(true);
    	                    }

    	                    LoginForm.this.dispose();
    	                } else {
    	                    JOptionPane.showMessageDialog((Component) null, "Invalid username or password!", "Login Failed", 0);
    	                }
    	            }
    	        } catch (SQLException var101) {
    	            JOptionPane.showMessageDialog((Component) null, "Database error: " + var101.getMessage(), "Error", 0);
    	        }
    	    }
    	});

      this.setVisible(true);
   }
}