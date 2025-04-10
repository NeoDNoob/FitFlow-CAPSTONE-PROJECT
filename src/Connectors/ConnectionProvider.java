/* Decompiler 5ms, total 653ms, lines 20 */
package Connectors;

import java.awt.Component;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;

public class ConnectionProvider {
   public static Connection getCon() {
      try {
         Class.forName("com.mysql.cj.jdbc.Driver");
         Connection con = DriverManager.getConnection("jdbc:mysql://sql12.freesqldatabase.com:3306/sql12733864", "sql12733864", "TsUVkXxjZG");
         return con;
      } catch (Exception var1) {
         JOptionPane.showMessageDialog((Component)null, "Cannot Connect to Online Database, please contact the Admin or try again later.");
         return null;
      }
   }
}