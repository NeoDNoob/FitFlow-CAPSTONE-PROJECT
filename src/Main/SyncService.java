/* Decompiler 200ms, total 995ms, lines 376 */
/*package Main;



import java.net.InetAddress;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import Connectors.ConnectionProvider;
import Connectors.ConnectionProviderOFFLINE;

public class SyncService {
	private List<Integer> getIds(Connection con, String tableName, String primaryKey) throws SQLException {
	    List<Integer> ids = new ArrayList<>();
	    String query = "SELECT " + primaryKey + " FROM " + tableName;

	    try (Statement stmt = con.createStatement();
	         ResultSet rs = stmt.executeQuery(query)) {

	        while (rs.next()) {
	            ids.add(rs.getInt(primaryKey));
	        }

	    }

	    return ids;
	}


   private void insertRecord(Connection sourceCon, Connection targetCon, String tableName, String primaryKey, int id) throws SQLException {
	    String selectQuery = "SELECT * FROM " + tableName + " WHERE " + primaryKey + " = ?";
	    String insertQuery = this.generateInsertQuery(tableName);

	    try (PreparedStatement selectStmt = sourceCon.prepareStatement(selectQuery);
	         PreparedStatement insertStmt = targetCon.prepareStatement(insertQuery)) {

	        selectStmt.setInt(1, id);
	        try (ResultSet rs = selectStmt.executeQuery()) {
	            if (rs.next()) {
	                ResultSetMetaData metaData = rs.getMetaData();
	                int columnCount = metaData.getColumnCount();
	                System.out.println("Column count from SELECT query: " + columnCount);
	                System.out.println("Generated INSERT query: " + insertQuery);

	                if (columnCount != insertStmt.getParameterMetaData().getParameterCount()) {
	                    throw new SQLException("Column count mismatch between SELECT and INSERT queries.");
	                }

	                for (int i = 1; i <= columnCount; ++i) {
	                    insertStmt.setObject(i, rs.getObject(i));
	                }

	                insertStmt.executeUpdate();
	            }
	        }
	    }
	}


   private String generateInsertQuery(String tableName) {
      int columnCount = 0;

      try {
         columnCount = this.getColumnCount(tableName);
      } catch (SQLException var5) {
         var5.printStackTrace();
      }

      StringBuilder queryBuilder = new StringBuilder("INSERT INTO " + tableName + " VALUES (");

      for(int i = 0; i < columnCount; ++i) {
         queryBuilder.append("?, ");
      }

      queryBuilder.setLength(queryBuilder.length() - 2);
      queryBuilder.append(")");
      return queryBuilder.toString();
   }

   private int getColumnCount(String tableName) throws SQLException {
	    try (Connection conn = ConnectionProvider.getCon();
	         PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + tableName + " LIMIT 1");
	         ResultSet rs = stmt.executeQuery()) {

	        ResultSetMetaData metaData = rs.getMetaData();
	        return metaData.getColumnCount();

	    } catch (SQLException e) {
	        throw e; // Re-throw the SQLException
	    }
	}


   public boolean isInternetAvailable() {
      try {
         return InetAddress.getByName("8.8.8.8").isReachable(2000);
      } catch (Exception var2) {
         return false;
      }
   }

   public void syncDatabases() {
      if (this.isInternetAvailable()) {
         this.syncOfflineToOnline();
         this.syncOnlineToOffline();
      } else {
         System.out.println("No internet connection; running in offline mode.");
      }

   }

   private void syncOfflineToOnline() {
	    try (Connection offlineCon = ConnectionProviderOFFLINE.getCon();
	         Connection onlineCon = ConnectionProvider.getCon()) {

	        syncTable("admincred", offlineCon, onlineCon, "id");
	        syncTable("member", offlineCon, onlineCon, "id");
	        syncTable("payments", offlineCon, onlineCon, "id");
	        syncTable("product", offlineCon, onlineCon, "id");
	        syncTable("sales", offlineCon, onlineCon, "id");
	        syncTable("sales_product", offlineCon, onlineCon, "id");
	        syncTable("audit_trail", offlineCon, onlineCon, "id");
	        syncTable("inbound", offlineCon, onlineCon, "id");

	        JOptionPane.showMessageDialog(null, "Offline to online sync complete.");

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}


   private void syncOnlineToOffline() {
	    try (Connection onlineCon = ConnectionProvider.getCon();
	         Connection offlineCon = ConnectionProviderOFFLINE.getCon()) {

	        syncTable("admincred", onlineCon, offlineCon, "id");
	        syncTable("member", onlineCon, offlineCon, "id");
	        syncTable("payments", onlineCon, offlineCon, "id");
	        syncTable("product", onlineCon, offlineCon, "id");
	        syncTable("sales", onlineCon, offlineCon, "id");
	        syncTable("sales_product", onlineCon, offlineCon, "id");
	        syncTable("audit_trail", onlineCon, offlineCon, "id");
	        syncTable("inbound", onlineCon, offlineCon, "id");

	        JOptionPane.showMessageDialog(null, "Online to offline sync complete.");

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}


   private void syncTable(String tableName, Connection sourceCon, Connection targetCon, String primaryKey) throws SQLException {
	    List<Integer> sourceIds = getIds(sourceCon, tableName, primaryKey);
	    List<Integer> targetIds = getIds(targetCon, tableName, primaryKey);

	    for (int id : sourceIds) {
	        if (!targetIds.contains(id)) {
	            insertRecord(sourceCon, targetCon, tableName, primaryKey, id);
	        }
	    }
	}

}
 *
 */