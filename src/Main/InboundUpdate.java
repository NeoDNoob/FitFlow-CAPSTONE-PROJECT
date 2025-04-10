/* Decompiler 21ms, total 360ms, lines 139 */
package Main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;

import com.toedter.calendar.JDateChooser;

import Connectors.ConnectionProvider;

@SuppressWarnings("serial")
public class InboundUpdate extends JFrame {
   private JComboBox<String> DStatusBox;
   private JDateChooser dateChooser;
   private String productId;
   private AdminPOS parentFrame;

   @SuppressWarnings({ "unchecked", "rawtypes" })
public InboundUpdate(String productId, String eta, String status, AdminPOS parentFrame) {
   	setType(Type.UTILITY);
      this.productId = productId;
      this.parentFrame = parentFrame;
      this.getContentPane().setBackground(Color.BLACK);
      this.getContentPane().setLayout((LayoutManager)null);
      this.setSize(545, 223);
      this.setLocationRelativeTo((Component)null);
      this.setIconImage(Toolkit.getDefaultToolkit().getImage(InboundUpdate.class.getResource("/resources/Black_and_Brown_Vintage_Fitness_Center_Logo_-removebg-preview.png")));
      this.setTitle("Update Inbound Product");
      this.setResizable(false);
      JLabel lblUpdateInbound = new JLabel("UPDATE INBOUND");
      lblUpdateInbound.setBounds(203, 11, 145, 26);
      lblUpdateInbound.setForeground(new Color(255, 215, 0));
      lblUpdateInbound.setFont(new Font("Impact", 0, 20));
      this.getContentPane().add(lblUpdateInbound);
      JLabel lblEstimatedTimeOf = new JLabel("Estimated Time of Arrival");
      lblEstimatedTimeOf.setForeground(new Color(255, 215, 0));
      lblEstimatedTimeOf.setFont(new Font("Segoe UI", 1, 18));
      lblEstimatedTimeOf.setBounds(10, 64, 241, 18);
      this.getContentPane().add(lblEstimatedTimeOf);
      this.dateChooser = new JDateChooser();
      this.dateChooser.setForeground(Color.WHITE);
      this.dateChooser.setFont(new Font("Segoe UI", 1, 18));
      this.dateChooser.setBackground(Color.DARK_GRAY);
      this.dateChooser.setBounds(10, 93, 241, 35);
      this.getContentPane().add(this.dateChooser);
      JLabel lblDeliveryStatus = new JLabel("Delivery Status");
      lblDeliveryStatus.setForeground(new Color(255, 215, 0));
      lblDeliveryStatus.setFont(new Font("Segoe UI", 1, 18));
      lblDeliveryStatus.setBounds(280, 64, 141, 18);
      this.getContentPane().add(lblDeliveryStatus);
      this.DStatusBox = new JComboBox(new String[]{"Pending", "Delayed", "Failed", "Shipped", "Completed"});
      this.DStatusBox.setBackground(Color.DARK_GRAY);
      this.DStatusBox.setForeground(Color.WHITE);
      this.DStatusBox.setFont(new Font("Segoe UI", 1, 18));
      this.DStatusBox.setBounds(280, 93, 241, 35);
      this.getContentPane().add(this.DStatusBox);
      JButton updateInbound = new JButton("Update");
      updateInbound.addActionListener((e) -> {
         this.updateInbound();
      });
      updateInbound.setIcon(new ImageIcon(InboundUpdate.class.getResource("/resources/edit.png")));
      updateInbound.setForeground(Color.BLACK);
      updateInbound.setFont(new Font("Segoe UI", 1, 16));
      updateInbound.setBorder(new LineBorder(new Color(51, 51, 51), 2, true));
      updateInbound.setBackground(new Color(255, 204, 51));
      updateInbound.setBounds(203, 143, 121, 30);
      this.getContentPane().add(updateInbound);
      this.prefillFields(eta, status);
   }

   private void prefillFields(String eta, String status) {
      try {
         Date etaDate = (new SimpleDateFormat("yyyy-MM-dd")).parse(eta);
         this.dateChooser.setDate(etaDate);
         this.DStatusBox.setSelectedItem(status);
      } catch (Exception var4) {
         var4.printStackTrace();
         JOptionPane.showMessageDialog(this, "Error pre-filling fields: " + var4.getMessage());
      }

   }

   private void updateInbound() {
	    String sql = "UPDATE inbound SET ETA = ?, status = ? WHERE id = ?";

	    try (Connection con = ConnectionProvider.getCon();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        Date selectedDate = this.dateChooser.getDate();
	        String newETA = new SimpleDateFormat("yyyy-MM-dd").format(selectedDate);
	        String newStatus = (String) this.DStatusBox.getSelectedItem();

	        ps.setString(1, newETA);
	        ps.setString(2, newStatus);
	        ps.setString(3, this.productId);

	        int rowsUpdated = ps.executeUpdate();
	        if (rowsUpdated > 0) {
	            JOptionPane.showMessageDialog(this, "Inbound record updated successfully!");
	            this.parentFrame.populateInboundTable();
	            this.dispose();
	        } else {
	            JOptionPane.showMessageDialog(this, "Failed to update record. Please try again.");
	        }

	    } catch (SQLException var18) {
	        var18.printStackTrace();
	        JOptionPane.showMessageDialog(this, "Error: " + var18.getMessage());
	    }
	}

}