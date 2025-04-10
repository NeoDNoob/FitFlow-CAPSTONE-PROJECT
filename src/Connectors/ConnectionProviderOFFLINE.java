/* Decompiler 14ms, total 382ms, lines 20 */
package Connectors;

import java.awt.Component;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;

public class ConnectionProviderOFFLINE {
   public static Connection getCon() {
      try {
         Class.forName("com.mysql.cj.jdbc.Driver");
         Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/fitflowofflinedb", "root", "admin123");
         return con;
      } catch (Exception var1) {
         JOptionPane.showMessageDialog((Component)null, "Cannot Connect to Offline Database, please contact the Admin or try again later.");
         return null;
      }
   }
}