/* Decompiler 203ms, total 1347ms, lines 907 */
package Main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import Connectors.ConnectionProviderOFFLINE;

@SuppressWarnings("serial")
public class StaffPOSOffline extends JFrame implements Runnable {
   private JTable cartTable;
   private DefaultTableModel cartTableModel;
   private JLabel clock;
   @SuppressWarnings("unused")
private int hour;
   @SuppressWarnings("unused")
private int minute;
   @SuppressWarnings("unused")
private int seconds;
   @SuppressWarnings("unused")
private int loggedInUserId;
   private JTextField posBarcodeField;
   private JTextField posChangeField;
   private JTabbedPane POSInvTabbedPane;
   private JTextField posPayField;
   private JTextField posPriceField;
   private JTextField posProductNameField;
   private JTextField posQtyField;
   private JTextField posSTField;
   private DefaultTableModel salesModel;
   private DefaultTableModel tableModel;
   @SuppressWarnings("unused")
private String username;
   @SuppressWarnings("unused")
private String userType;

   public StaffPOSOffline(int selectedIndex, final String username, final String userType, final int loggedInUserId) {
      this.username = username;
      this.userType = userType;
      this.loggedInUserId = loggedInUserId;
      this.setFont(new Font("Segoe UI", 0, 12));
      this.setTitle("FitFlow POS ");
      this.setDefaultCloseOperation(0);
      this.addWindowListener(new WindowAdapter() {
    	    @Override
			public void windowClosing(WindowEvent e) {
    	        String[] options = new String[]{"Exit", "Logout"};
    	        int choice = JOptionPane.showOptionDialog(null, "Do you want to Exit or Logout instead?", "Confirmation",
    	                                                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
    	                                                    null, options, options[0]);

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
    	                            StaffPOSOffline.this.dispose();
    	                        }
    	                    } else {
    	                        JOptionPane.showMessageDialog(null, "Error logging out. No active session found.");
    	                    }
    	                }
    	            } catch (SQLException var18) {
    	                JOptionPane.showMessageDialog(null, "Database error: " + var18.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    	            }
    	        }
    	    }
    	});

      this.setIconImage(Toolkit.getDefaultToolkit().getImage(StaffPOSOffline.class.getResource("/Resources/FitFlowIconPngResized.png")));
      this.getContentPane().setBackground(new Color(255, 223, 88));
      this.getContentPane().setLayout((LayoutManager)null);
      this.setResizable(false);
      this.setSize(1200, 700);
      this.setLocationRelativeTo((Component)null);
      new JTabbedPane(1);
      this.POSInvTabbedPane = new JTabbedPane(2);
      this.POSInvTabbedPane.setTabLayoutPolicy(1);
      this.POSInvTabbedPane.setBorder(UIManager.getBorder("CheckBox.border"));
      this.POSInvTabbedPane.setBounds(7, 58, 1170, 590);
      this.POSInvTabbedPane.setFont(new Font("Segoe UI", 0, 25));
      this.POSInvTabbedPane.setBackground(new Color(153, 204, 204));
      this.POSInvTabbedPane.addChangeListener(new ChangeListener() {
         @Override
		public void stateChanged(ChangeEvent e) {
            JTabbedPane sourceTabbedPane = (JTabbedPane)e.getSource();
            int selectedIndex = sourceTabbedPane.getSelectedIndex();
            if (selectedIndex == 1) {
               StaffPOSOffline.this.clearPosProductFields();
               StaffPOSOffline.this.checkOverstock();
            } else if (selectedIndex != 2 && selectedIndex != 3) {
            }

         }
      });
      this.getContentPane().add(this.POSInvTabbedPane);
      new ImageIcon(StaffPOSOffline.class.getResource("/Resources/POSicon.png"));
      new ImageIcon(ClassLoader.getSystemResource("Resources/minus.png"));
      Thread t = new Thread(this);
      t.start();
      int rowHeight = 30;
      JPanel pos = new JPanel();
      pos.setBorder(new EtchedBorder(1, (Color)null, (Color)null));
      pos.setBackground(new Color(0, 0, 0));
      this.POSInvTabbedPane.addTab("Point of Sale", new ImageIcon(StaffPOSOffline.class.getResource("/Resources/POS.png")), pos, "Point of Sale");
      pos.setLayout((LayoutManager)null);
      JLabel prodIDLabel = new JLabel("Product ID / Barcode");
      prodIDLabel.setForeground(new Color(255, 255, 0));
      prodIDLabel.setBackground(new Color(255, 255, 255));
      prodIDLabel.setFont(new Font("Segoe UI", 1, 15));
      prodIDLabel.setBounds(40, 90, 200, 14);
      pos.add(prodIDLabel);
      this.posBarcodeField = new JTextField();
      this.posBarcodeField.requestFocus();
      this.posBarcodeField.addKeyListener(new KeyAdapter() {
         @Override
		public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == 10) {
               try {
                  Connection con = ConnectionProviderOFFLINE.getCon();
                  Statement st = con.createStatement();
                  ResultSet rs = st.executeQuery("SELECT product_name, retail_price FROM product WHERE barcode = '" + StaffPOSOffline.this.posBarcodeField.getText() + "'");
                  if (rs.next()) {
                     StaffPOSOffline.this.posProductNameField.setText(rs.getString("product_name"));
                     StaffPOSOffline.this.posPriceField.setText(rs.getString("retail_price"));
                  } else {
                     JOptionPane.showMessageDialog((Component)null, "Barcode not found.");
                  }
               } catch (SQLException var5) {
                  JOptionPane.showMessageDialog((Component)null, "Error: " + var5.getMessage());
               }
            }

         }
      });
      this.posBarcodeField.setFont(new Font("Tahoma", 1, 15));
      this.posBarcodeField.setBounds(40, 113, 300, 35);
      pos.add(this.posBarcodeField);
      this.posBarcodeField.setColumns(10);
      JLabel prodNameLabel = new JLabel("Product Name");
      prodNameLabel.setForeground(new Color(255, 255, 0));
      prodNameLabel.setFont(new Font("Segoe UI", 1, 15));
      prodNameLabel.setBounds(40, 170, 200, 14);
      pos.add(prodNameLabel);
      this.posProductNameField = new JTextField();
      this.posProductNameField.setEditable(false);
      this.posProductNameField.setFont(new Font("Tahoma", 1, 15));
      this.posProductNameField.setColumns(10);
      this.posProductNameField.setBounds(40, 191, 300, 35);
      pos.add(this.posProductNameField);
      JLabel prodQuantityLabel = new JLabel("Quantity");
      prodQuantityLabel.setForeground(new Color(255, 255, 0));
      prodQuantityLabel.setFont(new Font("Segoe UI", 1, 15));
      prodQuantityLabel.setBounds(40, 304, 100, 20);
      pos.add(prodQuantityLabel);
      this.posQtyField = new JTextField();
      this.posQtyField.setHorizontalAlignment(0);
      this.posQtyField.setText("0");
      this.posQtyField.setFont(new Font("Tahoma", 1, 15));
      this.posQtyField.setColumns(10);
      this.posQtyField.setBounds(40, 329, 50, 30);
      pos.add(this.posQtyField);
      final JButton posMinusButt = new JButton();
      posMinusButt.setFont(new Font("Segoe UI", 1, 15));
      posMinusButt.addActionListener(new ActionListener() {
         @Override
		public void actionPerformed(ActionEvent e) {
            int currentQty = Integer.parseInt(StaffPOSOffline.this.posQtyField.getText());
            if (currentQty > 0) {
               --currentQty;
               StaffPOSOffline.this.posQtyField.setText(String.valueOf(currentQty));
               if (currentQty == 0) {
                  posMinusButt.setEnabled(false);
               }
            }

         }
      });
      posMinusButt.setIcon(new ImageIcon(StaffPOSOffline.class.getResource("/Resources/minus.png")));
      posMinusButt.setBounds(92, 333, 20, 20);
      posMinusButt.setFocusable(false);
      pos.add(posMinusButt);
      JButton posAddButt = new JButton();
      posAddButt.setFont(new Font("Segoe UI", 1, 15));
      posAddButt.addActionListener(new ActionListener() {
         @Override
		public void actionPerformed(ActionEvent e) {
            int currentQty = Integer.parseInt(StaffPOSOffline.this.posQtyField.getText());
            ++currentQty;
            StaffPOSOffline.this.posQtyField.setText(String.valueOf(currentQty));
            posMinusButt.setEnabled(true);
         }
      });
      posAddButt.setIcon(new ImageIcon(StaffPOSOffline.class.getResource("/Resources/addition.png")));
      posAddButt.setBounds(117, 333, 20, 20);
      posAddButt.setFocusable(false);
      pos.add(posAddButt);
      JButton toCartField = new JButton("Add to Cart");
      toCartField.addActionListener(new ActionListener() {
         @Override
		public void actionPerformed(ActionEvent e) {
            StaffPOSOffline.this.pos();
            StaffPOSOffline.this.clearPosProductFields();
         }
      });
      toCartField.setForeground(new Color(0, 0, 0));
      toCartField.setBackground(new Color(255, 204, 51));
      toCartField.setBorder(new LineBorder(new Color(51, 51, 51), 2, true));
      toCartField.setFont(new Font("Segoe UI", 1, 15));
      toCartField.setIcon(new ImageIcon(StaffPOSOffline.class.getResource("/Resources/add-to-cart.png")));
      toCartField.setBounds(105, 397, 160, 32);
      toCartField.setFocusable(false);
      pos.add(toCartField);
      this.clock = new JLabel();
      this.clock.setBackground(new Color(0, 0, 0));
      this.clock.setForeground(new Color(255, 255, 0));
      this.clock.setFont(new Font("Segoe UI", 1, 55));
      this.clock.setBounds(44, 458, 350, 100);
      (new Thread(this::run)).start();
      pos.add(this.clock);
      JLabel POSLabel = new JLabel("Point of Sale");
      POSLabel.setIcon(new ImageIcon(StaffPOSOffline.class.getResource("/Resources/payment-terminal(1).png")));
      POSLabel.setForeground(new Color(255, 215, 0));
      POSLabel.setFont(new Font("Segoe UI", 1, 15));
      POSLabel.setBounds(1000, 0, 340, 83);
      pos.add(POSLabel);
      JScrollPane scrollPane = new JScrollPane();
      scrollPane.setFont(new Font("Segoe UI", 0, 11));
      scrollPane.addMouseListener(new MouseAdapter() {
         @Override
		public void mouseClicked(MouseEvent e) {
         }
      });
      scrollPane.setBounds(350, 85, 545, 200);
      pos.add(scrollPane);
      this.cartTableModel = new DefaultTableModel(new String[]{"Barcode", "Product Name", "Price", "Quantity", "Total"}, 0);
      this.cartTable = new JTable();
      this.cartTable.setRowHeight(rowHeight);
      this.cartTable.setSelectionForeground(new Color(0, 0, 0));
      this.cartTable.setSelectionBackground(new Color(255, 255, 153));
      this.cartTable.getTableHeader().setBackground(new Color(255, 255, 102));
      this.cartTable.setBackground(new Color(255, 255, 204));
      this.cartTable.setFont(new Font("Tahoma", 0, 15));
      this.cartTable.setModel(this.cartTableModel);
      this.cartTable.getColumnModel().getColumn(1).setPreferredWidth(77);
      this.cartTable.setSelectionBackground(new Color(255, 255, 153));
      this.cartTable.setBackground(new Color(255, 255, 204));
      scrollPane.setViewportView(this.cartTable);
      JLabel posPriceLabel = new JLabel("Price");
      posPriceLabel.setForeground(new Color(255, 255, 0));
      posPriceLabel.setFont(new Font("Segoe UI", 1, 15));
      posPriceLabel.setBounds(40, 243, 200, 14);
      pos.add(posPriceLabel);
      this.posPriceField = new JTextField();
      this.posPriceField.setHorizontalAlignment(0);
      this.posPriceField.setEditable(false);
      this.posPriceField.setFont(new Font("Tahoma", 1, 15));
      this.posPriceField.setColumns(10);
      this.posPriceField.setBounds(40, 263, 80, 35);
      pos.add(this.posPriceField);
      JLabel posSTLabel = new JLabel("Subtotal");
      posSTLabel.setForeground(new Color(255, 255, 0));
      posSTLabel.setFont(new Font("Segoe UI", 1, 15));
      posSTLabel.setBounds(350, 332, 100, 20);
      pos.add(posSTLabel);
      this.posSTField = new JTextField();
      this.posSTField.setHorizontalAlignment(0);
      this.posSTField.setEditable(false);
      this.posSTField.setFont(new Font("Tahoma", 1, 15));
      this.posSTField.setColumns(10);
      this.posSTField.setBounds(414, 329, 100, 30);
      pos.add(this.posSTField);
      JLabel posPayLabel = new JLabel("Pay");
      posPayLabel.setForeground(new Color(255, 255, 0));
      posPayLabel.setFont(new Font("Segoe UI", 1, 15));
      posPayLabel.setBounds(573, 332, 50, 20);
      pos.add(posPayLabel);
      this.posPayField = new JTextField();
      this.posPayField.setHorizontalAlignment(0);
      this.posPayField.setFont(new Font("Tahoma", 1, 15));
      this.posPayField.setColumns(10);
      this.posPayField.setBounds(604, 329, 100, 30);
      pos.add(this.posPayField);
      JLabel posChangeLabel = new JLabel("Change");
      posChangeLabel.setForeground(new Color(255, 255, 0));
      posChangeLabel.setFont(new Font("Segoe UI", 1, 15));
      posChangeLabel.setBounds(739, 332, 70, 20);
      pos.add(posChangeLabel);
      this.posChangeField = new JTextField();
      this.posChangeField.setHorizontalAlignment(0);
      this.posChangeField.setEditable(false);
      this.posChangeField.setFont(new Font("Tahoma", 1, 15));
      this.posChangeField.setColumns(10);
      this.posChangeField.setBounds(795, 329, 100, 30);
      pos.add(this.posChangeField);
      JButton posDeleteButton = new JButton("Delete");
      posDeleteButton.addActionListener(new ActionListener() {
         @Override
		public void actionPerformed(ActionEvent e) {
         }
      });
      posDeleteButton.addMouseListener(new MouseAdapter() {
         @Override
		public void mouseClicked(MouseEvent e) {
            StaffPOSOffline.this.cartTableModel.removeRow(StaffPOSOffline.this.cartTable.getSelectedRow());
            int sum = 0;

            for(int i = 0; i < StaffPOSOffline.this.cartTable.getRowCount(); ++i) {
               sum += Integer.parseInt(StaffPOSOffline.this.cartTable.getValueAt(i, 4).toString());
            }

            StaffPOSOffline.this.posSTField.setText(Integer.toString(sum));
         }
      });
      posDeleteButton.setIcon(new ImageIcon(AdminPOSOffline.class.getResource("/Resources/trash.png")));
      posDeleteButton.setForeground(Color.BLACK);
      posDeleteButton.setFont(new Font("Segoe UI", 1, 15));
      posDeleteButton.setFocusable(false);
      posDeleteButton.setBackground(new Color(255, 204, 51));
      posDeleteButton.setBorder(new LineBorder(new Color(51, 51, 51), 2, true));
      posDeleteButton.setBounds(463, 420, 160, 32);
      pos.add(posDeleteButton);
      JButton posFInalize = new JButton("Finalize Order");
      posFInalize.addActionListener(new ActionListener() {
         @Override
		public void actionPerformed(ActionEvent e) {
            int pay = Integer.parseInt(StaffPOSOffline.this.posPayField.getText());
            int subtotal = Integer.parseInt(StaffPOSOffline.this.posSTField.getText());
            int balance = pay - subtotal;
            StaffPOSOffline.this.posChangeField.setText(String.valueOf(balance));
            StaffPOSOffline.this.sales(username);
            StaffPOSOffline.this.salesModel.fireTableStructureChanged();
            StaffPOSOffline.this.tableModel.fireTableStructureChanged();
         }
      });
      posFInalize.setIcon(new ImageIcon(AdminPOSOffline.class.getResource("/Resources/bill.png")));
      posFInalize.setForeground(Color.BLACK);
      posFInalize.setFont(new Font("Segoe UI", 1, 15));
      posFInalize.setFocusable(false);
      posFInalize.setBackground(new Color(255, 204, 51));
      posFInalize.setBorder(new LineBorder(new Color(51, 51, 51), 2, true));
      posFInalize.setBounds(735, 420, 160, 32);
      pos.add(posFInalize);
      this.setVisible(true);
      JButton toGMButton = new JButton("Gym Management");
      toGMButton.addActionListener(new ActionListener() {
         @Override
		public void actionPerformed(ActionEvent e) {
            (new StaffFitFlow(0, username, userType, loggedInUserId)).setVisible(true);
            StaffPOSOffline.this.setVisible(false);
         }
      });
      toGMButton.setBackground(new Color(224, 177, 25));
      toGMButton.setIcon(new ImageIcon(StaffPOSOffline.class.getResource("/Resources/weight.png")));
      toGMButton.setFont(new Font("Segoe UI", 1, 17));
      toGMButton.setBounds(924, 0, 250, 45);
      toGMButton.setFocusable(false);
      this.getContentPane().add(toGMButton);
      JLabel userLabel = new JLabel("Logged in as: " + username + " (" + userType + ")");
      userLabel.setFont(new Font("Segoe UI", 1, 18));
      userLabel.setBounds(10, 33, 369, 23);
      this.getContentPane().add(userLabel);
      JLabel power = new JLabel("Powered by: \nGroup 3 - Lyceum de San Pablo");
      power.setFont(new Font("Franklin Gothic Medium", 3, 15));
      power.setBounds(608, 31, 317, 16);
      this.getContentPane().add(power);
   }

   private void checkOverstock() {
      Connection connection = null;
      PreparedStatement pst = null;
      ResultSet rs = null;
      boolean var19 = false;

      label216: {
         label217: {
            try {
               var19 = true;
               connection = ConnectionProviderOFFLINE.getCon();
               String query = "SELECT product_name, invQty, date_last_sold FROM product WHERE invQty > 50 AND (date_last_sold IS NULL OR date_last_sold < NOW() - INTERVAL 2 MONTH)";
               pst = connection.prepareStatement(query);
               rs = pst.executeQuery();
               StringBuilder overstockProducts = new StringBuilder();
               boolean hasOverstock = false;

               while(rs.next()) {
                  hasOverstock = true;
                  String productName = rs.getString("product_name");
                  int quantity = rs.getInt("invQty");
                  Date lastSoldDate = rs.getDate("date_last_sold");
                  if (lastSoldDate == null) {
                     overstockProducts.append(productName).append(" - Quantity: ").append(quantity).append(" (Not sold since last restock)\n");
                  } else {
                     overstockProducts.append(productName).append(" - Quantity: ").append(quantity).append(" - Last Sold: ").append(lastSoldDate.toString()).append("\n");
                  }
               }

               if (hasOverstock) {
                  JOptionPane.showMessageDialog((Component)null, "Overstock Alert:\n" + overstockProducts.toString(), "Overstock Notification", 2);
                  var19 = false;
               } else {
                  var19 = false;
               }
               break label216;
            } catch (SQLException var24) {
               JOptionPane.showMessageDialog((Component)null, "SQL Error: " + var24.getMessage());
               var19 = false;
            } catch (Exception var25) {
               JOptionPane.showMessageDialog((Component)null, "Error: " + var25.getMessage());
               var19 = false;
               break label217;
            } finally {
               if (var19) {
                  try {
                     if (rs != null) {
                        rs.close();
                     }

                     if (pst != null) {
                        pst.close();
                     }

                     if (connection != null) {
                        connection.close();
                     }
                  } catch (SQLException var20) {
                     JOptionPane.showMessageDialog((Component)null, "Error closing Resources: " + var20.getMessage());
                  }

               }
            }

            try {
               if (rs != null) {
                  rs.close();
               }

               if (pst != null) {
                  pst.close();
               }

               if (connection != null) {
                  connection.close();
                  return;
               }
            } catch (SQLException var23) {
               JOptionPane.showMessageDialog((Component)null, "Error closing Resources: " + var23.getMessage());
            }

            return;
         }

         try {
            if (rs != null) {
               rs.close();
            }

            if (pst != null) {
               pst.close();
            }

            if (connection != null) {
               connection.close();
               return;
            }
         } catch (SQLException var22) {
            JOptionPane.showMessageDialog((Component)null, "Error closing Resources: " + var22.getMessage());
         }

         return;
      }

      try {
         if (rs != null) {
            rs.close();
         }

         if (pst != null) {
            pst.close();
         }

         if (connection != null) {
            connection.close();
         }
      } catch (SQLException var21) {
         JOptionPane.showMessageDialog((Component)null, "Error closing Resources: " + var21.getMessage());
      }

   }

   private void clearCartField() {
      this.cartTableModel = (DefaultTableModel)this.cartTable.getModel();
      this.cartTableModel.setRowCount(0);
   }

   private void clearPosProductFields() {
      this.posBarcodeField.setText("");
      this.posProductNameField.setText("");
      this.posQtyField.setText("0");
      this.posPayField.setText("");
      this.posChangeField.setText("");
      this.posPriceField.setText("");
   }

   private void clearPosProductFieldsFinished() {
      this.posBarcodeField.setText("");
      this.posProductNameField.setText("");
      this.posQtyField.setText("0");
      this.posSTField.setText("");
      this.posPayField.setText("");
      this.posChangeField.setText("");
      this.posPriceField.setText("");
   }

   private String generateReferenceNumber(int lastInsertId) {
      Random random = new Random();
      int randomNum = 100000 + random.nextInt(900000);
      return "RF" + randomNum + "-" + lastInsertId;
   }

   private void pos() {
      try {
         Connection con = ConnectionProviderOFFLINE.getCon();
         Statement st = con.createStatement();
         ResultSet rs = st.executeQuery("SELECT * FROM product WHERE barcode = '" + this.posBarcodeField.getText() + "'");

         while(true) {
            while(rs.next()) {
               int qtyCurrent = rs.getInt("invQty");
               double price = Integer.parseInt(this.posPriceField.getText());
               int qtyNew = Integer.parseInt(this.posQtyField.getText());
               int total = (int)(price * qtyNew);
               if (qtyNew >= qtyCurrent) {
                  JOptionPane.showMessageDialog((Component)null, "Product quantity is not enough for the order");
                  JOptionPane.showMessageDialog((Component)null, "Available stock: " + qtyCurrent);
               } else {
                  this.cartTableModel = (DefaultTableModel)this.cartTable.getModel();
                  this.cartTableModel.addRow(new Object[]{this.posBarcodeField.getText(), this.posProductNameField.getText(), this.posPriceField.getText(), this.posQtyField.getText(), total});
                  int sum = 0;

                  for(int i = 0; i < this.cartTable.getRowCount(); ++i) {
                     sum += Integer.parseInt(this.cartTable.getValueAt(i, 4).toString());
                  }

                  this.posSTField.setText(Integer.toString(sum));
                  this.posBarcodeField.setText("");
                  this.posProductNameField.setText("");
                  this.posPriceField.setText("");
                  this.posQtyField.setText("");
               }
            }

            return;
         }
      } catch (SQLException var11) {
         JOptionPane.showMessageDialog((Component)null, "Error: " + var11.getMessage());
      }
   }

   private void printReceipt(final String receiptText) throws PrinterException {
      Printable printable = new Printable() {
         @Override
		public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
            if (pageIndex > 0) {
               return 1;
            } else {
               Graphics2D g2d = (Graphics2D)graphics;
               g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
               g2d.setFont(new Font("Monospaced", 0, 9));
               String[] lines = receiptText.split("\n");
               int y = 10;
               String[] var10 = lines;
               int var9 = lines.length;

               for(int var8 = 0; var8 < var9; ++var8) {
                  String line = var10[var8];
                  g2d.drawString(line, 0, y);
                  y += 12;
               }

               return 0;
            }
         }
      };
      PrinterJob job = PrinterJob.getPrinterJob();
      job.setPrintable(printable);

      try {
         job.print();
      } catch (PrinterException var5) {
         var5.printStackTrace();
         JOptionPane.showMessageDialog((Component)null, "Printing failed: " + var5.getMessage());
      }

   }

   @Override
public void run() {
      while(true) {
         Calendar cal = Calendar.getInstance();
         SimpleDateFormat sdf12 = new SimpleDateFormat("hh:mm:ss a");
         Date dat = cal.getTime();
         String time12 = sdf12.format(dat);
         SwingUtilities.invokeLater(() -> {
            this.clock.setText(time12);
         });

         try {
            Thread.sleep(1000L);
         } catch (InterruptedException var6) {
            var6.printStackTrace();
         }
      }
   }

   private void sales(String username) {
	    LocalDateTime now = LocalDateTime.now();
	    Timestamp timestamp = Timestamp.valueOf(now);
	    java.sql.Date sqlDate = java.sql.Date.valueOf(now.toLocalDate());
	    int subtotal = Integer.parseInt(this.posSTField.getText());
	    int pay = Integer.parseInt(this.posPayField.getText());
	    int balance = Integer.parseInt(this.posChangeField.getText());
	    int lastInsertId = 0;

	    try (Connection connection = ConnectionProviderOFFLINE.getCon()) {
	        String query = "INSERT INTO sales(date, subtotal, pay, balance, user_id, reference_number) VALUES(?, ?, ?, ?, (SELECT id FROM admincred WHERE username = ?), ?)";
	        try (PreparedStatement pstSales = connection.prepareStatement(query, 1)) {
	            pstSales.setTimestamp(1, timestamp);
	            pstSales.setInt(2, subtotal);
	            pstSales.setInt(3, pay);
	            pstSales.setInt(4, balance);
	            pstSales.setString(5, username);

	            String referenceNumber = this.generateReferenceNumber(lastInsertId);
	            pstSales.setString(6, referenceNumber);
	            pstSales.executeUpdate();

	            try (ResultSet generatedKeyResult = pstSales.getGeneratedKeys()) {
	                if (generatedKeyResult.next()) {
	                    lastInsertId = generatedKeyResult.getInt(1);
	                }

	                int rows = this.cartTable.getRowCount();
	                String updateQuery = "UPDATE product SET invQty = invQty - ?, date_last_sold = ? WHERE barcode = ?";
	                try (PreparedStatement pstSalesProduct = connection.prepareStatement(updateQuery)) {
	                    String insertQuery = "INSERT INTO sales_product(sales_id, product_id, sell_price, qty, total, user_id) VALUES(?, ?, ?, ?, ?, (SELECT id FROM admincred WHERE username = ?))";
	                    try (PreparedStatement pstInsert = connection.prepareStatement(insertQuery)) {
	                        StringBuilder receipt = new StringBuilder();
	                        receipt.append("\nIDOL'S FITNESS GYM\n")
	                               .append("M. Leonor St. II-E, SPC\n")
	                               .append("Date: ").append(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\n")
	                               .append("Reference No: ").append(referenceNumber).append("\n")
	                               .append("Cashier: ").append(username).append("\n")
	                               .append("--------------------------------\n")
	                               .append(String.format("%-18s %6s %6s\n", "Item", "Qty", "Total"))
	                               .append("--------------------------------\n");

	                        for (int i = 0; i < rows; i++) {
	                            String product_id = this.cartTable.getValueAt(i, 0).toString();
	                            String product_name = this.cartTable.getValueAt(i, 1).toString();
	                            int result = Integer.parseInt(this.cartTable.getValueAt(i, 3).toString());
	                            int price = Integer.parseInt(this.cartTable.getValueAt(i, 2).toString());
	                            int total = Integer.parseInt(this.cartTable.getValueAt(i, 4).toString());

	                            pstSalesProduct.setInt(1, result);
	                            pstSalesProduct.setDate(2, sqlDate);
	                            pstSalesProduct.setString(3, product_id);
	                            pstSalesProduct.executeUpdate();

	                            pstInsert.setInt(1, lastInsertId);
	                            pstInsert.setString(2, product_id);
	                            pstInsert.setInt(3, price);
	                            pstInsert.setInt(4, result);
	                            pstInsert.setInt(5, total);
	                            pstInsert.setString(6, username);
	                            pstInsert.addBatch();

	                            receipt.append(String.format("%-18s %6d %6d\n", product_name, result, total));
	                        }

	                        receipt.append("--------------------------------\n")
	                               .append(String.format("%-20s %11d\n", "Subtotal", subtotal))
	                               .append(String.format("%-20s %11d\n", "Cash", pay))
	                               .append(String.format("%-20s %11d\n", "Change", balance))
	                               .append("--------------------------------\n")
	                               .append("Thank you for choosing us!\n")
	                               .append("FitFlow System\n")
	                               .append("Group 3 BSIS\n")
	                               .append("Lyceum de San Pablo\n")
	                               .append("\n\n");

	                        pstInsert.executeBatch();

	                        JOptionPane.showMessageDialog(null, "Transaction Record Saved");
	                        this.clearPosProductFields();
	                        this.clearCartField();

	                        JTextArea receiptArea = new JTextArea(receipt.toString());
	                        receiptArea.setEditable(false);
	                        JScrollPane scrollPane = new JScrollPane(receiptArea);
	                        scrollPane.setPreferredSize(new Dimension(100, 265));

	                        Object[] options = new Object[]{"Print Receipt"};
	                        int result = JOptionPane.showOptionDialog(null, scrollPane, "Receipt", -1, -1, null, options, options[0]);
	                        if (result == 0) {
	                            this.printReceipt(receipt.toString());
	                            this.clearPosProductFieldsFinished();
	                        }
	                    }
	                }
	            }
	        }
	    } catch (Exception ex) {
	        JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
	    }
	}

}