/* Decompiler 20ms, total 514ms, lines 111 */
package Main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.Border;

@SuppressWarnings("serial")
public class LoadingScreen extends JFrame {
   private JProgressBar loadingBar;
   private int progress = 0;

   public LoadingScreen() {
      this.setTitle("FitFlow - Loading");
      this.setIconImage(Toolkit.getDefaultToolkit().getImage(LoadingScreen.class.getResource("/Resources/FitFlowIconPngResized.png")));
      System.out.println(AdminFitFlow.class.getResource("Resources/FitFlowIconPngResized.png"));
      this.setUndecorated(true);
      this.setSize(750, 422);
      this.setDefaultCloseOperation(3);
      this.getContentPane().setLayout(new BorderLayout());
      JLabel loadingLabel = new JLabel(new ImageIcon(LoadingScreen.class.getResource("/Resources/powered by group 3 - lyceum de san pablo.png")));
      this.getContentPane().add(loadingLabel, "Center");
      this.loadingBar = new JProgressBar();
      this.loadingBar.setFocusable(false);
      this.loadingBar.setBorder((Border)null);
      this.loadingBar.setStringPainted(true);
      this.loadingBar.setFont(new Font("Impact", 1, 23));
      this.loadingBar.setForeground(new Color(255, 204, 51));
      this.loadingBar.setBackground(new Color(0, 0, 0));
      this.loadingBar.setMinimum(0);
      this.loadingBar.setMaximum(100);
      this.loadingBar.setValue(this.progress);
      this.getContentPane().add(this.loadingBar, "South");
      this.setLocationRelativeTo((Component)null);
      this.setVisible(true);

      try {
         UIManager.setLookAndFeel("com.formdev.flatlaf.FlatLightLaf");
      } catch (Exception var3) {
         var3.printStackTrace();
      }

      (new Thread(() -> {
         try {
            Thread.sleep(2000L);
            if (this.isInternetAvailable()) {
               SwingUtilities.invokeAndWait(() -> {
                  try {
                     LoginForm loginForm = new LoginForm();
                     loginForm.setVisible(true);
                     this.dispose();
                  } catch (Exception var2) {
                     SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(this, "Cannot connect to the database. Please try again later.", "Database Connection Error", 0);
                        (new LoginFormOffline()).setVisible(true);
                        this.dispose();
                     });
                  }

               });
            } else {
               SwingUtilities.invokeAndWait(() -> {
                  JOptionPane.showMessageDialog(this, "No internet connection detected. Proceeding to offline mode.", "Network Error", 2);
                  (new LoginFormOffline()).setVisible(true);
                  this.dispose();
               });
            }
         } catch (Exception var2) {
            var2.printStackTrace();
            SwingUtilities.invokeLater(() -> {
               JOptionPane.showMessageDialog(this, "An unexpected error occurred. Please try again later.", "Error", 0);
               (new LoginFormOffline()).setVisible(true);
               this.dispose();
            });
         }

      })).start();
      Timer timer = new Timer(1, new ActionListener() {
         @Override
		public void actionPerformed(ActionEvent e) {
            ++LoadingScreen.this.progress;
            LoadingScreen.this.loadingBar.setValue(LoadingScreen.this.progress);
            if (LoadingScreen.this.progress >= 100) {
               LoadingScreen.this.loadingBar.setValue(100);
            }

         }
      });
      timer.start();
   }

   private boolean isInternetAvailable() {
      try {
         InetAddress address = InetAddress.getByName("google.com");
         return address.isReachable(2000);
      } catch (Exception var2) {
         return false;
      }
   }
}