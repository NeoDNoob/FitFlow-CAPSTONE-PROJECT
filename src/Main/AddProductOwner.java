/* Decompiler 360ms, total 768ms, lines 297 */
package Main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

import Connectors.ConnectionProvider;

@SuppressWarnings("serial")
public class AddProductOwner extends JFrame {
   private JTextField anpPField;
   private JTextField anpBarcodeField;
   private JTextField anpQtyField;
   private JTextField anpCPField;
   private JTextField retailPriceField;
   private JComboBox<String> existingProdBox;
   private JComboBox<String> DStatusBox;
   private JDateChooser dateChooser;
   private OwnerPOS parentFrame;

   @SuppressWarnings({ "unchecked", "rawtypes" })
public AddProductOwner(OwnerPOS ownerPOS) {
   	setIconImage(Toolkit.getDefaultToolkit().getImage(AddProductOwner.class.getResource("/Resources/FitFlowIconPngResized.png")));
   	setType(Type.UTILITY);
      this.parentFrame = ownerPOS;
      this.setSize(575, 520);
      this.setResizable(false);
      this.setLocationRelativeTo((Component)null);
      this.getContentPane().setBackground(new Color(0, 0, 0));
      this.getContentPane().setLayout((LayoutManager)null);
      JLabel lblNewLabel = new JLabel("ADD NEW PRODUCT");
      lblNewLabel.setForeground(new Color(255, 215, 0));
      lblNewLabel.setFont(new Font("Impact", 0, 20));
      lblNewLabel.setBounds(213, 11, 172, 14);
      this.getContentPane().add(lblNewLabel);
      JLabel anpPNlbl = new JLabel("Product Name");
      anpPNlbl.setForeground(new Color(255, 215, 0));
      anpPNlbl.setFont(new Font("Segoe UI", 1, 18));
      anpPNlbl.setBounds(23, 59, 141, 18);
      this.getContentPane().add(anpPNlbl);
      this.anpPField = new JTextField();
      this.anpPField.setFont(new Font("Segoe UI", 1, 18));
      this.anpPField.setBackground(Color.DARK_GRAY);
      this.anpPField.setForeground(new Color(255, 255, 255));
      this.anpPField.setBounds(23, 84, 241, 35);
      this.getContentPane().add(this.anpPField);
      this.existingProdBox = new JComboBox();
      this.existingProdBox.setForeground(Color.WHITE);
      this.existingProdBox.setFont(new Font("Segoe UI", 1, 18));
      this.existingProdBox.setBackground(Color.DARK_GRAY);
      this.existingProdBox.setBounds(274, 84, 241, 35);
      this.getContentPane().add(this.existingProdBox);
      this.existingProdBox.addActionListener((e) -> {
         this.autofillFields();
      });
      JLabel anpBarcodelbl = new JLabel("Barcode");
      anpBarcodelbl.setForeground(new Color(255, 215, 0));
      anpBarcodelbl.setFont(new Font("Segoe UI", 1, 18));
      anpBarcodelbl.setBounds(23, 147, 141, 18);
      this.getContentPane().add(anpBarcodelbl);
      this.anpBarcodeField = new JTextField();
      this.anpBarcodeField.setFont(new Font("Segoe UI", 1, 18));
      this.anpBarcodeField.setForeground(Color.WHITE);
      this.anpBarcodeField.setBackground(Color.DARK_GRAY);
      this.anpBarcodeField.setBounds(23, 172, 241, 35);
      this.getContentPane().add(this.anpBarcodeField);
      JLabel anpQty = new JLabel("Quantity");
      anpQty.setForeground(new Color(255, 215, 0));
      anpQty.setFont(new Font("Segoe UI", 1, 18));
      anpQty.setBounds(302, 147, 141, 18);
      this.getContentPane().add(anpQty);
      this.anpQtyField = new JTextField();
      this.anpQtyField.setFont(new Font("Segoe UI", 1, 18));
      this.anpQtyField.setForeground(Color.WHITE);
      this.anpQtyField.setBackground(Color.DARK_GRAY);
      this.anpQtyField.setBounds(302, 172, 61, 35);
      this.getContentPane().add(this.anpQtyField);
      JLabel anpCostPriceLbl = new JLabel("Cost Price");
      anpCostPriceLbl.setForeground(new Color(255, 215, 0));
      anpCostPriceLbl.setFont(new Font("Segoe UI", 1, 18));
      anpCostPriceLbl.setBounds(23, 236, 84, 18);
      this.getContentPane().add(anpCostPriceLbl);
      this.anpCPField = new JTextField();
      this.anpCPField.setFont(new Font("Segoe UI", 1, 18));
      this.anpCPField.setForeground(Color.WHITE);
      this.anpCPField.setBackground(Color.DARK_GRAY);
      this.anpCPField.setBounds(23, 262, 241, 35);
      this.getContentPane().add(this.anpCPField);
      JLabel lblRetailPrice = new JLabel("Retail Price");
      lblRetailPrice.setForeground(new Color(255, 215, 0));
      lblRetailPrice.setFont(new Font("Segoe UI", 1, 18));
      lblRetailPrice.setBounds(302, 236, 141, 18);
      this.getContentPane().add(lblRetailPrice);
      this.retailPriceField = new JTextField();
      this.retailPriceField.setFont(new Font("Segoe UI", 1, 18));
      this.retailPriceField.setForeground(Color.WHITE);
      this.retailPriceField.setBackground(Color.DARK_GRAY);
      this.retailPriceField.setBounds(302, 262, 241, 35);
      this.getContentPane().add(this.retailPriceField);
      JLabel lblEstimatedTimeOf = new JLabel("Estimated Time of Arrival");
      lblEstimatedTimeOf.setForeground(new Color(255, 215, 0));
      lblEstimatedTimeOf.setFont(new Font("Segoe UI", 1, 18));
      lblEstimatedTimeOf.setBounds(23, 337, 241, 18);
      this.getContentPane().add(lblEstimatedTimeOf);
      this.dateChooser = new JDateChooser();
      this.dateChooser.setFont(new Font("Segoe UI", 1, 18));
      this.dateChooser.setForeground(Color.WHITE);
      this.dateChooser.setBackground(Color.DARK_GRAY);
      this.dateChooser.setBounds(23, 366, 241, 35);
      this.getContentPane().add(this.dateChooser);
      JLabel lblDeliveryStatus = new JLabel("Delivery Status");
      lblDeliveryStatus.setForeground(new Color(255, 215, 0));
      lblDeliveryStatus.setFont(new Font("Segoe UI", 1, 18));
      lblDeliveryStatus.setBounds(302, 337, 141, 18);
      this.getContentPane().add(lblDeliveryStatus);
      this.DStatusBox = new JComboBox(new String[]{"Pending", "Delayed", "Failed", "Shipped", "Completed"});
      this.DStatusBox.setForeground(Color.WHITE);
      this.DStatusBox.setFont(new Font("Segoe UI", 1, 18));
      this.DStatusBox.setBackground(Color.DARK_GRAY);
      this.DStatusBox.setBounds(302, 366, 241, 35);
      this.getContentPane().add(this.DStatusBox);
      JButton addButton = new JButton("Add");
      addButton.setFont(new Font("Segoe UI", 1, 18));
      addButton.setBackground(new Color(255, 204, 51));
      addButton.setBounds(228, 425, 110, 30);
      this.getContentPane().add(addButton);
      addButton.addActionListener((e) -> {
         this.addProducts();
      });
      this.preloadExistingProducts();
      this.setTitle("Add New Product");
   }

   private void preloadExistingProducts() {
	    try (Connection con = ConnectionProvider.getCon();
	         Statement stmt = con.createStatement();
	         ResultSet rs = stmt.executeQuery("SELECT product_name FROM product")) {

	        while (rs.next()) {
	            this.existingProdBox.addItem(rs.getString("product_name"));
	        }

	    } catch (SQLException e) {
	        e.printStackTrace(); // Log the exception
	    }
	}


   private void autofillFields() {
	    String selectedProduct = (String) this.existingProdBox.getSelectedItem();
	    if (selectedProduct != null && !selectedProduct.isEmpty()) {
	        try (Connection con = ConnectionProvider.getCon();
	             PreparedStatement ps = con.prepareStatement("SELECT * FROM product WHERE product_name = ?")) {

	            ps.setString(1, selectedProduct);

	            try (ResultSet rs = ps.executeQuery()) {
	                if (rs.next()) {
	                    this.anpPField.setText(rs.getString("product_name"));
	                    this.anpBarcodeField.setText(rs.getString("barcode"));
	                    this.anpCPField.setText(String.valueOf(rs.getInt("cost_price")));
	                    this.retailPriceField.setText(String.valueOf(rs.getInt("retail_price")));
	                }
	            }

	        } catch (SQLException e) {
	            e.printStackTrace(); // Log the exception
	        }
	    }
	}


   private void addProducts() {
	    try (Connection con = ConnectionProvider.getCon();
	         PreparedStatement ps = con.prepareStatement(
	                 "INSERT INTO inbound (product_name, barcode, inbQty, cost_price, retail_price, ETA, status) " +
	                 "VALUES (?, ?, ?, ?, ?, ?, ?)")) {

	        // Set parameters
	        ps.setString(1, this.anpPField.getText());
	        ps.setString(2, this.anpBarcodeField.getText());
	        ps.setInt(3, Integer.parseInt(this.anpQtyField.getText()));
	        ps.setDouble(4, Double.parseDouble(this.anpCPField.getText()));
	        ps.setDouble(5, Double.parseDouble(this.retailPriceField.getText()));
	        ps.setDate(6, new Date(this.dateChooser.getDate().getTime()));
	        ps.setString(7, (String) this.DStatusBox.getSelectedItem());

	        // Execute the update
	        int rowsAffected = ps.executeUpdate();

	        if (rowsAffected > 0) {
	            JOptionPane.showMessageDialog(this, "Product added successfully!");
	            this.clearFields();
	            this.parentFrame.populateInboundTable();
	        } else {
	            JOptionPane.showMessageDialog(this, "Failed to add product. Try again.");
	        }

	    } catch (NumberFormatException | SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
	    }
	}


   private void clearFields() {
      this.anpPField.setText("");
      this.anpBarcodeField.setText("");
      this.anpQtyField.setText("");
      this.anpCPField.setText("");
      this.retailPriceField.setText("");
      this.dateChooser.setDate((java.util.Date)null);
      this.DStatusBox.setSelectedIndex(0);
   }
}