package tag.cloud.app;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author georgia
 */

public class Layout {

    private final JFrame frame = new JFrame();       
    private CardLayout cards;  
        
    private void Layout(JPanel contentPane)
    {        
        cards = new CardLayout();
        contentPane.setLayout(cards);  
        contentPane.setBackground(Color.white);
                
        File f= new File(); 
        java.util.Date date1= new java.util.Date();
        Timestamp time = new Timestamp(date1.getTime());
        String t=time.toString();        
        f.setName(t);
        String name= f.getName();
       
        HomePanel panel1 = new HomePanel(cards, contentPane, name);
        contentPane.add(panel1, "1");          
       
        cards.show(contentPane, "1");
        PrintWriter writer;       
        try {
            writer = new PrintWriter (new FileWriter(name, true));
            writer.printf("%-10s%-10s%-15s%-10s%-10s%-15s%-15s%-30s%-15s%-30s\r\n", "Λέξη 1", "Θέση 1", "Αρ.Γραμμάτων1" , "Λέξη 2", "Θέση 2", 
                    "Αρ.Γραμμάτων2","Μορφοποίηση", "Αρχή χρόνου απάντησης", "Απάντηση", "Τέλος χρόνου απάντησης");
            writer.close(); 
        } catch (IOException ex) {
            Logger.getLogger(Layout.class.getName()).log(Level.SEVERE, null, ex);
        }       
             
        frame.add(contentPane, BorderLayout.CENTER);
        frame.setTitle("Tag Cloud Questions");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);       
        frame.pack();
        frame.setVisible(true);      
    }
    
    private static void createAndShowGUI() {         
        JPanel contentPane = new JPanel();       
        Layout c = new Layout();        
        c.Layout(contentPane);        
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                createAndShowGUI();
            }
        });
    }
    
} 
        
        
    
    
    
   
    

