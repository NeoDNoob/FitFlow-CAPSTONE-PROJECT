/* Decompiler 2414ms, total 3612ms, lines 3471 */
package Main;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
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
import javax.swing.JToggleButton;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import Connectors.ConnectionProvider;

@SuppressWarnings("serial")
public class AdminPOS extends JFrame implements Runnable {
   private JTextArea analysisTextArea;
   private JTable cartTable;
   private DefaultTableModel cartTableModel;
   private JLabel clock;
   private JPanel contentPanel;
   private JFreeChart dualAxisChart;
   private JPanel history;
   @SuppressWarnings("unused")
private int hour;
   @SuppressWarnings("unused")
private int minute;
   @SuppressWarnings("unused")
private int seconds;
   private JTextField invBarcodeField;
   private JTextField invCpField;
   private JTextField invProductNameField;
   private JTextField invQtyField;
   private JTextField invRpField;
   private JTable invTable;
   private JFreeChart lineChart;
   @SuppressWarnings("unused")
private int loggedInUserId;
   private JFreeChart pieChart;
   private JTextField posBarcodeField;
   private JTextField posChangeField;
   private JTabbedPane POSInvTabbedPane;
   private JTextField posPayField;
   private JTextField posPriceField;
   private JTextField posProductNameField;
   private JTextField posQtyField;
   private JTextField posSTField;
   private JPanel productsPanel;
   private JPanel profitPanel;
   private DefaultTableModel salesModel;
   private JPanel salesOverTimeChartPanel;
   private JPanel salesSummaryPanel;
   private JTable salesTable;
   private String sortByColumn;
   private JComboBox<String> SSsortingComboBox;
   private JComboBox<String> SSsortingComboBoxMonths;
   private JComboBox<String> SSsortingComboBoxYears;
   private DefaultTableModel summaryTableModel;
   private DefaultTableModel tableModel;
   private JLabel totalSalesLabel;
   @SuppressWarnings("unused")
private String username;
   @SuppressWarnings("unused")
private String userType;
   private JLabel totalAmountLabel;
   private JTable paymentsAndSalesTable;
   private JTable inboundTable;
   private DefaultTableModel inboundTableModel;

   public AdminPOS(int selectedIndex, final String username, final String userType, final int loggedInUserId) {
      this.username = username;
      this.userType = userType;
      this.loggedInUserId = loggedInUserId;
      this.setFont(new Font("Segoe UI", 0, 12));
      this.setTitle("FitFlow POS");
      this.setDefaultCloseOperation(0);
      this.addWindowListener(new WindowAdapter() {
    	    @Override
			public void windowClosing(WindowEvent e) {
    	        String[] options = new String[]{"Exit", "Logout"};
    	        int choice = JOptionPane.showOptionDialog((Component) null, "Do you want to Exit or Logout instead?", "Confirmation", 0, 3, (Icon) null, options, (Object) null);

    	        if (choice == 0 || choice == 1) {
    	            String updateQuery = "UPDATE audit_trail SET logouttime = NOW() WHERE user_id = ? AND logouttime IS NULL";

    	            try (Connection connection = ConnectionProvider.getCon();
    	                 PreparedStatement ps = connection.prepareStatement(updateQuery)) {

    	                ps.setInt(1, loggedInUserId);
    	                int rowsUpdated = ps.executeUpdate();

    	                if (rowsUpdated > 0) {
    	                    if (choice == 0) {
    	                        JOptionPane.showMessageDialog((Component) null, "Logout recorded. Exiting application.");
    	                        System.exit(0);
    	                    } else {
    	                        JOptionPane.showMessageDialog((Component) null, "Logout successful!");
    	                        new LoginForm();
    	                        AdminPOS.this.dispose();
    	                    }
    	                } else {
    	                    JOptionPane.showMessageDialog((Component) null, "Error logging out. No active session found.");
    	                }

    	            } catch (SQLException var25) {
    	                JOptionPane.showMessageDialog((Component) null, "Database error: " + var25.getMessage(), "Error", 0);
    	            }
    	        }
    	    }
    	});

      this.setIconImage(Toolkit.getDefaultToolkit().getImage(AdminPOS.class.getResource("/Resources/FitFlowIconPngResized.png")));
      this.getContentPane().setBackground(new Color(255, 223, 88));
      this.getContentPane().setLayout((LayoutManager)null);
      this.setResizable(false);
      this.setSize(1200, 700);
      this.setLocationRelativeTo((Component)null);
      new JTabbedPane(1);
      this.POSInvTabbedPane = new JTabbedPane(2);
      this.POSInvTabbedPane.setTabLayoutPolicy(1);
      this.POSInvTabbedPane.setBorder(new LineBorder(new Color(51, 51, 51), 1, true));
      this.POSInvTabbedPane.setBounds(7, 58, 1170, 590);
      this.POSInvTabbedPane.setFont(new Font("Segoe UI", 0, 25));
      this.POSInvTabbedPane.setBackground(new Color(255, 244, 179));
      this.POSInvTabbedPane.addChangeListener(new ChangeListener() {
         @Override
		public void stateChanged(ChangeEvent e) {
            JTabbedPane sourceTabbedPane = (JTabbedPane)e.getSource();
            int selectedIndex = sourceTabbedPane.getSelectedIndex();
            if (selectedIndex == 1) {
               AdminPOS.this.clearPosProductFields();
               AdminPOS.this.checkOverstock();
               AdminPOS.this.loadTableData();
               AdminPOS.this.populateInboundTable();
            } else if (selectedIndex == 2) {
               AdminPOS.this.populateInboundTable();
               AdminPOS.this.clearInvProductFields();
            } else if (selectedIndex == 3) {
               AdminPOS.this.populateInboundTable();
            }

         }
      });
      this.getContentPane().add(this.POSInvTabbedPane);
      new ImageIcon(AdminPOS.class.getResource("/Resources/POSicon.png"));
      new ImageIcon(ClassLoader.getSystemResource("Resources/minus.png"));
      Thread t = new Thread(this);
      t.start();
      int rowHeight = 30;
      JPanel pos = new JPanel();
      pos.setBorder(new EtchedBorder(1, (Color)null, (Color)null));
      pos.setBackground(new Color(0, 0, 0));
      this.POSInvTabbedPane.addTab("Point of Sale", new ImageIcon(AdminPOS.class.getResource("/Resources/POS.png")), pos, "Point of Sale");
      pos.setLayout((LayoutManager)null);
      JLabel prodIDLabel = new JLabel("Product ID / Barcode");
      prodIDLabel.setForeground(new Color(255, 255, 0));
      prodIDLabel.setBackground(new Color(255, 255, 255));
      prodIDLabel.setFont(new Font("Segoe UI", 1, 15));
      prodIDLabel.setBounds(40, 90, 200, 14);
      pos.add(prodIDLabel);
      this.posBarcodeField = new JTextField();
      this.posBarcodeField.setForeground(Color.WHITE);
      this.posBarcodeField.setBackground(Color.DARK_GRAY);
      this.posBarcodeField.requestFocus();
      this.posBarcodeField.addKeyListener(new KeyAdapter() {
    	    @Override
			public void keyPressed(KeyEvent e) {
    	        if (e.getKeyCode() == 10) {
    	            String barcode = AdminPOS.this.posBarcodeField.getText();
    	            String query = "SELECT product_name, retail_price FROM product WHERE barcode = ?";

    	            try (Connection con = ConnectionProvider.getCon();
    	                 PreparedStatement pstmt = con.prepareStatement(query)) {

    	                pstmt.setString(1, barcode);
    	                try (ResultSet rs = pstmt.executeQuery()) {
    	                    if (rs.next()) {
    	                        AdminPOS.this.posProductNameField.setText(rs.getString("product_name"));
    	                        AdminPOS.this.posPriceField.setText(rs.getString("retail_price"));
    	                        AdminPOS.this.posQtyField.setText("1");
    	                    } else {
    	                        JOptionPane.showMessageDialog((Component) null, "Barcode not found.");
    	                        AdminPOS.this.posBarcodeField.setText("");
    	                    }
    	                }

    	            } catch (SQLException var24) {
    	                JOptionPane.showMessageDialog((Component) null, "Error: " + var24.getMessage());
    	            }
    	        }
    	    }
    	});

      this.posBarcodeField.setFont(new Font("Segoe UI", 1, 15));
      this.posBarcodeField.setBounds(40, 113, 300, 35);
      pos.add(this.posBarcodeField);
      this.posBarcodeField.setColumns(10);
      JLabel prodNameLabel = new JLabel("Product Name");
      prodNameLabel.setForeground(new Color(255, 255, 0));
      prodNameLabel.setFont(new Font("Segoe UI", 1, 15));
      prodNameLabel.setBounds(40, 170, 200, 14);
      pos.add(prodNameLabel);
      this.posProductNameField = new JTextField();
      this.posProductNameField.setForeground(Color.WHITE);
      this.posProductNameField.setBackground(Color.DARK_GRAY);
      this.posProductNameField.setEditable(false);
      this.posProductNameField.setFont(new Font("Segoe UI", 1, 15));
      this.posProductNameField.setColumns(10);
      this.posProductNameField.setBounds(40, 191, 300, 35);
      pos.add(this.posProductNameField);
      JLabel prodQuantityLabel = new JLabel("Quantity");
      prodQuantityLabel.setForeground(new Color(255, 255, 0));
      prodQuantityLabel.setFont(new Font("Segoe UI", 1, 15));
      prodQuantityLabel.setBounds(40, 304, 100, 20);
      pos.add(prodQuantityLabel);
      this.posQtyField = new JTextField();
      this.posQtyField.setForeground(Color.WHITE);
      this.posQtyField.setBackground(Color.DARK_GRAY);
      this.posQtyField.setHorizontalAlignment(0);
      this.posQtyField.setText("0");
      this.posQtyField.setFont(new Font("Segoe UI", 1, 15));
      this.posQtyField.setColumns(10);
      this.posQtyField.setBounds(40, 329, 50, 30);
      pos.add(this.posQtyField);
      final JButton posMinusButt = new JButton();
      posMinusButt.setFont(new Font("Segoe UI", 1, 15));
      posMinusButt.addActionListener(new ActionListener() {
         @Override
		public void actionPerformed(ActionEvent e) {
            int currentQty = Integer.parseInt(AdminPOS.this.posQtyField.getText());
            if (currentQty > 0) {
               --currentQty;
               AdminPOS.this.posQtyField.setText(String.valueOf(currentQty));
               if (currentQty == 0) {
                  posMinusButt.setEnabled(false);
               }
            }

         }
      });
      posMinusButt.setIcon(new ImageIcon(AdminPOS.class.getResource("/Resources/minus.png")));
      posMinusButt.setBounds(92, 333, 20, 20);
      posMinusButt.setFocusable(false);
      pos.add(posMinusButt);
      JButton posAddButt = new JButton();
      posAddButt.setFont(new Font("Segoe UI", 1, 15));
      posAddButt.addActionListener(new ActionListener() {
         @Override
		public void actionPerformed(ActionEvent e) {
            int currentQty = Integer.parseInt(AdminPOS.this.posQtyField.getText());
            ++currentQty;
            AdminPOS.this.posQtyField.setText(String.valueOf(currentQty));
            posMinusButt.setEnabled(true);
         }
      });
      posAddButt.setIcon(new ImageIcon(AdminPOS.class.getResource("/Resources/addition.png")));
      posAddButt.setBounds(117, 333, 20, 20);
      posAddButt.setFocusable(false);
      pos.add(posAddButt);
      JButton toCartField = new JButton("Add to Cart");
      toCartField.addActionListener(new ActionListener() {
         @Override
		public void actionPerformed(ActionEvent e) {
            AdminPOS.this.pos();
            AdminPOS.this.clearPosProductFields();
         }
      });
      toCartField.setForeground(new Color(0, 0, 0));
      toCartField.setBackground(new Color(255, 204, 51));
      toCartField.setBorder(new LineBorder(new Color(51, 51, 51), 2, true));
      toCartField.setFont(new Font("Segoe UI", 1, 15));
      toCartField.setIcon(new ImageIcon(AdminPOS.class.getResource("/Resources/add-to-cart.png")));
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
      POSLabel.setIcon(new ImageIcon(AdminPOS.class.getResource("/Resources/payment-terminal(1).png")));
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
      JTableHeader cartTableHead = this.cartTable.getTableHeader();
      cartTableHead.setBackground(new Color(255, 204, 51));
      cartTableHead.setFont(new Font("Segoe UI", 1, 12));
      this.cartTable.setGridColor(new Color(211, 211, 211));
      this.cartTable.setRowHeight(rowHeight);
      this.cartTable.setSelectionForeground(new Color(0, 0, 0));
      this.cartTable.setSelectionBackground(new Color(255, 255, 153));
      this.cartTable.setFont(new Font("Tahoma", 0, 15));
      this.cartTable.setModel(this.cartTableModel);
      this.cartTable.getColumnModel().getColumn(1).setPreferredWidth(77);
      this.cartTable.setSelectionBackground(new Color(255, 255, 153));
      this.cartTable.setBackground(new Color(255, 255, 255));
      scrollPane.setViewportView(this.cartTable);
      JLabel posPriceLabel = new JLabel("Price");
      posPriceLabel.setForeground(new Color(255, 255, 0));
      posPriceLabel.setFont(new Font("Segoe UI", 1, 15));
      posPriceLabel.setBounds(40, 243, 200, 14);
      pos.add(posPriceLabel);
      this.posPriceField = new JTextField();
      this.posPriceField.setForeground(Color.WHITE);
      this.posPriceField.setBackground(Color.DARK_GRAY);
      this.posPriceField.setHorizontalAlignment(0);
      this.posPriceField.setEditable(false);
      this.posPriceField.setFont(new Font("Segoe UI", 1, 15));
      this.posPriceField.setColumns(10);
      this.posPriceField.setBounds(40, 263, 80, 35);
      pos.add(this.posPriceField);
      JLabel posSTLabel = new JLabel("Subtotal");
      posSTLabel.setForeground(new Color(255, 255, 0));
      posSTLabel.setFont(new Font("Segoe UI", 1, 15));
      posSTLabel.setBounds(350, 332, 100, 20);
      pos.add(posSTLabel);
      this.posSTField = new JTextField();
      this.posSTField.setForeground(Color.WHITE);
      this.posSTField.setBackground(Color.DARK_GRAY);
      this.posSTField.setHorizontalAlignment(0);
      this.posSTField.setEditable(false);
      this.posSTField.setFont(new Font("Segoe UI", 1, 15));
      this.posSTField.setColumns(10);
      this.posSTField.setBounds(414, 329, 100, 30);
      pos.add(this.posSTField);
      JLabel posPayLabel = new JLabel("Cash");
      posPayLabel.setForeground(new Color(255, 255, 0));
      posPayLabel.setFont(new Font("Segoe UI", 1, 15));
      posPayLabel.setBounds(566, 333, 50, 20);
      pos.add(posPayLabel);
      this.posPayField = new JTextField();
      this.posPayField.setForeground(Color.WHITE);
      this.posPayField.setBackground(Color.DARK_GRAY);
      this.posPayField.setHorizontalAlignment(0);
      this.posPayField.setFont(new Font("Segoe UI", 1, 15));
      this.posPayField.setColumns(10);
      this.posPayField.setBounds(604, 329, 100, 30);
      pos.add(this.posPayField);
      JLabel posChangeLabel = new JLabel("Change");
      posChangeLabel.setForeground(new Color(255, 255, 0));
      posChangeLabel.setFont(new Font("Segoe UI", 1, 15));
      posChangeLabel.setBounds(739, 332, 70, 20);
      pos.add(posChangeLabel);
      this.posChangeField = new JTextField();
      this.posChangeField.setForeground(Color.WHITE);
      this.posChangeField.setBackground(Color.DARK_GRAY);
      this.posChangeField.setHorizontalAlignment(0);
      this.posChangeField.setEditable(false);
      this.posChangeField.setFont(new Font("Segoe UI", 1, 15));
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
            AdminPOS.this.cartTableModel.removeRow(AdminPOS.this.cartTable.getSelectedRow());
            int sum = 0;

            for(int i = 0; i < AdminPOS.this.cartTable.getRowCount(); ++i) {
               sum += Integer.parseInt(AdminPOS.this.cartTable.getValueAt(i, 4).toString());
            }

            AdminPOS.this.posSTField.setText(Integer.toString(sum));
         }
      });
      posDeleteButton.setIcon(new ImageIcon(AdminPOS.class.getResource("/Resources/trash.png")));
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
            int pay = Integer.parseInt(AdminPOS.this.posPayField.getText());
            int subtotal = Integer.parseInt(AdminPOS.this.posSTField.getText());
            int balance = pay - subtotal;
            AdminPOS.this.posChangeField.setText(String.valueOf(balance));
            AdminPOS.this.sales(username);
            AdminPOS.this.salesModel.fireTableStructureChanged();
            AdminPOS.this.tableModel.fireTableStructureChanged();
         }
      });
      posFInalize.setIcon(new ImageIcon(AdminPOS.class.getResource("/Resources/bill.png")));
      posFInalize.setForeground(Color.BLACK);
      posFInalize.setFont(new Font("Segoe UI", 1, 15));
      posFInalize.setFocusable(false);
      posFInalize.setBackground(new Color(255, 204, 51));
      posFInalize.setBorder(new LineBorder(new Color(51, 51, 51), 2, true));
      posFInalize.setBounds(735, 420, 160, 32);
      pos.add(posFInalize);
      JPanel inventory = new JPanel();
      inventory.setBackground(new Color(0, 0, 0));
      this.POSInvTabbedPane.addTab("Inventory", new ImageIcon(AdminPOS.class.getResource("/Resources/list.png")), inventory, (String)null);
      inventory.setLayout((LayoutManager)null);
      JLabel inventoryLabel = new JLabel("Inventory");
      inventoryLabel.setIcon(new ImageIcon(AdminPOS.class.getResource("/Resources/shipping.png")));
      inventoryLabel.setForeground(new Color(255, 215, 0));
      inventoryLabel.setFont(new Font("Impact", 3, 47));
      inventoryLabel.setBounds(648, 11, 280, 83);
      inventory.add(inventoryLabel);
      JLabel invProdIDLabel = new JLabel("Product ID / Barcode");
      invProdIDLabel.setForeground(new Color(255, 255, 0));
      invProdIDLabel.setFont(new Font("Tahoma", 1, 15));
      invProdIDLabel.setBackground(Color.WHITE);
      invProdIDLabel.setBounds(31, 93, 200, 14);
      inventory.add(invProdIDLabel);
      this.invBarcodeField = new JTextField();
      this.invBarcodeField.setForeground(Color.WHITE);
      this.invBarcodeField.setBackground(Color.DARK_GRAY);
      this.invBarcodeField.requestFocus();
      this.invBarcodeField.setFont(new Font("Segoe UI", 1, 18));
      this.invBarcodeField.setColumns(10);
      this.invBarcodeField.setBounds(31, 115, 300, 35);
      inventory.add(this.invBarcodeField);
      JLabel invProdNameLabel = new JLabel("Product Name");
      invProdNameLabel.setForeground(new Color(255, 255, 0));
      invProdNameLabel.setFont(new Font("Tahoma", 1, 15));
      invProdNameLabel.setBounds(31, 166, 200, 14);
      inventory.add(invProdNameLabel);
      this.invProductNameField = new JTextField();
      this.invProductNameField.setForeground(Color.WHITE);
      this.invProductNameField.setBackground(Color.DARK_GRAY);
      this.invProductNameField.setFont(new Font("Segoe UI", 1, 18));
      this.invProductNameField.setColumns(10);
      this.invProductNameField.setBounds(31, 187, 300, 35);
      inventory.add(this.invProductNameField);
      JLabel invQuantityLabel = new JLabel("Quantity");
      invQuantityLabel.setForeground(new Color(255, 255, 0));
      invQuantityLabel.setFont(new Font("Tahoma", 1, 15));
      invQuantityLabel.setBounds(31, 235, 100, 20);
      inventory.add(invQuantityLabel);
      this.invQtyField = new JTextField();
      this.invQtyField.setForeground(Color.WHITE);
      this.invQtyField.setBackground(Color.DARK_GRAY);
      this.invQtyField.setHorizontalAlignment(0);
      this.invQtyField.setFont(new Font("Segoe UI", 1, 18));
      this.invQtyField.setText("0");
      this.invQtyField.setColumns(10);
      this.invQtyField.setBounds(31, 260, 50, 30);
      inventory.add(this.invQtyField);
      JLabel invCostPriceLabel = new JLabel("Cost Price");
      invCostPriceLabel.setForeground(new Color(255, 255, 0));
      invCostPriceLabel.setFont(new Font("Tahoma", 1, 15));
      invCostPriceLabel.setBounds(31, 305, 200, 14);
      inventory.add(invCostPriceLabel);
      this.invCpField = new JTextField();
      this.invCpField.setForeground(Color.WHITE);
      this.invCpField.setBackground(Color.DARK_GRAY);
      this.invCpField.setFont(new Font("Segoe UI", 1, 18));
      this.invCpField.setColumns(10);
      this.invCpField.setBounds(31, 325, 150, 35);
      inventory.add(this.invCpField);
      JLabel invRetailPriceLabel = new JLabel("Retail Price");
      invRetailPriceLabel.setForeground(new Color(255, 255, 0));
      invRetailPriceLabel.setFont(new Font("Tahoma", 1, 15));
      invRetailPriceLabel.setBounds(31, 383, 200, 14);
      inventory.add(invRetailPriceLabel);
      this.invRpField = new JTextField();
      this.invRpField.setForeground(Color.WHITE);
      this.invRpField.setBackground(Color.DARK_GRAY);
      this.invRpField.setFont(new Font("Segoe UI", 1, 18));
      this.invRpField.setColumns(10);
      this.invRpField.setBounds(31, 402, 150, 35);
      inventory.add(this.invRpField);
      JButton invAddProd = new JButton("Add ");
      invAddProd.addActionListener(new ActionListener() {
         @Override
		public void actionPerformed(ActionEvent e) {
            AddProduct add = new AddProduct(AdminPOS.this);
            add.setVisible(true);
         }
      });
      invAddProd.setIcon(new ImageIcon(AdminPOS.class.getResource("/Resources/add-small.png")));
      invAddProd.setForeground(Color.BLACK);
      invAddProd.setFont(new Font("Segoe UI", 1, 16));
      invAddProd.setBackground(new Color(255, 204, 51));
      invAddProd.setBorder(new LineBorder(new Color(51, 51, 51), 2, true));
      invAddProd.setBounds(391, 518, 110, 30);
      inventory.add(invAddProd);
      JScrollPane invScrollPane = new JScrollPane();
      invScrollPane.setFont(new Font("Segoe UI", 0, 11));
      invScrollPane.setBackground(new Color(255, 255, 255));
      invScrollPane.setBounds(391, 95, 525, 213);
      inventory.add(invScrollPane);
      this.invTable = new JTable();
      JTableHeader invTableHead = this.invTable.getTableHeader();
      invTableHead.setBackground(new Color(255, 204, 51));
      invTableHead.setFont(new Font("Segoe UI", 1, 12));
      this.invTable.setGridColor(new Color(211, 211, 211));
      this.invTable.setBorder(new BevelBorder(1, (Color)null, (Color)null, (Color)null, (Color)null));
      rowHeight = 30;
      this.invTable.setRowHeight(rowHeight);
      this.invTable.setSelectionForeground(SystemColor.text);
      this.invTable.setSelectionBackground(SystemColor.textHighlight);
      this.invTable.setBackground(new Color(255, 255, 255));
      this.invTable.setFont(new Font("Segoe UI", 0, 15));
      this.loadTableData();
      invScrollPane.setViewportView(this.invTable);
      this.invTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
         @Override
		public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
               int selectedRow = AdminPOS.this.invTable.getSelectedRow();
               if (selectedRow != -1) {
                  AdminPOS.this.invBarcodeField.setText((String)AdminPOS.this.invTable.getValueAt(selectedRow, 1));
                  AdminPOS.this.invProductNameField.setText((String)AdminPOS.this.invTable.getValueAt(selectedRow, 2));
                  AdminPOS.this.invQtyField.setText(String.valueOf(AdminPOS.this.invTable.getValueAt(selectedRow, 3)));
                  AdminPOS.this.invCpField.setText(String.valueOf(AdminPOS.this.invTable.getValueAt(selectedRow, 4)));
                  AdminPOS.this.invRpField.setText(String.valueOf(AdminPOS.this.invTable.getValueAt(selectedRow, 5)));
               }
            }

         }
      });
      JButton invUpdateProduct = new JButton("Update");
      invUpdateProduct.addActionListener(new ActionListener() {
         @Override
		public void actionPerformed(ActionEvent e) {
            int selectedRow = AdminPOS.this.inboundTable.getSelectedRow();
            if (selectedRow == -1) {
               JOptionPane.showMessageDialog(AdminPOS.this, "Please select a row to update.");
            } else {
               String productId = AdminPOS.this.inboundTable.getValueAt(selectedRow, 0).toString();
               String eta = AdminPOS.this.inboundTable.getValueAt(selectedRow, 6).toString();
               String status = AdminPOS.this.inboundTable.getValueAt(selectedRow, 7).toString();
               InboundUpdate updateInboundFrame = new InboundUpdate(productId, eta, status, AdminPOS.this);
               updateInboundFrame.setVisible(true);
            }
         }
      });
      invUpdateProduct.setIcon(new ImageIcon(AdminPOS.class.getResource("/Resources/edit.png")));
      invUpdateProduct.setForeground(Color.BLACK);
      invUpdateProduct.setFont(new Font("Segoe UI", 1, 16));
      invUpdateProduct.setBackground(new Color(255, 204, 51));
      invUpdateProduct.setBorder(new LineBorder(new Color(51, 51, 51), 2, true));
      invUpdateProduct.setBounds(596, 518, 121, 30);
      inventory.add(invUpdateProduct);
      JButton invDeleteProduct = new JButton("Delete ");
      invDeleteProduct.addActionListener(new ActionListener() {
    	    @Override
			public void actionPerformed(ActionEvent e) {
    	        String confirm = JOptionPane.showInputDialog((Component) null, "Please type \"Confirm\" to continue");
    	        if (confirm != null && !confirm.isEmpty()) {
    	            if (confirm.equals("Confirm")) {
    	                int selectedRow = AdminPOS.this.invTable.getSelectedRow();
    	                if (selectedRow != -1) {
    	                    int id = (Integer) AdminPOS.this.invTable.getValueAt(selectedRow, 0);
    	                    String barcode = (String) AdminPOS.this.invTable.getValueAt(selectedRow, 1);
    	                    String deleteQuery = "DELETE FROM product WHERE id = ? AND barcode = ?";

    	                    try (Connection con = ConnectionProvider.getCon();
    	                         PreparedStatement ps = con.prepareStatement(deleteQuery)) {

    	                        ps.setInt(1, id);
    	                        ps.setString(2, barcode);

    	                        int rowsAffected = ps.executeUpdate();
    	                        if (rowsAffected > 0) {
    	                            DefaultTableModel cartTableModel = (DefaultTableModel) AdminPOS.this.invTable.getModel();
    	                            cartTableModel.removeRow(selectedRow);
    	                            JOptionPane.showMessageDialog((Component) null, "Product deleted successfully");
    	                            AdminPOS.this.clearInvProductFields();
    	                        } else {
    	                            JOptionPane.showMessageDialog((Component) null, "No product found with the specified ID and barcode.", "Error", 0);
    	                        }

    	                    } catch (SQLException var28) {
    	                        var28.printStackTrace();
    	                        JOptionPane.showMessageDialog((Component) null, "Database error: " + var28.getMessage(), "Error", 0);
    	                    }
    	                } else {
    	                    JOptionPane.showMessageDialog((Component) null, "No row selected");
    	                }
    	            } else {
    	                JOptionPane.showMessageDialog((Component) null, "Type Confirm Properly");
    	            }
    	        }
    	    }
    	});

      invDeleteProduct.setIcon(new ImageIcon(AdminPOS.class.getResource("/Resources/trash.png")));
      invDeleteProduct.setForeground(Color.BLACK);
      invDeleteProduct.setFont(new Font("Segoe UI", 1, 16));
      invDeleteProduct.setBackground(new Color(255, 204, 51));
      invDeleteProduct.setBorder(new LineBorder(new Color(51, 51, 51), 2, true));
      invDeleteProduct.setFocusable(false);
      invDeleteProduct.setBounds(121, 492, 110, 30);
      inventory.add(invDeleteProduct);
      JLabel inboundlbl = new JLabel("Inbound Products");
      inboundlbl.setForeground(new Color(255, 215, 0));
      inboundlbl.setFont(new Font("Segoe UI", 1, 15));
      inboundlbl.setBounds(391, 325, 150, 14);
      inventory.add(inboundlbl);
      JScrollPane inboundScrollPane = new JScrollPane();
      inboundScrollPane.setBounds(391, 350, 525, 148);
      inventory.add(inboundScrollPane);
      String[] columnNames = new String[]{"ID", "Product Name", "Barcode", "Inbound Qty", "Cost Price", "Retail Price", "ETA", "Status"};
      this.inboundTableModel = new DefaultTableModel(columnNames, 0);
      this.inboundTable = new JTable(this.inboundTableModel);
      JTableHeader inbHead = this.inboundTable.getTableHeader();
      inbHead.setBackground(new Color(255, 204, 51));
      inboundScrollPane.setViewportView(this.inboundTable);
      JButton delivered = new JButton("Delivered");
      delivered.addActionListener(new ActionListener() {
    	    @Override
			public void actionPerformed(ActionEvent e) {
    	        int selectedRow = AdminPOS.this.inboundTable.getSelectedRow();
    	        if (selectedRow == -1) {
    	            JOptionPane.showMessageDialog((Component) null, "Please select a row in the inbound table first.");
    	        } else {
    	            try (Connection con = ConnectionProvider.getCon()) {
    	                if (con != null) {
    	                    String productName = (String) AdminPOS.this.inboundTable.getValueAt(selectedRow, 1);
    	                    String barcode = (String) AdminPOS.this.inboundTable.getValueAt(selectedRow, 2);
    	                    int quantity = Integer.parseInt(AdminPOS.this.inboundTable.getValueAt(selectedRow, 3).toString());
    	                    double costPrice = Double.parseDouble(AdminPOS.this.inboundTable.getValueAt(selectedRow, 4).toString());
    	                    double retailPrice = Double.parseDouble(AdminPOS.this.inboundTable.getValueAt(selectedRow, 5).toString());
    	                    Date etaDate = (Date) AdminPOS.this.inboundTable.getValueAt(selectedRow, 6);
    	                    @SuppressWarnings("unused")
							String status = (String) AdminPOS.this.inboundTable.getValueAt(selectedRow, 7);
    	                    Date today = new Date();

    	                    if (etaDate != null && etaDate.equals(new java.sql.Date(today.getTime()))) {
    	                        int response = JOptionPane.showConfirmDialog((Component) null, "Has the product " + productName + " arrived yet?", "Confirm Arrival", 0);
    	                        if (response != 0) {
    	                            return;
    	                        }
    	                    }

    	                    try (PreparedStatement psCheck = con.prepareStatement("SELECT * FROM product WHERE barcode = ?")) {
    	                        psCheck.setString(1, barcode);
    	                        try (ResultSet rs = psCheck.executeQuery()) {
    	                            if (rs.next()) {
    	                                int currentQty = rs.getInt("invQty");
    	                                int newQty = currentQty + quantity;
    	                                try (PreparedStatement psUpdate = con.prepareStatement("UPDATE product SET invQty = ? WHERE barcode = ?")) {
    	                                    psUpdate.setInt(1, newQty);
    	                                    psUpdate.setString(2, barcode);
    	                                    psUpdate.executeUpdate();
    	                                    JOptionPane.showMessageDialog((Component) null, "Product quantity updated successfully!");
    	                                }
    	                            } else {
    	                                try (PreparedStatement psInsert = con.prepareStatement("INSERT INTO product (product_name, barcode, invQty, cost_price, retail_price, date_last_sold) VALUES (?, ?, ?, ?, ?, NULL)")) {
    	                                    psInsert.setString(1, productName);
    	                                    psInsert.setString(2, barcode);
    	                                    psInsert.setInt(3, quantity);
    	                                    psInsert.setDouble(4, costPrice);
    	                                    psInsert.setDouble(5, retailPrice);
    	                                    psInsert.executeUpdate();
    	                                    JOptionPane.showMessageDialog((Component) null, "New product added to the product table!");
    	                                }
    	                            }
    	                        }
    	                    }

    	                    try (PreparedStatement psDelete = con.prepareStatement("DELETE FROM inbound WHERE barcode = ?")) {
    	                        psDelete.setString(1, barcode);
    	                        psDelete.executeUpdate();
    	                    }

    	                    ((DefaultTableModel) AdminPOS.this.inboundTable.getModel()).removeRow(selectedRow);
    	                    AdminPOS.this.loadTableData();
    	                }
    	            } catch (SQLException var31) {
    	                var31.printStackTrace();
    	                JOptionPane.showMessageDialog((Component) null, "Error: " + var31.getMessage());
    	            } catch (NumberFormatException var32) {
    	                JOptionPane.showMessageDialog((Component) null, "Invalid number format in table data.");
    	            }
    	        }
    	    }
    	});

      delivered.setIcon(new ImageIcon(AdminPOS.class.getResource("/Resources/changes.png")));
      delivered.setForeground(Color.BLACK);
      delivered.setFont(new Font("Segoe UI", 1, 16));
      delivered.setBorder(new LineBorder(new Color(51, 51, 51), 2, true));
      delivered.setBackground(new Color(255, 204, 51));
      delivered.setBounds(795, 518, 121, 30);
      inventory.add(delivered);
      this.populateInboundTable();
      this.history = new JPanel();
      this.history.setBackground(new Color(0, 0, 0));
      this.POSInvTabbedPane.addTab("History", new ImageIcon(AdminPOS.class.getResource("/Resources/file.png")), this.history, (String)null);
      this.history.setLayout((LayoutManager)null);
      JLabel lblHistory = new JLabel("History");
      lblHistory.setIcon(new ImageIcon(AdminPOS.class.getResource("/Resources/filegold.png")));
      lblHistory.setForeground(new Color(255, 215, 0));
      lblHistory.setFont(new Font("Impact", 3, 47));
      lblHistory.setBounds(1090, 0, 340, 83);
      this.history.add(lblHistory);
      this.salesModel = new DefaultTableModel(new String[]{"ID", "Date", "Subtotal", "Pay", "Balance", "Total Sales", "Sold By", "Reference Number"}, 0);
      final JTable SalesHistoryTable = new JTable(this.salesModel);
      JTableHeader SalesHistoryTableHead = SalesHistoryTable.getTableHeader();
      SalesHistoryTableHead.setBackground(new Color(255, 204, 51));
      SalesHistoryTableHead.setFont(new Font("Segoe UI", 1, 12));
      SalesHistoryTable.setGridColor(new Color(211, 211, 211));
      SalesHistoryTable.setBounds(50, 100, 1400, 600);
      SalesHistoryTable.setBackground(Color.WHITE);
      SalesHistoryTable.setForeground(Color.BLACK);
      SalesHistoryTable.setFont(new Font("Arial", 0, 14));
      SalesHistoryTable.setRowHeight(30);
      this.salesModel.fireTableStructureChanged();
      JScrollPane shScrollPane = new JScrollPane(SalesHistoryTable);
      shScrollPane.setFont(new Font("Segoe UI", 0, 11));
      shScrollPane.setBounds(50, 150, 861, 350);
      this.history.add(shScrollPane);
      String[] sortingOptions = new String[]{"Total Sales", "Date"};
      @SuppressWarnings({ "unchecked", "rawtypes" })
	final JComboBox<String> sortComboBox = new JComboBox(sortingOptions);
      sortComboBox.setForeground(Color.WHITE);
      sortComboBox.setBackground(Color.DARK_GRAY);
      sortComboBox.setFont(new Font("Segoe UI", 0, 11));
      sortComboBox.setBounds(50, 50, 200, 30);
      sortComboBox.setBorder(new LineBorder(new Color(51, 51, 51), 2, true));
      this.history.add(sortComboBox);
      String[] sortingOptionsYears = new String[]{"Select Year", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030", "2031", "2032", "2033", "2034"};
      @SuppressWarnings({ "unchecked", "rawtypes" })
	final JComboBox<String> dateSortYears = new JComboBox(sortingOptionsYears);
      dateSortYears.setForeground(Color.WHITE);
      dateSortYears.setBackground(Color.DARK_GRAY);
      dateSortYears.setFont(new Font("Segoe UI", 0, 11));
      dateSortYears.addActionListener(new ActionListener() {
         @Override
		public void actionPerformed(ActionEvent e) {
            String selectedYear = (String)dateSortYears.getSelectedItem();
            if (!selectedYear.equals("Select Year")) {
               AdminPOS.this.populateYearlySales(selectedYear);
            }

         }
      });
      dateSortYears.setVisible(false);
      dateSortYears.setBorder(new LineBorder(new Color(51, 51, 51), 2, true));
      dateSortYears.setBounds(577, 50, 115, 30);
      this.history.add(dateSortYears);
      String[] sortingOptionsMonths = new String[]{"Select Month", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
      @SuppressWarnings({ "unchecked", "rawtypes" })
	final JComboBox<String> dateSortMonths = new JComboBox(sortingOptionsMonths);
      dateSortMonths.setForeground(Color.WHITE);
      dateSortMonths.setBackground(Color.DARK_GRAY);
      dateSortMonths.setFont(new Font("Segoe UI", 0, 11));
      dateSortMonths.setBorder(new LineBorder(new Color(51, 51, 51), 2, true));
      dateSortMonths.addActionListener(new ActionListener() {
         @Override
		public void actionPerformed(ActionEvent e) {
            String selectedMonth = (String)dateSortMonths.getSelectedItem();
            String selectedYear = (String)dateSortYears.getSelectedItem();
            if (!selectedMonth.equals("Select Month") && !selectedYear.equals("Select Year")) {
               int monthNumber = dateSortMonths.getSelectedIndex();
               AdminPOS.this.populateMonthlySales(monthNumber, selectedYear);
            }

         }
      });
      dateSortMonths.setVisible(false);
      dateSortMonths.setBounds(429, 50, 115, 30);
      this.history.add(dateSortMonths);
      String[] sortingOptionsDate = new String[]{"Select", "Today", "This Week", "Monthly", "Yearly"};
      @SuppressWarnings({ "unchecked", "rawtypes" })
	final JComboBox<String> dateSort1 = new JComboBox(sortingOptionsDate);
      dateSort1.setForeground(Color.WHITE);
      dateSort1.setBackground(Color.DARK_GRAY);
      dateSort1.setFont(new Font("Segoe UI", 0, 11));
      dateSort1.setBorder(new LineBorder(new Color(51, 51, 51), 2, true));
      dateSort1.addActionListener(new ActionListener() {
         @Override
		public void actionPerformed(ActionEvent e) {
            String selectedOption = (String)dateSort1.getSelectedItem();
            switch(selectedOption.hashCode()) {
            case -1650694486:
               if (selectedOption.equals("Yearly")) {
                  dateSortMonths.setVisible(false);
                  dateSortYears.setVisible(true);
                  return;
               }
               break;
            case -1393678355:
               if (selectedOption.equals("Monthly")) {
                  dateSortMonths.setVisible(true);
                  dateSortYears.setVisible(true);
                  return;
               }
               break;
            case 80981793:
               if (selectedOption.equals("Today")) {
                  dateSortMonths.setVisible(false);
                  dateSortYears.setVisible(false);
                  AdminPOS.this.populateDailySales();
                  return;
               }
               break;
            case 1384532022:
               if (selectedOption.equals("This Week")) {
                  dateSortMonths.setVisible(false);
                  dateSortYears.setVisible(false);
                  AdminPOS.this.populateWeeklySales();
                  return;
               }
            }

            dateSortMonths.setVisible(false);
            dateSortYears.setVisible(false);
         }
      });
      dateSort1.setVisible(false);
      dateSort1.setBounds(281, 50, 115, 30);
      this.history.add(dateSort1);
      sortComboBox.addActionListener(new ActionListener() {
         @Override
		public void actionPerformed(ActionEvent e) {
            String selectedOption = (String)sortComboBox.getSelectedItem();
            AdminPOS.this.sortByColumn = selectedOption.equals("Total Sales") ? "total_sales_amount" : "s.date";
            dateSort1.setVisible(true);
            if (selectedOption.equals("Total Sales")) {
               dateSort1.setVisible(false);
               dateSortMonths.setVisible(false);
               dateSortYears.setVisible(false);
            } else {
               dateSort1.setVisible(true);
            }

            AdminPOS.this.populateSalesHistory(AdminPOS.this.salesModel, AdminPOS.this.sortByColumn);
            AdminPOS.this.salesModel.fireTableStructureChanged();
         }
      });
      JButton viewDetailsButton = new JButton("View Details");
      viewDetailsButton.setIcon(new ImageIcon(AdminPOS.class.getResource("/Resources/search-file.png")));
      viewDetailsButton.setFont(new Font("Segoe UI", 1, 14));
      viewDetailsButton.setBackground(new Color(255, 204, 51));
      viewDetailsButton.setBorder(new LineBorder(new Color(51, 51, 51), 2, true));
      viewDetailsButton.setBounds(754, 50, 150, 30);
      this.history.add(viewDetailsButton);
      viewDetailsButton.addActionListener(new ActionListener() {
         @Override
		public void actionPerformed(ActionEvent e) {
            int selectedRow = SalesHistoryTable.getSelectedRow();
            if (selectedRow != -1) {
               String salesId = SalesHistoryTable.getValueAt(selectedRow, 0).toString();
               AdminPOS.this.showOrderDetails(salesId);
            } else {
               JOptionPane.showMessageDialog((Component)null, "Please select a row to view details.", "No Row Selected", 2);
            }

         }
      });
      this.populateSalesHistory(this.salesModel, "total_sales_amount");
      this.salesModel.fireTableStructureChanged();
      JPanel report = new JPanel();
      report.setBackground(new Color(0, 0, 0));
      this.POSInvTabbedPane.addTab("Sales Report", new ImageIcon(AdminPOS.class.getResource("/Resources/sales.png")), report, (String)null);
      report.setLayout((LayoutManager)null);
      JLabel lblSalesReport = new JLabel("Sales Report");
      lblSalesReport.setIcon(new ImageIcon(AdminPOS.class.getResource("/Resources/sales-gold.png")));
      lblSalesReport.setForeground(new Color(255, 215, 0));
      lblSalesReport.setFont(new Font("Impact", 3, 47));
      lblSalesReport.setBounds(990, 0, 340, 83);
      report.add(lblSalesReport);
      JPanel sidePanel = new JPanel();
      sidePanel.setLayout((LayoutManager)null);
      sidePanel.setBounds(0, 0, 200, 610);
      sidePanel.setBackground(Color.DARK_GRAY);
      JToggleButton toggleSalesSummary = new JToggleButton("Sales Summary");
      toggleSalesSummary.setFont(new Font("Segoe UI", 0, 13));
      toggleSalesSummary.setBackground(new Color(255, 204, 51));
      toggleSalesSummary.setBorder(new LineBorder(new Color(51, 51, 51), 2, true));
      JToggleButton toggleSalesOverTimeChart = new JToggleButton("Sales Over Time Chart");
      toggleSalesOverTimeChart.setFont(new Font("Segoe UI", 0, 13));
      toggleSalesOverTimeChart.setBackground(new Color(255, 204, 51));
      toggleSalesOverTimeChart.setBorder(new LineBorder(new Color(51, 51, 51), 2, true));
      JToggleButton toggleProductSales = new JToggleButton("Product Sales Chart");
      toggleProductSales.setFont(new Font("Segoe UI", 0, 13));
      toggleProductSales.setBackground(new Color(255, 204, 51));
      toggleProductSales.setBorder(new LineBorder(new Color(51, 51, 51), 2, true));
      JToggleButton toggleProfitMargins = new JToggleButton("Profit Margins Chart");
      toggleProfitMargins.setFont(new Font("Segoe UI", 0, 13));
      toggleProfitMargins.setBackground(new Color(255, 204, 51));
      toggleProfitMargins.setBorder(new LineBorder(new Color(51, 51, 51), 2, true));
      JToggleButton tglbtnOverallGym = new JToggleButton("Overall Gym + Sale Profits");
      tglbtnOverallGym.setFont(new Font("Segoe UI", 0, 13));
      tglbtnOverallGym.setBorder(new LineBorder(new Color(51, 51, 51), 2, true));
      tglbtnOverallGym.setBackground(new Color(255, 204, 51));
      tglbtnOverallGym.setBounds(10, 302, 180, 30);
      sidePanel.add(tglbtnOverallGym);
      ButtonGroup group = new ButtonGroup();
      group.add(toggleSalesSummary);
      group.add(toggleSalesOverTimeChart);
      group.add(toggleProductSales);
      group.add(toggleProfitMargins);
      group.add(tglbtnOverallGym);
      toggleSalesSummary.setBounds(10, 100, 180, 30);
      toggleSalesOverTimeChart.setBounds(10, 150, 180, 30);
      toggleProductSales.setBounds(10, 201, 180, 30);
      toggleProfitMargins.setBounds(10, 250, 180, 30);
      sidePanel.add(toggleSalesSummary);
      sidePanel.add(toggleSalesOverTimeChart);
      sidePanel.add(toggleProductSales);
      sidePanel.add(toggleProfitMargins);
      report.add(sidePanel);
      this.contentPanel = new JPanel();
      this.contentPanel.setLayout(new CardLayout());
      this.contentPanel.setBounds(210, 100, 700, 446);
      report.add(this.contentPanel);
      JButton exportButton = new JButton("Export  to PDF");
      exportButton.setIcon(new ImageIcon(AdminPOS.class.getResource("/Resources/pdf-file.png")));
      exportButton.setForeground(new Color(0, 0, 0));
      exportButton.setBackground(new Color(255, 204, 51));
      exportButton.setBorder(new LineBorder(new Color(51, 51, 51), 2, true));
      exportButton.setFont(new Font("Segoe UI", 1, 14));
      exportButton.setBounds(210, 45, 142, 25);
      exportButton.addActionListener(new ActionListener() {
         @Override
		public void actionPerformed(ActionEvent e) {
            AdminPOS.this.exportToPDF();
         }
      });
      report.add(exportButton);
      this.createSalesSummaryTable();
      this.createSalesOverTimeChart();
      this.createProductSalesChart();
      this.createProfitMarginChart();
      this.createPaymentsAndSalesSummaryTable();
      toggleSalesSummary.addActionListener(new ActionListener() {
         @Override
		public void actionPerformed(ActionEvent e) {
            CardLayout cl = (CardLayout)AdminPOS.this.contentPanel.getLayout();
            cl.show(AdminPOS.this.contentPanel, "Sales Summary");
         }
      });
      toggleSalesOverTimeChart.addActionListener(new ActionListener() {
         @Override
		public void actionPerformed(ActionEvent e) {
            CardLayout cl = (CardLayout)AdminPOS.this.contentPanel.getLayout();
            cl.show(AdminPOS.this.contentPanel, "Sales Over Time Chart");
         }
      });
      toggleProductSales.addActionListener(new ActionListener() {
         @Override
		public void actionPerformed(ActionEvent e) {
            CardLayout cl = (CardLayout)AdminPOS.this.contentPanel.getLayout();
            cl.show(AdminPOS.this.contentPanel, "Product Sales");
         }
      });
      toggleProfitMargins.addActionListener(new ActionListener() {
         @Override
		public void actionPerformed(ActionEvent e) {
            CardLayout cl = (CardLayout)AdminPOS.this.contentPanel.getLayout();
            cl.show(AdminPOS.this.contentPanel, "Profit Margins");
         }
      });
      tglbtnOverallGym.addActionListener(new ActionListener() {
         @Override
		public void actionPerformed(ActionEvent e) {
            CardLayout cl = (CardLayout)AdminPOS.this.contentPanel.getLayout();
            cl.show(AdminPOS.this.contentPanel, "Payments and Sales Summary");
         }
      });
      this.setVisible(true);
      JButton toGMButton = new JButton("Gym Management");
      toGMButton.addActionListener(new ActionListener() {
         @Override
		public void actionPerformed(ActionEvent e) {
            (new AdminFitFlow(0, username, userType, loggedInUserId)).setVisible(true);
            AdminPOS.this.setVisible(false);
         }
      });
      toGMButton.setBackground(new Color(224, 177, 25));
      toGMButton.setIcon(new ImageIcon(AdminPOS.class.getResource("/Resources/weight.png")));
      toGMButton.setFont(new Font("Segoe UI", 1, 17));
      toGMButton.setBounds(924, 2, 250, 45);
      toGMButton.setFocusable(false);
      this.getContentPane().add(toGMButton);
      JLabel userLabel = new JLabel("Logged in as: " + username + " (" + userType + ")");
      userLabel.setForeground(new Color(0, 0, 0));
      userLabel.setFont(new Font("Segoe UI", 1, 18));
      userLabel.setBounds(10, 33, 369, 23);
      this.getContentPane().add(userLabel);
      JLabel power = new JLabel("Powered by: \nGroup 3 - Lyceum de San Pablo");
      power.setFont(new Font("Franklin Gothic Medium", 3, 15));
      power.setBounds(608, 31, 317, 16);
      this.getContentPane().add(power);
   }

   private void addSectionTitle(PDPageContentStream contentStream, String title, float x, float y) throws IOException {
      contentStream.setFont(new PDType1Font(FontName.HELVETICA_BOLD), 14.0F);
      contentStream.beginText();
      contentStream.newLineAtOffset(x, y);
      contentStream.showText(title);
      contentStream.endText();
   }

   private void addTitle(PDPageContentStream contentStream, String title, float x, float y, int fontSize) throws IOException {
      contentStream.setFont(new PDType1Font(FontName.HELVETICA_BOLD), fontSize);
      contentStream.beginText();
      contentStream.newLineAtOffset(x, y);
      contentStream.showText(title);
      contentStream.endText();
   }

   private void checkOverstock() {
      Connection connection = null;
      PreparedStatement pst = null;
      ResultSet rs = null;

      try {
         connection = ConnectionProvider.getCon();
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
            overstockProducts.append(productName).append(" - Quantity: ").append(quantity).append(lastSoldDate == null ? " (Not sold since last restock)\n" : " - Last Sold: " + String.valueOf(lastSoldDate) + "\n");
         }

         if (hasOverstock) {
            this.showOverstockAlert(overstockProducts.toString());
         }
      } catch (SQLException var14) {
         this.showError("SQL Error: " + var14.getMessage());
      } catch (Exception var15) {
         this.showError("Error: " + var15.getMessage());
      } finally {
         this.closeResources(rs, pst, connection);
      }

   }

   private void clearCartField() {
      this.cartTableModel = (DefaultTableModel)this.cartTable.getModel();
      this.cartTableModel.setRowCount(0);
   }

   private void clearInvProductFields() {
      this.invBarcodeField.setText("");
      this.invProductNameField.setText("");
      this.invQtyField.setText("0");
      this.invCpField.setText("");
      this.invRpField.setText("");
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

   private void closeResources(ResultSet rs, PreparedStatement pst, Connection connection) {
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
      } catch (SQLException var5) {
         this.showError("Error closing Resources: " + var5.getMessage());
      }

   }

   private BufferedImage createImageFromPanel(JPanel panel) {
      int width = panel.getWidth();
      int height = panel.getHeight();
      BufferedImage image = new BufferedImage(width, height, 1);
      Graphics2D g2 = image.createGraphics();
      panel.paint(g2);
      g2.dispose();
      return image;
   }

   private void createProductSalesChart() {
	    DefaultPieDataset dataset = new DefaultPieDataset();

	    try (Connection con = ConnectionProvider.getCon()) {
	        if (con != null) {
	            String sql = "SELECT p.product_name, SUM(sp.qty) AS total_sold " +
	                         "FROM sales_product sp " +
	                         "JOIN product p ON sp.product_id = p.barcode " +
	                         "GROUP BY p.product_name " +
	                         "ORDER BY total_sold DESC LIMIT 5";

	            try (PreparedStatement pst = con.prepareStatement(sql);
	                 ResultSet rs = pst.executeQuery()) {

	                while (rs.next()) {
	                    String productName = rs.getString("product_name");
	                    int totalSold = rs.getInt("total_sold");
	                    dataset.setValue(productName, totalSold);
	                }
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    // Create the pie chart
	    this.pieChart = ChartFactory.createPieChart("Most Sold Products", dataset, true, true, false);
	    this.productsPanel = new JPanel(new BorderLayout());
	    ChartPanel chartPanel = new ChartPanel(this.pieChart);
	    chartPanel.setPreferredSize(new Dimension(this.contentPanel.getWidth(), 300));
	    this.productsPanel.add(chartPanel, BorderLayout.CENTER);

	    // Set up the analysis text area and scroll pane
	    this.analysisTextArea = new JTextArea();
	    this.analysisTextArea.setEditable(false);
	    this.analysisTextArea.setLineWrap(true);
	    this.analysisTextArea.setWrapStyleWord(true);
	    this.analysisTextArea.setPreferredSize(new Dimension(this.contentPanel.getWidth(), 100));

	    JScrollPane scrollPane = new JScrollPane(this.analysisTextArea);
	    scrollPane.setPreferredSize(new Dimension(this.contentPanel.getWidth(), 100));
	    scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    this.productsPanel.add(scrollPane, BorderLayout.SOUTH);

	    // Minimum size for the scroll pane
	    scrollPane.setMinimumSize(new Dimension(this.contentPanel.getWidth(), 50));

	    // Add the panel to the content panel and perform analysis
	    this.contentPanel.add(this.productsPanel, "Product Sales");
	    this.performDataAnalysis(dataset, this.analysisTextArea);

	    // Refresh the content panel
	    this.contentPanel.revalidate();
	    this.contentPanel.repaint();
	}


   @SuppressWarnings("deprecation")
   private void createProfitMarginChart() {
	    DefaultCategoryDataset datasetProfit = new DefaultCategoryDataset();
	    DefaultCategoryDataset datasetMargin = new DefaultCategoryDataset();

	    try (Connection con = ConnectionProvider.getCon()) {
	        if (con != null) {
	            String sql = "SELECT p.product_name, SUM(sp.qty) AS total_sold, " +
	                         "SUM(sp.qty * (p.retail_price - p.cost_price)) AS total_profit, " +
	                         "AVG((p.retail_price - p.cost_price) / p.cost_price * 100) AS avg_profit_margin " +
	                         "FROM sales_product sp " +
	                         "JOIN product p ON sp.product_id = p.barcode " +
	                         "GROUP BY p.product_name " +
	                         "ORDER BY total_sold DESC LIMIT 5";

	            try (PreparedStatement pst = con.prepareStatement(sql);
	                 ResultSet rs = pst.executeQuery()) {

	                while (rs.next()) {
	                    String productName = rs.getString("product_name");
	                    double totalProfit = rs.getDouble("total_profit");
	                    double avgProfitMargin = rs.getDouble("avg_profit_margin");

	                    datasetProfit.addValue(totalProfit, "Total Profit", productName);
	                    datasetMargin.addValue(avgProfitMargin, "Avg Profit Margin (%)", productName);
	                }
	            }
	        }
	    } catch (SQLException var57) {
	        var57.printStackTrace();
	    }

	    // Create the chart with the collected data
	    CategoryAxis domainAxis = new CategoryAxis("Products");
	    NumberAxis profitAxis = new NumberAxis("Total Profit ($)");
	    NumberAxis marginAxis = new NumberAxis("Avg Profit Margin (%)");

	    BarRenderer profitRenderer = new BarRenderer();
	    profitRenderer.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());
	    CategoryPlot plot = new CategoryPlot(datasetProfit, domainAxis, profitAxis, profitRenderer);

	    plot.setRangeAxis(1, marginAxis);
	    plot.setDataset(1, datasetMargin);
	    plot.mapDatasetToRangeAxis(1, 1);

	    LineAndShapeRenderer marginRenderer = new LineAndShapeRenderer();
	    marginRenderer.setToolTipGenerator(new StandardCategoryToolTipGenerator());
	    plot.setRenderer(1, marginRenderer);

	    // Set up the chart and panel
	    this.dualAxisChart = new JFreeChart("Profit Margins", JFreeChart.DEFAULT_TITLE_FONT, plot, true);
	    this.profitPanel = new JPanel(new BorderLayout());
	    ChartPanel chartPanel = new ChartPanel(this.dualAxisChart);
	    chartPanel.setPreferredSize(new Dimension(this.contentPanel.getWidth(), 300));
	    this.profitPanel.add(chartPanel, BorderLayout.CENTER);

	    // Set up analysis text area and scroll pane
	    JTextArea analysisTextArea = new JTextArea();
	    analysisTextArea.setEditable(false);
	    analysisTextArea.setLineWrap(true);
	    analysisTextArea.setWrapStyleWord(true);
	    analysisTextArea.setPreferredSize(new Dimension(this.contentPanel.getWidth(), 100));

	    JScrollPane scrollPane = new JScrollPane(analysisTextArea);
	    scrollPane.setPreferredSize(new Dimension(this.contentPanel.getWidth(), 100));
	    this.profitPanel.add(scrollPane, BorderLayout.SOUTH);

	    // Add the panel to the content panel and perform analysis
	    this.contentPanel.add(this.profitPanel, "Profit Margins");
	    this.performProfitMarginAnalysis(datasetProfit, datasetMargin, analysisTextArea);
	    this.contentPanel.revalidate();
	    this.contentPanel.repaint();
	}


   private void createSalesOverTimeChart() {
	    TimeSeries series = new TimeSeries("Sales");

	    try (Connection con = ConnectionProvider.getCon();
	         PreparedStatement pst = con.prepareStatement("SELECT date, subtotal FROM sales ORDER BY date");
	         ResultSet rs = pst.executeQuery()) {

	        // Loop through result set and populate the TimeSeries
	        while (rs.next()) {
	            Day day = new Day(rs.getDate("date"));
	            double subtotal = rs.getDouble("subtotal");
	            if (series.getIndex(day) >= 0) {
	                series.update(day, subtotal);
	            } else {
	                series.add(day, subtotal);
	            }
	        }
	    } catch (SQLException var34) {
	        var34.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Database error: " + var34.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	        return;
	    }

	    // Create the chart with the collected data
	    TimeSeriesCollection dataset = new TimeSeriesCollection(series);
	    this.lineChart = ChartFactory.createTimeSeriesChart(
	        "Sales Over Time", "Date", "Sales Amount", dataset, true, true, false
	    );

	    // Set up chart panel and text area for analysis
	    this.salesOverTimeChartPanel = new JPanel(new BorderLayout());
	    ChartPanel chartPanel = new ChartPanel(this.lineChart);
	    chartPanel.setPreferredSize(new Dimension(this.contentPanel.getWidth(), 300));
	    this.salesOverTimeChartPanel.add(chartPanel, BorderLayout.CENTER);

	    JTextArea analysisTextArea = new JTextArea();
	    analysisTextArea.setEditable(false);
	    analysisTextArea.setLineWrap(true);
	    analysisTextArea.setWrapStyleWord(true);
	    analysisTextArea.setPreferredSize(new Dimension(this.contentPanel.getWidth(), 100));

	    JScrollPane scrollPane = new JScrollPane(analysisTextArea);
	    scrollPane.setPreferredSize(new Dimension(this.contentPanel.getWidth(), 100));
	    this.salesOverTimeChartPanel.add(scrollPane, BorderLayout.SOUTH);

	    // Add the chart panel to the content panel and perform analysis
	    this.contentPanel.add(this.salesOverTimeChartPanel, "Sales Over Time Chart");
	    this.performSalesOverTimeAnalysis(series, analysisTextArea);
	    this.contentPanel.revalidate();
	    this.contentPanel.repaint();
	}

   @SuppressWarnings({ "rawtypes", "unchecked" })
private void createSalesSummaryTable() {
      this.salesSummaryPanel = new JPanel();
      this.salesSummaryPanel.setFont(new Font("Segoe UI", 0, 11));
      this.salesSummaryPanel.setLayout((LayoutManager)null);
      String[] SSsortingOptions = new String[]{"All", "Today", "This Week", "Monthly", "Yearly"};
      this.SSsortingComboBox = new JComboBox(SSsortingOptions);
      this.SSsortingComboBox.setForeground(Color.WHITE);
      this.SSsortingComboBox.setBackground(Color.DARK_GRAY);
      this.SSsortingComboBox.setBorder(new LineBorder(new Color(51, 51, 51), 2, true));
      this.SSsortingComboBox.setFont(new Font("Segoe UI", 0, 11));
      this.SSsortingComboBox.setBounds(10, 10, 90, 25);
      String[] SSsortingOptionsMonths = new String[]{"Select", "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
      this.SSsortingComboBoxMonths = new JComboBox(SSsortingOptionsMonths);
      this.SSsortingComboBoxMonths.setForeground(Color.WHITE);
      this.SSsortingComboBoxMonths.setBackground(Color.DARK_GRAY);
      this.SSsortingComboBoxMonths.setVisible(false);
      this.SSsortingComboBoxMonths.setBorder(new LineBorder(new Color(51, 51, 51), 2, true));
      this.SSsortingComboBoxMonths.setFont(new Font("Segoe UI", 0, 11));
      this.SSsortingComboBoxMonths.setBounds(122, 10, 90, 25);
      String[] SSsortingOptionsYears = new String[]{"Select", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030", "2031", "2032", "2033", "2034", "2035"};
      this.SSsortingComboBoxYears = new JComboBox(SSsortingOptionsYears);
      this.SSsortingComboBoxYears.setForeground(Color.WHITE);
      this.SSsortingComboBoxYears.setBackground(Color.DARK_GRAY);
      this.SSsortingComboBoxYears.setVisible(false);
      this.SSsortingComboBoxYears.setBorder(new LineBorder(new Color(51, 51, 51), 2, true));
      this.SSsortingComboBoxYears.setFont(new Font("Segoe UI", 0, 11));
      this.SSsortingComboBoxYears.setBounds(234, 10, 90, 25);
      this.SSsortingComboBox.addActionListener((e) -> {
         String selectedOption = (String)this.SSsortingComboBox.getSelectedItem();
         this.SSsortingComboBoxMonths.setVisible(selectedOption.equals("Monthly"));
         this.SSsortingComboBoxYears.setVisible(selectedOption.equals("Monthly") || selectedOption.equals("Yearly"));
         this.SSsortingComboBoxMonths.setSelectedIndex(0);
         this.SSsortingComboBoxYears.setSelectedIndex(0);
         this.updateSalesSummary(selectedOption, this.summaryTableModel, this.totalSalesLabel, (String)null, (String)null);
      });
      this.SSsortingComboBoxMonths.addActionListener((e) -> {
         String SSselectedOptionMonths = (String)this.SSsortingComboBoxMonths.getSelectedItem();
         String SSselectedYear = (String)this.SSsortingComboBoxYears.getSelectedItem();
         if (!SSselectedOptionMonths.equals("Select") && !SSselectedYear.equals("Select")) {
            this.updateSalesSummary("Monthly", this.summaryTableModel, this.totalSalesLabel, SSselectedOptionMonths, SSselectedYear);
         }

      });
      this.SSsortingComboBoxYears.addActionListener((e) -> {
         String SSselectedOptionYears = (String)this.SSsortingComboBoxYears.getSelectedItem();
         if (this.SSsortingComboBox.getSelectedItem().equals("Yearly") && !SSselectedOptionYears.equals("Select")) {
            this.updateSalesSummary("Yearly", this.summaryTableModel, this.totalSalesLabel, (String)null, SSselectedOptionYears);
         }

      });
      this.salesSummaryPanel.add(this.SSsortingComboBoxMonths);
      this.salesSummaryPanel.add(this.SSsortingComboBox);
      this.salesSummaryPanel.add(this.SSsortingComboBoxYears);
      this.summaryTableModel = new DefaultTableModel();
      this.salesTable = new JTable(this.summaryTableModel);
      JTableHeader salesTableHead = this.salesTable.getTableHeader();
      salesTableHead.setBackground(new Color(255, 204, 51));
      salesTableHead.setFont(new Font("Segoe UI", 1, 12));
      this.salesTable.setGridColor(new Color(211, 211, 211));
      this.salesTable.setFont(new Font("Segoe UI", 0, 11));
      this.salesTable.setSelectionBackground(SystemColor.textHighlight);
      JScrollPane tableScrollPane = new JScrollPane(this.salesTable);
      tableScrollPane.setBounds(10, 50, 680, 312);
      this.salesSummaryPanel.add(tableScrollPane);
      this.totalSalesLabel = new JLabel("Total Sales: ₱0.00");
      this.totalSalesLabel.setFont(new Font("Segoe UI", 1, 13));
      this.totalSalesLabel.setBounds(540, 10, 150, 25);
      this.salesSummaryPanel.add(this.totalSalesLabel);
      this.contentPanel.add(this.salesSummaryPanel, "Sales Summary");
      this.updateSalesSummary("All", this.summaryTableModel, this.totalSalesLabel, (String)null, (String)null);
   }

   private void exportToPDF() {
	    logPanelSizes();

	    try (PDDocument document = new PDDocument()) {
	        PDPage page = createNewPage(document);
	        PDPageContentStream contentStream = new PDPageContentStream(document, page);

	        addTitle(contentStream, "Sales Report", 25.0F, 750.0F, 20);
	        List<String[]> salesSummaryData = fetchTableData(this.getSalesSummaryTable());
	        List<BufferedImage> chartImages = fetchChartImages();

	        float currentYPosition = 700.0F;
	        currentYPosition -= 40.0F;

	        addSectionWithTitle(contentStream, getSalesSummaryTitle(), currentYPosition);
	        currentYPosition -= 30.0F;

	        addSectionWithTitle(contentStream, getTotalSalesLabel(), currentYPosition);
	        currentYPosition -= 30.0F;

	        writeTableToPDF(contentStream, salesSummaryData, 25.0F, currentYPosition);
	        contentStream.close();

	        if (!chartImages.isEmpty()) {
	            page = createNewPage(document);
	            contentStream = new PDPageContentStream(document, page);
	            addSectionTitle(contentStream, "Charts", 25.0F, 750.0F);
	            currentYPosition = 700.0F;
	            writeChartImagesToPDF(document, contentStream, chartImages, currentYPosition);
	            contentStream.close();
	        }

	        saveAndOpenPDF(document);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	private void logPanelSizes() {
	    logPanelSize("Sales Over Time Panel", this.salesOverTimeChartPanel);
	    logPanelSize("Product Sales Panel", this.productsPanel);
	    logPanelSize("Profit Margins Panel", this.profitPanel);
	}

	private void logPanelSize(String panelName, JPanel panel) {
	    String sizeInfo = (panel == null) ? "null" : "not null, size: " + panel.getSize();
	    System.out.println(panelName + ": " + sizeInfo);
	}

	private PDPage createNewPage(PDDocument document) {
	    PDPage page = new PDPage(PDRectangle.LETTER);
	    document.addPage(page);
	    return page;
	}

	private void addSectionWithTitle(PDPageContentStream contentStream, String title, float currentYPosition) throws IOException {
	    addSectionTitle(contentStream, title, 25.0F, currentYPosition);
	    currentYPosition -= 30.0F;
	}

	private void saveAndOpenPDF(PDDocument document) throws IOException {
	    Path downloadsPath = Paths.get(System.getProperty("user.home"), "Downloads", "SalesReport.pdf");
	    document.save(downloadsPath.toString());
	    JOptionPane.showMessageDialog(null, "PDF exported successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
	    openPDF(downloadsPath.toFile());
	}


   @SuppressWarnings({ "rawtypes", "unchecked" })
private List<BufferedImage> fetchChartImages() {
      List<BufferedImage> images = new ArrayList();
      JPanel salesOverTimeChartPanel = this.getSalesOverTimeChartPanel();
      JPanel productSalesChartPanel = this.getProductSalesChartPanel();
      JPanel profitMarginsChartPanel = this.getProfitMarginsChartPanel();
      images.add(this.createImageFromPanel(salesOverTimeChartPanel));
      images.add(this.createImageFromPanel(productSalesChartPanel));
      images.add(this.createImageFromPanel(profitMarginsChartPanel));
      return images;
   }

   private List<String[]> fetchTableData(JTable table) {
	    Set<String> uniqueData = new HashSet<>();
	    List<String[]> data = new ArrayList<>();

	    // Collect column names
	    int columnCount = table.getColumnCount();
	    String[] columnNames = new String[columnCount];
	    for (int col = 0; col < columnCount; col++) {
	        columnNames[col] = table.getColumnName(col);
	    }
	    data.add(columnNames);

	    // Collect unique row data
	    for (int row = 0; row < table.getRowCount(); row++) {
	        String[] rowData = new String[columnCount];
	        StringBuilder rowDataBuilder = new StringBuilder();

	        for (int col = 0; col < columnCount; col++) {
	            rowData[col] = table.getValueAt(row, col).toString();
	            rowDataBuilder.append(rowData[col]).append(" ");
	        }

	        String rowDataString = rowDataBuilder.toString().trim();
	        if (uniqueData.add(rowDataString)) {
	            data.add(rowData);
	        }
	    }

	    return data;
	}


   private String generateReferenceNumber(int lastInsertId) {
      Random random = new Random();
      int randomNum = 100000 + random.nextInt(900000);
      return "RF" + randomNum + "-" + lastInsertId;
   }

   private int getMonthNumber(String month) {
      switch(month.hashCode()) {
      case 66051:
         if (month.equals("Apr")) {
            return 4;
         }
         break;
      case 66195:
         if (month.equals("Aug")) {
            return 8;
         }
         break;
      case 68578:
         if (month.equals("Dec")) {
            return 12;
         }
         break;
      case 70499:
         if (month.equals("Feb")) {
            return 2;
         }
         break;
      case 74231:
         if (month.equals("Jan")) {
            return 1;
         }
         break;
      case 74849:
         if (month.equals("Jul")) {
            return 7;
         }
         break;
      case 74851:
         if (month.equals("Jun")) {
            return 6;
         }
         break;
      case 77118:
         if (month.equals("Mar")) {
            return 3;
         }
         break;
      case 77125:
         if (month.equals("May")) {
            return 5;
         }
         break;
      case 78517:
         if (month.equals("Nov")) {
            return 11;
         }
         break;
      case 79104:
         if (month.equals("Oct")) {
            return 10;
         }
         break;
      case 83006:
         if (month.equals("Sep")) {
            return 9;
         }
      }

      return -1;
   }

   private JPanel getProductSalesChartPanel() {
      return this.productsPanel;
   }

   private JPanel getProfitMarginsChartPanel() {
      return this.profitPanel;
   }

   private JPanel getSalesOverTimeChartPanel() {
      return this.salesOverTimeChartPanel;
   }

   private JTable getSalesSummaryTable() {
      return this.salesTable;
   }

   private String getSalesSummaryTitle() {
      String salesSummaryTitle = "Sales Summary";
      String dateSortRange = this.SSsortingComboBox.getSelectedItem().toString();
      String selectedYear;
      switch(dateSortRange.hashCode()) {
      case -1650694486:
         if (dateSortRange.equals("Yearly")) {
            selectedYear = this.SSsortingComboBoxYears.getSelectedItem().toString();
            salesSummaryTitle = "Sales Summary for the year of " + selectedYear;
         }
         break;
      case -1393678355:
         if (dateSortRange.equals("Monthly")) {
            String selectedMonth = this.SSsortingComboBoxMonths.getSelectedItem().toString();
            selectedYear = this.SSsortingComboBoxYears.getSelectedItem().toString();
            salesSummaryTitle = "Sales Summary for the month of " + selectedMonth + ", " + selectedYear;
         }
         break;
      case 80981793:
         if (dateSortRange.equals("Today")) {
            salesSummaryTitle = "Sales Summary for Today";
         }
         break;
      case 1384532022:
         if (dateSortRange.equals("This Week")) {
            salesSummaryTitle = "Sales Summary for This Week";
         }
      }

      return salesSummaryTitle;
   }

   private String getTotalSalesLabel() {
      String totalSales = this.totalSalesLabel.getText().replace("₱", "PHP");
      return totalSales;
   }

   private void loadTableData() {
      String[] columnNames = new String[]{"Product ID", "Barcode", "Product Name", "Quantity", "Cost Price", "Retail Price"};
      this.tableModel = new DefaultTableModel(columnNames, 0);
      Connection con = null;
      PreparedStatement ps = null;
      ResultSet rs = null;
      boolean var21 = false;

      label164: {
         try {
            var21 = true;
            con = ConnectionProvider.getCon();
            String query = "SELECT * FROM product";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while(rs.next()) {
               int productId = rs.getInt("id");
               String barcode = rs.getString("barcode");
               String productName = rs.getString("product_name");
               int qty = rs.getInt("invQty");
               int costPrice = rs.getInt("cost_price");
               int retailPrice = rs.getInt("retail_price");
               Object[] row = new Object[]{productId, barcode, productName, qty, costPrice, retailPrice};
               this.tableModel.addRow(row);
               if (qty <= 5) {
                  String notification = "Product: " + productName + " is low on stock";
                  JOptionPane.showMessageDialog((Component)null, notification, "Low Stock Notification", 2);
               }
            }

            this.tableModel.fireTableStructureChanged();
            this.invTable.setModel(this.tableModel);
            var21 = false;
            break label164;
         } catch (SQLException var25) {
            var25.printStackTrace();
            JOptionPane.showMessageDialog((Component)null, "Error loading data from database", "Database Error", 0);
            var21 = false;
         } finally {
            if (var21) {
               try {
                  if (rs != null) {
                     rs.close();
                  }

                  if (ps != null) {
                     ps.close();
                  }

                  if (con != null) {
                     con.close();
                  }
               } catch (SQLException var22) {
                  JOptionPane.showMessageDialog((Component)null, "Error closing Resources: " + var22.getMessage());
               }

            }
         }

         try {
            if (rs != null) {
               rs.close();
            }

            if (ps != null) {
               ps.close();
            }

            if (con != null) {
               con.close();
               return;
            }
         } catch (SQLException var24) {
            JOptionPane.showMessageDialog((Component)null, "Error closing Resources: " + var24.getMessage());
         }

         return;
      }

      try {
         if (rs != null) {
            rs.close();
         }

         if (ps != null) {
            ps.close();
         }

         if (con != null) {
            con.close();
         }
      } catch (SQLException var23) {
         JOptionPane.showMessageDialog((Component)null, "Error closing Resources: " + var23.getMessage());
      }

   }

   private void openPDF(File file) {
      if (Desktop.isDesktopSupported()) {
         try {
            Desktop.getDesktop().open(file);
         } catch (IOException var3) {
            var3.printStackTrace();
         }
      }

   }

   private void performDataAnalysis(DefaultPieDataset dataset, JTextArea analysisTextArea) {
      StringBuilder analysis = new StringBuilder();
      int totalUnitsSold = 0;
      int productCount = dataset.getItemCount();

      int i;
      for(i = 0; i < productCount; ++i) {
         totalUnitsSold += dataset.getValue(i).intValue();
      }

      analysis.append("In total, ").append(totalUnitsSold).append(" units were sold across the top products. ");
      if (productCount > 0) {
         analysis.append("The best-selling products include: ");

         for(i = 0; i < productCount; ++i) {
            String productName = dataset.getKey(i).toString();
            int sold = dataset.getValue(i).intValue();
            double percentage = (double)sold / (double)totalUnitsSold * 100.0D;
            analysis.append(productName).append(" with ").append(sold).append(" units sold, making up ").append(String.format("%.2f", percentage)).append("% of total sales. ");
         }
      } else {
         analysis.append("No product sales data available.");
      }

      analysisTextArea.setText(analysis.toString());
   }

   private void performProfitMarginAnalysis(DefaultCategoryDataset datasetProfit, DefaultCategoryDataset datasetMargin, JTextArea analysisTextArea) {
      StringBuilder analysis = new StringBuilder();
      double totalProfit = 0.0D;
      double totalAvgMargin = 0.0D;
      int productCount = datasetProfit.getColumnCount();

      for(int i = 0; i < productCount; ++i) {
         totalProfit += datasetProfit.getValue(0, i).doubleValue();
         totalAvgMargin += datasetMargin.getValue(0, i).doubleValue();
      }

      analysis.append("The total profit generated from the top products is ").append(String.format("%.2f", totalProfit)).append(". ");
      if (productCount > 0) {
         analysis.append("The average profit margin for these products is ").append(String.format("%.2f", totalAvgMargin / productCount)).append("%. ");
         analysis.append("This indicates a healthy profit margin across the top-selling products. ");
      } else {
         analysis.append("No product profit data available.");
      }

      analysisTextArea.setText(analysis.toString());
   }

   private void performSalesOverTimeAnalysis(TimeSeries series, JTextArea analysisTextArea) {
      StringBuilder analysis = new StringBuilder();
      double totalSales = 0.0D;
      int totalDays = series.getItemCount();

      int i;
      for(i = 0; i < totalDays; ++i) {
         totalSales += series.getValue(i).doubleValue();
      }

      analysis.append("Over the selected period, the total sales amounted to ").append(totalSales).append(". ");
      if (totalDays > 0) {
         analysis.append("Sales trends show that sales figures varied over the days, indicating periods of higher and lower activity. ");

         for(i = 0; i < totalDays; ++i) {
            Day date = (Day)series.getTimePeriod(i);
            double dailySales = series.getValue(i).doubleValue();
            analysis.append("On ").append(date).append(", sales reached ").append(dailySales).append(". ");
         }
      } else {
         analysis.append("No sales data available for the selected period.");
      }

      analysisTextArea.setText(analysis.toString());
   }
   private void populateDailySales() {
	    this.salesModel.fireTableStructureChanged();
	    this.salesModel.setRowCount(0);

	    String query = "SELECT s.id, s.date, s.subtotal, s.pay, s.balance, SUM(sp.total) AS total_sales_amount, a.username, s.reference_number " +
	                   "FROM sales s " +
	                   "INNER JOIN sales_product sp ON s.id = sp.sales_id " +
	                   "INNER JOIN admincred a ON s.user_id = a.id " +
	                   "WHERE DATE(s.date) = DATE(CONVERT_TZ(NOW(), '+00:00', '+08:00')) " +
	                   "GROUP BY s.id, s.date, s.subtotal, s.pay, s.balance, a.username, s.reference_number";

	    System.out.println("Query: " + query);

	    try (Connection conn = ConnectionProvider.getCon();
	         Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery(query)) {

	        while (rs.next()) {
	            Vector<String> row = new Vector<>();
	            row.add(rs.getString("id"));
	            row.add(rs.getString("date"));
	            row.add(rs.getString("subtotal"));
	            row.add(rs.getString("pay"));
	            row.add(rs.getString("balance"));
	            row.add(rs.getString("total_sales_amount"));
	            row.add(rs.getString("username"));
	            row.add(rs.getString("reference_number"));
	            this.salesModel.addRow(row);
	        }

	        System.out.println("Sales data for today populated with " + this.salesModel.getRowCount() + " rows.");

	    } catch (SQLException e) {
	        System.err.println("SQL Error: " + e.getMessage());
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	    } catch (Exception e) {
	        System.err.println("Error: " + e.getMessage());
	        e.printStackTrace();
	    }
	}


   private void populateMonthlySales(int month, String year) {
	    this.salesModel.fireTableStructureChanged();
	    this.salesModel.setRowCount(0);

	    String query = "SELECT s.id, s.date, s.subtotal, s.pay, s.balance, SUM(sp.total) AS total_sales_amount, a.username, s.reference_number " +
	                   "FROM sales s " +
	                   "INNER JOIN sales_product sp ON s.id = sp.sales_id " +
	                   "INNER JOIN admincred a ON s.user_id = a.id " +
	                   "WHERE MONTH(s.date) = ? AND YEAR(s.date) = ? " +
	                   "GROUP BY s.id, s.date, s.subtotal, s.pay, s.balance, a.username, s.reference_number";

	    try (Connection conn = ConnectionProvider.getCon();
	         PreparedStatement stmt = conn.prepareStatement(query)) {

	        stmt.setInt(1, month);
	        stmt.setString(2, year);

	        try (ResultSet rs = stmt.executeQuery()) {
	            while (rs.next()) {
	                Vector<String> row = new Vector<>();
	                row.add(rs.getString("id"));
	                row.add(rs.getString("date"));
	                row.add(rs.getString("subtotal"));
	                row.add(rs.getString("pay"));
	                row.add(rs.getString("balance"));
	                row.add(rs.getString("total_sales_amount"));
	                row.add(rs.getString("username"));
	                row.add(rs.getString("reference_number"));
	                this.salesModel.addRow(row);
	            }
	        }

	        System.out.println("Sales history populated with " + this.salesModel.getRowCount() + " rows.");

	    } catch (SQLException e) {
	        System.err.println("SQL Error: " + e.getMessage());
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	    } catch (Exception e) {
	        System.err.println("Error: " + e.getMessage());
	        e.printStackTrace();
	    }
	}


   private void populateSalesHistory(DefaultTableModel salesModel, String sortByColumn) {
	    System.out.println("Populating sales history...");
	    salesModel.setRowCount(0);

	    String query = "SELECT s.id, s.date, s.subtotal, s.pay, s.balance, SUM(sp.total) AS total_sales_amount, a.username, s.reference_number " +
	                   "FROM sales s " +
	                   "INNER JOIN sales_product sp ON s.id = sp.sales_id " +
	                   "INNER JOIN admincred a ON s.user_id = a.id " +
	                   "GROUP BY s.id, s.date, s.subtotal, s.pay, s.balance, a.username, s.reference_number " +
	                   "ORDER BY " + sortByColumn + " ASC";

	    try (Connection conn = ConnectionProvider.getCon();
	         Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery(query)) {

	        while (rs.next()) {
	            Vector<String> row = new Vector<>();
	            row.add(rs.getString("id"));
	            row.add(rs.getString("date"));
	            row.add(rs.getString("subtotal"));
	            row.add(rs.getString("pay"));
	            row.add(rs.getString("balance"));
	            row.add(rs.getString("total_sales_amount"));
	            row.add(rs.getString("username"));
	            row.add(rs.getString("reference_number"));
	            salesModel.addRow(row);
	        }

	        System.out.println("Sales history populated with " + salesModel.getRowCount() + " rows.");

	    } catch (SQLException e) {
	        System.err.println("SQL Error: " + e.getMessage());
	        e.printStackTrace();
	    } catch (Exception e) {
	        System.err.println("Error: " + e.getMessage());
	        e.printStackTrace();
	    }
	}


   private void populateWeeklySales() {
	    this.salesModel.fireTableStructureChanged();
	    this.salesModel.setRowCount(0);
	    System.out.println("Current Date: " + LocalDate.now());

	    String query = "SELECT s.id, s.date, s.subtotal, s.pay, s.balance, SUM(sp.total) AS total_sales_amount, a.username, s.reference_number " +
	                   "FROM sales s " +
	                   "INNER JOIN sales_product sp ON s.id = sp.sales_id " +
	                   "INNER JOIN admincred a ON s.user_id = a.id " +
	                   "WHERE YEARWEEK(s.date, 1) = YEARWEEK(CURDATE(), 1) " +
	                   "GROUP BY s.id, s.date, s.subtotal, s.pay, s.balance, a.username, s.reference_number";

	    System.out.println("Query: " + query);

	    try (Connection conn = ConnectionProvider.getCon();
	         Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery(query)) {

	        while (rs.next()) {
	            Vector<String> row = new Vector<>();
	            row.add(rs.getString("id"));
	            row.add(rs.getString("date"));
	            row.add(rs.getString("subtotal"));
	            row.add(rs.getString("pay"));
	            row.add(rs.getString("balance"));
	            row.add(rs.getString("total_sales_amount"));
	            row.add(rs.getString("username"));
	            row.add(rs.getString("reference_number"));
	            this.salesModel.addRow(row);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}


   private void populateYearlySales(String year) {
	    this.salesModel.fireTableStructureChanged();
	    this.salesModel.setRowCount(0);
	    System.out.println("Current Date: " + LocalDate.now());

	    String query = "SELECT s.id, s.date, s.subtotal, s.pay, s.balance, SUM(sp.total) AS total_sales_amount, a.username, s.reference_number " +
	                   "FROM sales s " +
	                   "INNER JOIN sales_product sp ON s.id = sp.sales_id " +
	                   "INNER JOIN admincred a ON s.user_id = a.id " +
	                   "WHERE YEAR(s.date) = ? " +
	                   "GROUP BY s.id, s.date, s.subtotal, s.pay, s.balance, a.username, s.reference_number";

	    System.out.println("Query: " + query);

	    try (Connection conn = ConnectionProvider.getCon();
	         PreparedStatement stmt = conn.prepareStatement(query)) {

	        stmt.setString(1, year);

	        try (ResultSet rs = stmt.executeQuery()) {
	            while (rs.next()) {
	                Vector<String> row = new Vector<>();
	                row.add(rs.getString("id"));
	                row.add(rs.getString("date"));
	                row.add(rs.getString("subtotal"));
	                row.add(rs.getString("pay"));
	                row.add(rs.getString("balance"));
	                row.add(rs.getString("total_sales_amount"));
	                row.add(rs.getString("username"));
	                row.add(rs.getString("reference_number"));
	                this.salesModel.addRow(row);
	            }
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}


   private void pos() {
	    try (Connection con = ConnectionProvider.getCon();
	         Statement st = con.createStatement()) {

	        String barcode = this.posBarcodeField.getText();
	        String query = "SELECT * FROM product WHERE barcode = ?";

	        try (PreparedStatement pst = con.prepareStatement(query)) {
	            pst.setString(1, barcode);
	            ResultSet rs = pst.executeQuery();

	            if (rs.next()) {
	                int qtyCurrent = rs.getInt("invQty");
	                double price = Double.parseDouble(this.posPriceField.getText());
	                int qtyNew = Integer.parseInt(this.posQtyField.getText());
	                int total = (int) (price * qtyNew);

	                if (qtyNew > qtyCurrent) {
	                    JOptionPane.showMessageDialog(null, "Product quantity is not enough for the order");
	                    JOptionPane.showMessageDialog(null, "Available stock: " + qtyCurrent);
	                } else {
	                    // Add product to cart table
	                    DefaultTableModel cartTableModel = (DefaultTableModel) this.cartTable.getModel();
	                    cartTableModel.addRow(new Object[]{barcode, this.posProductNameField.getText(), this.posPriceField.getText(), this.posQtyField.getText(), total});

	                    // Update subtotal
	                    int sum = 0;
	                    for (int i = 0; i < this.cartTable.getRowCount(); i++) {
	                        sum += Integer.parseInt(this.cartTable.getValueAt(i, 4).toString());
	                    }
	                    this.posSTField.setText(Integer.toString(sum));

	                    // Clear input fields
	                    this.posBarcodeField.setText("");
	                    this.posProductNameField.setText("");
	                    this.posPriceField.setText("");
	                    this.posQtyField.setText("");
	                }
	            }
	        }
	    } catch (SQLException | NumberFormatException ex) {
	        JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
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

	    try (Connection connection = ConnectionProvider.getCon()) {
	        // Prepare SQL statements
	        try (PreparedStatement pstSales = connection.prepareStatement(
	                "INSERT INTO sales(date, subtotal, pay, balance, user_id, reference_number) VALUES(?, ?, ?, ?, (SELECT id FROM admincred WHERE username = ?), ?)",
	                Statement.RETURN_GENERATED_KEYS);
	             PreparedStatement pstSalesProduct = connection.prepareStatement(
	                "UPDATE product SET invQty = invQty - ?, date_last_sold = ? WHERE barcode = ?");
	             PreparedStatement pstInsert = connection.prepareStatement(
	                "INSERT INTO sales_product(sales_id, product_id, sell_price, qty, total, user_id) VALUES(?, ?, ?, ?, ?, (SELECT id FROM admincred WHERE username = ?))")) {

	            // Insert sales record
	            pstSales.setTimestamp(1, timestamp);
	            pstSales.setInt(2, subtotal);
	            pstSales.setInt(3, pay);
	            pstSales.setInt(4, balance);
	            pstSales.setString(5, username);
	            String referenceNumber = this.generateReferenceNumber(lastInsertId);
	            pstSales.setString(6, referenceNumber);
	            pstSales.executeUpdate();

	            // Get the generated sales ID
	            try (ResultSet generatedKeyResult = pstSales.getGeneratedKeys()) {
	                if (generatedKeyResult.next()) {
	                    lastInsertId = generatedKeyResult.getInt(1);
	                }
	            }

	            // Build receipt
	            StringBuilder receipt = new StringBuilder();
	            receipt.append("\n        IDOL'S FITNESS GYM\n");
	            receipt.append("      M. Leonor St. II-E, SPC\n");
	            receipt.append("    Date: ").append(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\n");
	            receipt.append("    Reference No: ").append(referenceNumber).append("\n");
	            receipt.append("    Cashier: ").append(username).append("\n");
	            receipt.append("--------------------------------\n");
	            receipt.append(String.format("%-18s %6s %6s\n", "Item", "Qty", "Total"));
	            receipt.append("--------------------------------\n");

	            int rows = this.cartTable.getRowCount();
	            for (int i = 0; i < rows; i++) {
	                String productId = this.cartTable.getValueAt(i, 0).toString();
	                String productName = this.cartTable.getValueAt(i, 1).toString();
	                int qty = Integer.parseInt(this.cartTable.getValueAt(i, 3).toString());
	                int price = Integer.parseInt(this.cartTable.getValueAt(i, 2).toString());
	                int total = Integer.parseInt(this.cartTable.getValueAt(i, 4).toString());

	                // Update product inventory
	                pstSalesProduct.setInt(1, qty);
	                pstSalesProduct.setDate(2, sqlDate);
	                pstSalesProduct.setString(3, productId);
	                pstSalesProduct.executeUpdate();

	                // Insert sales product record
	                pstInsert.setInt(1, lastInsertId);
	                pstInsert.setString(2, productId);
	                pstInsert.setInt(3, price);
	                pstInsert.setInt(4, qty);
	                pstInsert.setInt(5, total);
	                pstInsert.setString(6, username);
	                pstInsert.addBatch();

	                // Append product details to receipt
	                receipt.append(String.format("%-18s %6d %6d\n", productName, qty, total));
	            }

	            // Execute batch insert for sales products
	            pstInsert.executeBatch();

	            // Finalize receipt
	            receipt.append("--------------------------------\n");
	            receipt.append(String.format("%-20s %11d\n", "Subtotal", subtotal));
	            receipt.append(String.format("%-20s %11d\n", "Cash", pay));
	            receipt.append(String.format("%-20s %11d\n", "Change", balance));
	            receipt.append("--------------------------------\n");
	            receipt.append("    Thank you for choosing us!\n");
	            receipt.append("         FitFlow System\n");
	            receipt.append("         Group 3 BSIS\n");
	            receipt.append("       Lyceum de San Pablo\n");
	            receipt.append("\n\n");

	            // Show transaction result and print receipt
	            JOptionPane.showMessageDialog(null, "Transaction Record Saved");
	            this.clearPosProductFields();
	            this.clearCartField();
	            JTextArea receiptArea = new JTextArea(receipt.toString());
	            receiptArea.setEditable(false);
	            JScrollPane scrollPane = new JScrollPane(receiptArea);
	            scrollPane.setPreferredSize(new Dimension(100, 265));
	            Object[] options = new Object[]{"Print Receipt"};
	            int result = JOptionPane.showOptionDialog(null, scrollPane, "Receipt", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

	            if (result == 0) {
	                this.printReceipt(receipt.toString());
	                this.clearPosProductFieldsFinished();
	            }

	        } catch (Exception e) {
	            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
	        }
	    } catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}


   private void showError(String message) {
      JOptionPane.showMessageDialog((Component)null, message);
   }

   private void showOrderDetails(String salesId) {
	    JFrame detailsFrame = createDetailsFrame();
	    DefaultTableModel detailsModel = new DefaultTableModel(new String[]{"Product Name", "Barcode", "Sell Price", "Quantity", "Total"}, 0);
	    JTable detailsTable = new JTable(detailsModel);
	    JScrollPane detailsScrollPane = new JScrollPane(detailsTable);
	    detailsFrame.getContentPane().add(detailsScrollPane, BorderLayout.CENTER);

	    // Get order details from the database and populate the table
	    if (populateOrderDetails(salesId, detailsModel)) {
	        detailsFrame.setVisible(true);
	    } else {
	        JOptionPane.showMessageDialog(this, "No details found for the selected order.", "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}

	private JFrame createDetailsFrame() {
	    JFrame detailsFrame = new JFrame("Order Details");
	    detailsFrame.setResizable(false);
	    detailsFrame.setLocationRelativeTo(this);
	    detailsFrame.setSize(800, 400);
	    detailsFrame.getContentPane().setLayout(new BorderLayout());
	    detailsFrame.getContentPane().setBackground(Color.BLACK);
	    return detailsFrame;
	}

	private boolean populateOrderDetails(String salesId, DefaultTableModel detailsModel) {
	    String query = "SELECT p.product_name, sp.product_id, sp.sell_price, sp.qty, sp.total " +
	                   "FROM sales_product sp " +
	                   "JOIN product p ON sp.product_id = p.barcode " +
	                   "WHERE sp.sales_id = ?";

	    try (Connection conn = ConnectionProvider.getCon();
	         PreparedStatement pst = conn.prepareStatement(query)) {

	        pst.setString(1, salesId);

	        try (ResultSet rs = pst.executeQuery()) {
	            boolean hasData = false;
	            while (rs.next()) {
	                Vector<String> row = new Vector<>();
	                row.add(rs.getString("product_name"));
	                row.add(rs.getString("product_id"));
	                row.add(rs.getString("sell_price"));
	                row.add(rs.getString("qty"));
	                row.add(rs.getString("total"));
	                detailsModel.addRow(row);
	                hasData = true;
	            }
	            return hasData;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}


   private void showOverstockAlert(String overstockDetails) {
      JOptionPane.showMessageDialog((Component)null, "Overstock Alert:\n" + overstockDetails, "Overstock Notification", 2);
   }

   private byte[] toByteArray(BufferedImage bi) throws IOException {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      ImageIO.write(bi, "png", baos);
      return baos.toByteArray();
   }

   private void updateSalesSummary(String sortingRange, DefaultTableModel summaryTableModel, JLabel totalSalesLabel, String SSselectedMonth, String SSselectedOptionYears) {
	    String query = getQueryForSortingRange(sortingRange, SSselectedMonth, SSselectedOptionYears);
	    if (query.isEmpty()) {
	        System.out.println("Invalid sorting range: " + sortingRange);
	        return;
	    }

	    try (Connection conn = ConnectionProvider.getCon();
	         Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery(query)) {

	        ResultSetMetaData metaData = rs.getMetaData();
	        int columnCount = metaData.getColumnCount();
	        String[] columnNames = new String[columnCount];
	        for (int i = 0; i < columnCount; i++) {
	            columnNames[i] = metaData.getColumnLabel(i + 1);
	        }

	        summaryTableModel.setColumnIdentifiers(columnNames);
	        summaryTableModel.fireTableStructureChanged();
	        summaryTableModel.setRowCount(0);

	        long totalSales = 0L;
	        while (rs.next()) {
	            Object[] rowData = new Object[columnCount];
	            for (int i = 0; i < columnCount; i++) {
	                rowData[i] = rs.getObject(i + 1);
	            }
	            summaryTableModel.addRow(rowData);
	            totalSales += rs.getLong("total");
	        }

	        totalSalesLabel.setText("Total Sales: ₱" + totalSales);

	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}

	private String getQueryForSortingRange(String sortingRange, String SSselectedMonth, String SSselectedOptionYears) {
	    String query = "";
	    switch (sortingRange) {
	        case "Yearly":
	            if (SSselectedOptionYears != null && !SSselectedOptionYears.equals("Select")) {
	                query = "SELECT YEAR(s.date) AS year, p.product_name, sp.sell_price, SUM(sp.qty) AS quantity, SUM(sp.sell_price * sp.qty) AS total " +
	                        "FROM sales_product sp " +
	                        "JOIN product p ON sp.product_id = p.barcode " +
	                        "JOIN sales s ON sp.sales_id = s.id " +
	                        "WHERE YEAR(s.date) = " + SSselectedOptionYears + " " +
	                        "GROUP BY year, p.product_name, sp.sell_price " +
	                        "ORDER BY year DESC";
	            }
	            break;
	        case "Monthly":
	            if (SSselectedMonth != null && SSselectedOptionYears != null) {
	                int monthNumber = getMonthNumber(SSselectedMonth);
	                query = "SELECT p.product_name, SUM(sp.qty) AS quantity, SUM(sp.sell_price * sp.qty) AS total " +
	                        "FROM sales_product sp " +
	                        "JOIN product p ON sp.product_id = p.barcode " +
	                        "JOIN sales s ON sp.sales_id = s.id " +
	                        "WHERE MONTH(s.date) = " + monthNumber + " AND YEAR(s.date) = " + SSselectedOptionYears + " " +
	                        "GROUP BY p.product_name " +
	                        "ORDER BY total DESC";
	            }
	            break;
	        case "All":
	            query = "SELECT s.date, p.product_name, sp.qty, sp.sell_price, (sp.sell_price * sp.qty) AS total " +
	                    "FROM sales_product sp " +
	                    "JOIN product p ON sp.product_id = p.barcode " +
	                    "JOIN sales s ON sp.sales_id = s.id " +
	                    "ORDER BY s.date DESC";
	            break;
	        case "Today":
	            query = "SELECT s.date AS day, p.product_name, SUM(sp.qty) AS quantity, SUM(sp.sell_price * sp.qty) AS total " +
	                    "FROM sales_product sp " +
	                    "JOIN product p ON sp.product_id = p.barcode " +
	                    "JOIN sales s ON sp.sales_id = s.id " +
	                    "WHERE DATE(s.date) = DATE(CONVERT_TZ(NOW(), '+00:00', '+08:00')) " +
	                    "GROUP BY day, p.product_name " +
	                    "ORDER BY day DESC";
	            break;
	        case "This Week":
	            query = "SELECT YEAR(s.date) AS year, WEEK(s.date) AS week, p.product_name, sp.sell_price, SUM(sp.qty) AS quantity, " +
	                    "SUM(sp.sell_price * sp.qty) AS total " +
	                    "FROM sales_product sp " +
	                    "JOIN product p ON sp.product_id = p.barcode " +
	                    "JOIN sales s ON sp.sales_id = s.id " +
	                    "WHERE YEAR(s.date) = YEAR(CURDATE()) AND WEEK(s.date) = WEEK(CURDATE()) " +
	                    "GROUP BY year, week, p.product_name, sp.sell_price " +
	                    "ORDER BY year DESC, week DESC";
	            break;
	        default:
	            break;
	    }
	    return query;
	}


   @SuppressWarnings({ "unchecked", "rawtypes" })
private void createPaymentsAndSalesSummaryTable() {
      JPanel paymentsAndSalesPanel = new JPanel();
      paymentsAndSalesPanel.setFont(new Font("Segoe UI", 0, 11));
      paymentsAndSalesPanel.setLayout((LayoutManager)null);
      String[] sortingOptions = new String[]{"Select", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
      JComboBox<String> monthComboBox = new JComboBox(sortingOptions);
      monthComboBox.setForeground(Color.WHITE);
      monthComboBox.setBackground(Color.DARK_GRAY);
      monthComboBox.setBorder(new LineBorder(new Color(51, 51, 51), 2, true));
      monthComboBox.setFont(new Font("Segoe UI", 0, 11));
      monthComboBox.setBounds(10, 10, 90, 25);
      String[] yearOptions = new String[]{"Select", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030"};
      JComboBox<String> yearComboBox = new JComboBox(yearOptions);
      yearComboBox.setForeground(Color.WHITE);
      yearComboBox.setBackground(Color.DARK_GRAY);
      yearComboBox.setBorder(new LineBorder(new Color(51, 51, 51), 2, true));
      yearComboBox.setFont(new Font("Segoe UI", 0, 11));
      yearComboBox.setBounds(122, 10, 90, 25);
      monthComboBox.addActionListener((e) -> {
         String selectedMonth = (String)monthComboBox.getSelectedItem();
         String selectedYear = (String)yearComboBox.getSelectedItem();
         if (!selectedMonth.equals("Select") && !selectedYear.equals("Select")) {
            this.updatePaymentsAndSalesSummary(selectedMonth, selectedYear);
         }

      });
      yearComboBox.addActionListener((e) -> {
         String selectedMonth = (String)monthComboBox.getSelectedItem();
         String selectedYear = (String)yearComboBox.getSelectedItem();
         if (!selectedMonth.equals("Select") && !selectedYear.equals("Select")) {
            this.updatePaymentsAndSalesSummary(selectedMonth, selectedYear);
         }

      });
      paymentsAndSalesPanel.add(monthComboBox);
      paymentsAndSalesPanel.add(yearComboBox);
      DefaultTableModel paymentsAndSalesTableModel = new DefaultTableModel();
      this.paymentsAndSalesTable = new JTable(paymentsAndSalesTableModel);
      JTableHeader paymentsAndSalesTableHead = this.paymentsAndSalesTable.getTableHeader();
      paymentsAndSalesTableHead.setBackground(new Color(255, 204, 51));
      paymentsAndSalesTableHead.setFont(new Font("Segoe UI", 1, 12));
      this.paymentsAndSalesTable.setGridColor(new Color(211, 211, 211));
      this.paymentsAndSalesTable.setFont(new Font("Segoe UI", 0, 11));
      this.paymentsAndSalesTable.setSelectionBackground(SystemColor.textHighlight);
      JScrollPane tableScrollPane = new JScrollPane(this.paymentsAndSalesTable);
      tableScrollPane.setBounds(10, 50, 680, 312);
      paymentsAndSalesPanel.add(tableScrollPane);
      this.totalAmountLabel = new JLabel("Total Amount: ₱0.00");
      this.totalAmountLabel.setFont(new Font("Segoe UI", 1, 13));
      this.totalAmountLabel.setBounds(490, 10, 200, 25);
      paymentsAndSalesPanel.add(this.totalAmountLabel);
      this.contentPanel.add(paymentsAndSalesPanel, "Payments and Sales Summary");
      this.updatePaymentsAndSalesSummary("Select", "Select");
   }

   private void updatePaymentsAndSalesSummary(String selectedMonth, String selectedYear) {
	    String queryPayments = "SELECT SUM(amount) AS total_payments FROM payments WHERE YEAR(date) = " + selectedYear + " AND MONTH(date) = MONTH(STR_TO_DATE('" + selectedMonth + " 01', '%M %d'))";
	    String querySales = "SELECT SUM(subtotal) AS total_sales FROM sales WHERE YEAR(date) = " + selectedYear + " AND MONTH(date) = MONTH(STR_TO_DATE('" + selectedMonth + " 01', '%M %d'))";

	    try (Connection conn = ConnectionProvider.getCon();
	         Statement stmt = conn.createStatement()) {

	        DefaultTableModel tableModel = (DefaultTableModel) this.paymentsAndSalesTable.getModel();
	        tableModel.setRowCount(0);
	        tableModel.setColumnIdentifiers(new Object[]{"Total Payments", "Total Sales"});

	        double totalPayments = getTotalAmount(stmt, queryPayments);
	        double totalSales = getTotalAmount(stmt, querySales);

	        Object[] rowData = new Object[]{totalPayments, totalSales};
	        tableModel.addRow(rowData);
	        this.totalAmountLabel.setText("Total Amount: ₱" + (totalPayments + totalSales));

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	private double getTotalAmount(Statement stmt, String query) throws SQLException {
	    double totalAmount = 0.0;
	    try (ResultSet rs = stmt.executeQuery(query)) {
	        if (rs.next()) {
	            totalAmount = rs.getDouble(1);
	            if (rs.wasNull()) {
	                totalAmount = 0.0;
	            }
	        }
	    }
	    return totalAmount;
	}


   private float writeChartImagesToPDF(PDDocument document, PDPageContentStream contentStream, List<BufferedImage> chartImages, float startY) throws IOException {
	    float chartWidth = PDRectangle.LETTER.getWidth() - 50.0F;
	    float chartHeight = chartWidth / 16.0F * 9.0F;
	    float yOffset = startY;

	    for (BufferedImage image : chartImages) {
	        // Check if the current offset will cause the image to overflow the page
	        if (yOffset < 150.0F) {
	            // Close the current page and start a new one
	            contentStream.close();
	            PDPage newPage = new PDPage(PDRectangle.LETTER);
	            document.addPage(newPage);
	            contentStream = new PDPageContentStream(document, newPage);
	            yOffset = 750.0F; // Reset yOffset for the new page
	        }

	        // Convert the BufferedImage to PDImageXObject and draw it on the page
	        PDImageXObject pdImage = PDImageXObject.createFromByteArray(document, this.toByteArray(image), null);
	        float xOffset = (PDRectangle.LETTER.getWidth() - chartWidth) / 2.0F; // Center the image horizontally
	        contentStream.drawImage(pdImage, xOffset, yOffset - chartHeight, chartWidth, chartHeight);

	        // Update yOffset for the next image
	        yOffset -= chartHeight + 30.0F; // Adjust yOffset for the next image
	    }

	    // Close the content stream and return the final yOffset
	    contentStream.close();
	    return yOffset;
	}


   private float writeTableToPDF(PDPageContentStream contentStream, List<String[]> tableData, float startX, float startY) throws IOException {
	    contentStream.setFont(new PDType1Font(FontName.HELVETICA), 12.0F);
	    float rowHeight = 20.0F;
	    float tableWidth = PDRectangle.LETTER.getWidth() - 50.0F;
	    float cellMargin = 5.0F;
	    float nextY = startY;

	    // Draw table content
	    for (String[] row : tableData) {
	        float x = startX;

	        for (String cell : row) {
	            // Draw cell background
	            contentStream.setNonStrokingColor(Color.LIGHT_GRAY);
	            contentStream.addRect(x, nextY - rowHeight, tableWidth / row.length, rowHeight);
	            contentStream.fill();

	            // Draw cell text
	            contentStream.setNonStrokingColor(Color.BLACK);
	            contentStream.beginText();
	            contentStream.newLineAtOffset(x + cellMargin, nextY - rowHeight + cellMargin);
	            contentStream.showText(cell);
	            contentStream.endText();

	            // Move to next cell
	            x += tableWidth / row.length;
	        }

	        // Move to next row
	        nextY -= rowHeight;
	    }

	    // Draw table borders
	    contentStream.setStrokingColor(Color.BLACK);
	    contentStream.setLineWidth(1.0F);

	    // Draw horizontal lines
	    float y = startY;
	    for (int i = 0; i <= tableData.size(); i++) {
	        contentStream.moveTo(startX, y);
	        contentStream.lineTo(startX + tableWidth, y);
	        contentStream.stroke();
	        y -= rowHeight;
	    }

	    // Draw vertical lines
	    float x = startX;
	    for (int i = 0; i <= tableData.get(0).length; i++) {
	        contentStream.moveTo(x, startY);
	        contentStream.lineTo(x, startY - rowHeight * tableData.size());
	        contentStream.stroke();
	        x += tableWidth / tableData.get(0).length;
	    }

	    return nextY;
	}


   public void populateInboundTable() {
	    try (Connection con = ConnectionProvider.getCon();
	         Statement stmt = con.createStatement()) {

	        String query = "SELECT * FROM inbound";
	        try (ResultSet rs = stmt.executeQuery(query)) {
	            this.inboundTableModel.setRowCount(0);  // Clear the existing table data

	            while (rs.next()) {
	                int id = rs.getInt("id");
	                String productName = rs.getString("product_name");
	                String barcode = rs.getString("barcode");
	                int inbQty = rs.getInt("inbQty");
	                double costPrice = rs.getDouble("cost_price");
	                double retailPrice = rs.getDouble("retail_price");
	                Date eta = rs.getDate("ETA");
	                String status = rs.getString("status");
	                this.inboundTableModel.addRow(new Object[]{id, productName, barcode, inbQty, costPrice, retailPrice, eta, status});
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();  // Log the exception if something goes wrong with executing the query
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();  // Log the exception if something goes wrong with the connection or statement creation
	    }
	}

}