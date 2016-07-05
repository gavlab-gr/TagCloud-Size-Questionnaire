package tag.cloud.app;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import java.util.Vector;
import javax.swing.border.Border;
import static javax.swing.border.EtchedBorder.RAISED;

/**
 *
 * @author georgia
 */

public class QuestionPanel extends JPanel
{    
    private final CardLayout layout;
    private final JPanel panelCont;   
    private Vector word1Place = new Vector();
    private Vector word2Place = new Vector();  
    private final String up = "Η πάνω     ";
    private final String down = "Η κάτω     ";
    private final String both = "Και οι δύο ";
    private final String left = "Η αριστερά";
    private final String right = "Η δεξιά     ";
    private static final String FONT = "Arial"; 
    private int num;
    private int endFlagQ = 0;
    private final int numOfQuestions;
    private static int i1 = 0;
    private static int i2 = 0;
    private static int i3 = 0;
    private static int i4 = 0;
    ButtonGroup group = new ButtonGroup();

    public int getEndFlagQ() {
        return endFlagQ;
    }

    public void setEndFlagQ(int endFlagQ) {
        this.endFlagQ = endFlagQ;
    }

    public static void setI1(int i1) {
        QuestionPanel.i1 = i1;
    }

    public static void setI2(int i2) {
        QuestionPanel.i2 = i2;
    }

    public static void setI3(int i3) {
        QuestionPanel.i3 = i3;
    }

    public static void setI4(int i4) {
        QuestionPanel.i4 = i4;
    }  
    
    public static int getI1() {
        return i1;
    }

    public static int getI2() {
        return i2;
    }

    public static int getI3() {
        return i3;
    }

    public static int getI4() {
        return i4;
    }
    
    public void setA(int i) {
        word1Place.add(i);
        
    }

    public void setWord2Place(int i) {
        word2Place.add(i);
    }   

    public Vector getWord1Place() {
        return word1Place;
    }

    public Vector getA2() {
        return word2Place;
    }

    public void setWord1Place(Vector a) {
        this.word1Place = word1Place;
    }

    public void setWord2Place(Vector a2) {
        this.word2Place = a2;
    }    
   
 
        
    public QuestionPanel(final CardLayout layout, final JPanel panelCont, int i, Vector word1Place, Vector a2, String name, JProgressBar bar, int num) 
    {  
        this.numOfQuestions = 16;
        this.layout = layout;
        this.panelCont = panelCont; 
        this.word2Place=a2;
        this.word1Place=word1Place;
        this.num=num;
        displayJFrame(layout, panelCont, i, name, bar);    
        
    }
    
    private void displayJFrame(final CardLayout layout, final JPanel panelCont, int i, String name, JProgressBar bar)   
    {  
        
        JPanel j2=new JPanel(new GridLayout(1,0)); 
        JLabel text = new JLabel("Ποιά λέξη ήταν οπτικά πιο σημαντική;", JLabel.CENTER);        
        JPanel panel = new JPanel(new GridLayout(5,0));       
        text.setFont(new Font(FONT, Font.PLAIN, 26));       
        panel.setBackground(Color.WHITE);
        panel.add(text);  
       
        String path3 = "/img/homeBorder.jpg";
        ImageIcon icon2= createImageIcon(path3, "homeBorder");
        Border border = BorderFactory.createMatteBorder(80, 40, 40, 40, icon2);
        Border border2 = BorderFactory.createBevelBorder(RAISED);
        panel.setBorder(border2);
        j2.setBorder(border);
              
        JRadioButton option1;
        JRadioButton option2;
        JRadioButton option3;
        
        if(i==1){
            option3 = new JRadioButton(both);
            option1=new JRadioButton(up);  
            option2=new JRadioButton(down);
        }
        else
        {
            option3 = new JRadioButton(both);
            option1=new JRadioButton(right);  
            option2=new JRadioButton(left);        
        }
               
        group.add(option1);
        group.add(option2);
        group.add(option3);        
               
        setLayout(new BorderLayout()); 
        option1.setBackground(Color.white);
        option1.setFont(new Font(FONT, Font.PLAIN, 18));
        option1.setHorizontalAlignment(JRadioButton.CENTER);
        panel.add(option1);
        option2.setBackground(Color.white);
        option2.setFont(new Font(FONT, Font.PLAIN, 18));
        option2.setHorizontalAlignment(JRadioButton.CENTER);
        panel.add(option2);
        option3.setBackground(Color.white);
        option3.setFont(new Font(FONT, Font.PLAIN, 18));
        option3.setHorizontalAlignment(JRadioButton.CENTER);
        panel.add(option3);           
       
        bar.setBackground(Color.WHITE);            
        bar.setStringPainted(true);
        panel.add(bar);  
        changeProgressBar(panel, bar);        
        
        j2.add(panel, BorderLayout.CENTER);
        add(j2, BorderLayout.CENTER);       
       
        ActionListener l;
        l = new ActionListener(){       
            @Override
            public void actionPerformed(ActionEvent e)
            {
                
                int userChoice =  getSelectedButtonText(word1Place, word2Place) ;
                
                PrintWriter writer;
                try {
                    writer = new PrintWriter(new FileWriter( name, true));
                    writer.printf("%-15s", userChoice );
                    java.util.Date date2= new java.util.Date();
                    writer.printf("%-30s\r\n", new Timestamp(date2.getTime()) );      
                    writer.close();                            
                } catch (IOException ex) {
                    Logger.getLogger(QuestionPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                TagImgPanel panel4 = null;
                
                try {      
                    calcEqualImpAnswers(num, userChoice);
                    panel4 = new TagImgPanel(layout, panelCont, name);
                  
                    word1Place.add(panel4.getPlace1());
                    word2Place.add(panel4.getPlace2());                    
                
                } catch (IOException ex) {
                    Logger.getLogger(QuestionPanel.class.getName()).log(Level.SEVERE, null, ex);
                }                
               
                if (endFlagQ==numOfQuestions){
                    EndPanel panel6 = new EndPanel(layout,panelCont);
                    panelCont.add(panel6, "6");
                    layout.show(panelCont, "6");                                           
                }
                if (endFlagQ==numOfQuestions+1){
                    System.exit(0);
                }
                if (endFlagQ<=numOfQuestions-1){
                    panelCont.add(panel4, "5");
                    layout.show(panelCont, "5");        
                }
            }           
        };
        
       option1.addActionListener(l);
       option2.addActionListener(l);
       option3.addActionListener(l);
    }   
 
    /*calculates the answers for each modification 
    when the user chooses that two words are equally important. It is used for the results in the EndPanel*/
    public void calcEqualImpAnswers(int num, int userChoice)
    {
        if ((userChoice==0)&&(endFlagQ<numOfQuestions+1)){                                         
            int ii=num;               
            if (ii==1)
                i1++;
            if (ii==2)
                i2++;
            if (ii==3)
                i3++;
            if (ii==4)
                i4++;             
        }  
    
    }
  
  /*Repaints the ProgressBar every time a new question appears.*/  
  public void changeProgressBar(JPanel panel, JProgressBar bar) {
    SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() { 
                panel.add(bar);
                panel.repaint();
                bar.setStringPainted(true);            
                bar.setString(getEndFlagQ()+ "/16");
                bar.setForeground(Color.LIGHT_GRAY);
                int value = bar.getValue() + 6;
                if (value > bar.getMaximum()) {
                  value = bar.getMaximum();
                }
                bar.setValue(value);         
            }
        });
    }  
       
    /* Returns an ImageIcon, or null if the path was invalid. */
    protected ImageIcon createImageIcon(String path, String description) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
   
   /*Check and return the user's answer. The answer is an int
   (the position that the chosen word was located in the TagImgPanel) */ 
   public int getSelectedButtonText( Vector a, Vector a2 ) {
       
       Object one = 1;   //upper left position
       Object three = 3; //upper right position
       Object seven = 7; //down left position       
       Object nine = 9;  //down right position
       int choice = 0;   //Both words are equally important
        for (Enumeration<AbstractButton> buttons = this.group.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
               if( button.getText().compareTo(both)==0)
               {
                  choice = 0;
               }
               else if( button.getText().compareTo(up)==0)
               {
                   if((a.lastElement().equals(one))||(a2.lastElement().equals(one)))
                   {
                       choice = 1;
                   }
                   else if((a.lastElement().equals(three))||(a2.lastElement().equals(three)))
                   {
                       choice = 3;
                   }
                   else
                   {
                        choice = 5;
                   }
                   
               }
               else if( button.getText().compareTo(down)==0)
               {
                   if((a.lastElement().equals(nine))||(a2.lastElement().equals(nine)))
                   {
                       choice = 9;
                   }
                   else if((a.lastElement().equals(seven))||(a2.lastElement().equals(seven)))
                   {
                       choice = 7;
                   }
                   else
                   {
                        choice = 5;
                   }
               
               }
               else if( button.getText().compareTo(left)==0)
               {
               
                   if((a.lastElement().equals(one))||(a2.lastElement().equals(one)))
                   {
                       choice = 1;
                   }
                   else if((a.lastElement().equals(seven))||(a2.lastElement().equals(seven)))
                   {
                       choice = 7;
                   }              
               
               }
               else if( button.getText().compareTo(right)==0)
               {
                   if((a.lastElement().equals(nine))||(a2.lastElement().equals(nine)))
                   {
                       choice = 9;
                   }
                   else if((a.lastElement().equals(three))||(a2.lastElement().equals(three)))
                   {
                       choice = 3;
                   }              
               
               }
            }            
        }        
        return choice;        
    }  
     
    
}
