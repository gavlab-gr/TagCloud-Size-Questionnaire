package tag.cloud.app;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 *
 * @author georgia
 */

public final class EndPanel extends JPanel {
    
    CardLayout layout;
    JPanel panelCont;
    JButton linkButton;
    private static final String FONT = "Arial";  
    
    public EndPanel(final CardLayout layout, final JPanel panelCont) 
    {  
        this.layout = layout;
        this.panelCont = panelCont;        
      
        String path = "/img/homeBorder.jpg";
        ImageIcon icon2 = createImageIcon(path, "homeBorder");
        Border border = BorderFactory.createMatteBorder(80, 40, 40, 40, icon2);
        setBorder(border);
        
        //QuestionPanel p= new QuestionPanel(layout, panelCont);
        String text5 = "<br>Ευχαριστούμε θερμά για την συμμετοχή σας. Για περισσότερες πληροφορίες σχετικά με το πείραμα <br> επικοινωνήστε με την ερευνητική ομάδα.</html>" ;
        String text2 = "<html><br>Με βάση τις εικόνες που είδατε ";
        String text3 = "τα αποτελέσματά σας είναι τα εξής:<br> Επιλέξατε ως το ίδιο οπτικά σημαντικές λέξεις που είχαν: <br>ίδιο font size, " +QuestionPanel.getI1()+"/4"+" <br>ίδιο μήκος, "+QuestionPanel.getI2()+
                        "/4"+" <br>ίδιο εμβαδό, "+QuestionPanel.getI3()+"/4"+" <br> και τέλος, "+QuestionPanel.getI4()+"/4"+" μια ενδιάμεση προσέγγιση μεταξύ μήκους και εμβαδού.";
        String text4 =  "δεν βρήκατε κανένα ζευγάρι λέξεων <br> το ίδιο οπτικά σημαντικό. ";
        String text6;
        
        
        if((QuestionPanel.getI1()==0)&&(QuestionPanel.getI2()==0)&&(QuestionPanel.getI3()==0)&&(QuestionPanel.getI4()==0))        
           text6 = text2 + text4 + text5; 
        else
           text6 = text2 + text3 + text5; 
        
        
        JLabel text = new JLabel(text6, JLabel.CENTER);       
        text.setFont(new Font(FONT, Font.PLAIN, 16));
        setLayout(new BorderLayout());
        setBackground(Color.white);
        add(text, BorderLayout.CENTER);        
        
        String path4 = "/img/logoBubble.jpg";
        ImageIcon icon3= createImageIcon(path4, "logoButton");        
        JButton link = new JButton(icon3);    
        
        link.setOpaque(false);
        link.setContentAreaFilled(false);
        link.setBorderPainted(false);
        link.setFocusPainted(false);       
           
        add(link,BorderLayout.PAGE_END);       
        
        link.addActionListener(new ActionListener(){       
        public void actionPerformed(ActionEvent e)
        {  
            try {
                URL  url = new URL("http://gav.uop.gr/");
                openWebpage(url);
            } catch (MalformedURLException ex) {
                Logger.getLogger(EndPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            
               
        }
        });
    } 
    
    /** Returns an ImageIcon, or null if the path was invalid. */
    protected ImageIcon createImageIcon(String path, String description) {
    java.net.URL imgURL = getClass().getResource(path);
    if (imgURL != null) {
        return new ImageIcon(imgURL, description);
    } else {
        System.err.println("Couldn't find file: " + path);
        return null;
    }
  }
    
    public void openWebpage(URI uri) {
    Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
        try {
            desktop.browse(uri);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

    public void openWebpage(URL url) {
        try {
            openWebpage(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
