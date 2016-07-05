package tag.cloud.app;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.BorderFactory; 
import static javax.swing.border.EtchedBorder.RAISED;

/**
 *
 * @author georgia
 */

public class HomePanel extends JPanel {
    String text;   
    CardLayout layout;
    JPanel panelCont;
   
    public HomePanel(final CardLayout layout, final JPanel panelCont, String name)
    {
        this.text = "<html><br>Τα σύννεφα λέξεων, όπως το παράδειγμα εδώ, χρησιμοποιούνται για να δείξουν"
                + " ένα σύνολο<br> λέξεων με διαφορετική σημαντικότητα. "
                + " Στην έρευνα που ακολουθεί θα θέλαμε  να μας πείτε<br> ποιες από τις λέξεις που θα δείτε, "
                + "θεωρείτε εσείς ως πιο σημαντική.<br> Προσοχή: "
                + "δεν υπάρχουν σωστές ή λάθος απαντήσεις και δεν θέλουμε απλά τη μεγαλύτερη λέξη<br>"
                + "αλλά,  όποια νομίζετε ότι είναι η πιο σημαντική από τις δύο που θα βλέπετε κάθε φορά.<br></html>";
        this.layout = layout;
        this.panelCont = panelCont;       
        displayJFrame(layout, panelCont, name);              
        
    }
       
    private void displayJFrame(final CardLayout layout, final JPanel panelCont, String name)   
    {  
        JPanel j=new JPanel(); 
        JPanel j2=new JPanel(); 
        j.setLayout(new BorderLayout());
        String path2 = "/img/homeBorder.jpg";
        ImageIcon icon2= createImageIcon(path2, "homeBorder");
        Border border = BorderFactory.createMatteBorder(80, 40, 40, 40, icon2);
        Border border2 = BorderFactory.createBevelBorder(RAISED);
        
        j.setBorder(border2);
        j2.setBorder(border);
        String path = "/img/tag.jpg";
        ImageIcon icon= createImageIcon(path, "home");        
        j.add(new JLabel(icon));   
        
        String path3 = "/img/button3.png";
        ImageIcon icon3= createImageIcon(path3, "home");        
        JButton buttonOne = new JButton(icon3);    
        
        buttonOne.setOpaque(false);
        buttonOne.setContentAreaFilled(false);
        buttonOne.setBorderPainted(false);
        buttonOne.setFocusPainted(false);
        j.add(buttonOne, BorderLayout.PAGE_END); 
        
        JLabel text2 = new JLabel(text); 
        text2.setFont(new Font("Serif", Font.PLAIN, 18));       
        j.setBackground(Color.WHITE);
        Dimension d = new Dimension(800,600);
        panelCont.setPreferredSize(d);
        j.add(text2, BorderLayout.PAGE_START);      
        j2.add(j);
        add(j2, BorderLayout.PAGE_END); 
        
        buttonOne.addActionListener(new ActionListener(){       
            public void actionPerformed(ActionEvent e)
            {                   
                TagImgPanel panel2;
                try {
                    panel2 = new TagImgPanel(layout, panelCont, name);                     
                    panelCont.add(panel2, "2");        
                    layout.show(panelCont, "2"); 
                } catch (IOException ex) {
                    Logger.getLogger(HomePanel.class.getName()).log(Level.SEVERE, null, ex);
                }            
            }
        });
        
    }
    
     /*Returns an ImageIcon, or null if the path was invalid.*/
    protected ImageIcon createImageIcon(String path, String description) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
  }
    
}
