package tag.cloud.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Math.sqrt;
import java.sql.Timestamp;
import java.util.Random;
import java.util.Vector;

/**
 *
 * @author georgia
 */

public class TagImgPanel extends JPanel
{
    CardLayout layout;
    JPanel panelCont;      
    private Timer timer;
    private int endCounter = 0;
    private int counter = 3; // the duration
    private int delay = 1000; // every 1 second
    private static final long serialVersionUID = 1L;
    private static String[] consonant = {"q","w","r","t","p","s","d","f","g","h","j","k","l","z","x","c","v","b","n","m"}; //0-19   
    
    private final Random n = new Random();
    private final Random n2 = new Random();
    private final Random n3 = new Random();
    private int place1;
    private int place2;      
    private int change_num;
    
    private static final int font_size = 25; 
    private static final String FONT = "Arial"; 
    private static final int width = 50;
    private static final int position = 25;
    
    private static final int flag = 4; 
    private static int flag1 = 0;  
    private static int flag2 = 0;  
    private static int flag3 = 0;  
    private static int flag4 = 0;  
    
    private static int endFlag = 0;
    private Vector a = new Vector();
    private Vector a2 = new Vector(); 
    
    private static final JProgressBar bar = new JProgressBar();
    private final int numOfQuestions = 16;
    

    public static int getEndFlag() {
        return endFlag;
    }
    
    public int getChange_num() {
        return change_num;
    }

    public void setChange_num(int change_num) {
        this.change_num = change_num;
    }
     
    public int getPlace1() {
        return place1;
    }

    public int getPlace2() {
        return place2;
    }
    
    public TagImgPanel(final CardLayout layout, final JPanel panelCont, String name) throws IOException  
    {    
       
        this.change_num = n3.nextInt(4)+1;
        this.place1=n2.nextInt(10);
        this.place2 = n.nextInt(10);        
        this.layout = layout;
        this.panelCont = panelCont;        
        displayTagImgPanel(layout, panelCont, name);
    
    }     
    
    private void displayTagImgPanel(final CardLayout layout, final JPanel panelCont, String name)throws IOException
    {
        setLayout(new GridLayout(3, 3));           
        
        if (place1%2==0)
            place1++;
        if (place2%2==0)
            place2++;
        if(place1==place2)
        {
            if(place1<9)
                place1+=2;
            else
                place1-=2;      
        
        }    
       
       String s1= GenerateWords();
       String s2= GenerateWords();
       int len1=s1.length();
       int len2=s2.length();              
       int change_int=calcNumOfChange();       
       
       BufferedImage bufferedImage1;   
       BufferedImage bufferedImage2;    
       bufferedImage1=createSmall(s1, s2, change_int );     
       bufferedImage2=createBig(s1, s2);         
       
       JLabel label2 = new JLabel(new ImageIcon(bufferedImage1));
       JLabel label3 = new JLabel(new ImageIcon(bufferedImage2));         
       label2.setHorizontalAlignment(JLabel.CENTER);
       label3.setHorizontalAlignment(JLabel.CENTER);
              
       for(int i=1; i<9; i++){
            if(place1==i){                
                add(label2);
            }
            else if(place2==i){                
                add(label3); 
            }
            else{
                add(new JLabel());                          
            }
        }
        if (place1==9)
           add(label2);
        if (place2==9)
           add(label3);             
        
       setBackground(Color.white);     
       
       PrintWriter writer = new PrintWriter (new FileWriter(name, true));       
       writer.printf("%-10s%-10s%-15s%-10s%-10s%-15s%-15s", s1, place1, len1, s2, place2, len2, change_int);
        
       endFlag++;
       
       ActionListener action = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
               
                QuestionPanel panel3;
                
                if(counter==0)
                {                                      
                    timer.stop();                    
                    if(endFlag<=numOfQuestions){
                        if (endCounter<=5){
                            java.util.Date date1= new java.util.Date();
                            writer.printf("%-30s",new Timestamp(date1.getTime()) );
                            writer.close();  
                            
                        }  
                        
                        panel3 = createQuestion(layout, panelCont, name, bar, change_num);
                        panel3.setEndFlagQ(endFlag);
                        panelCont.add(panel3, "3");
                        layout.show(panelCont, "3");                   
                        
                    }
                    if (endFlag==numOfQuestions+2){
                         System.exit(0);
                    }
                    
                }
                else
                {                    
                    counter--;
                    endCounter++;                    
                }                
            }
        };

        timer = new Timer(delay, action);
        timer.setInitialDelay(0);
        timer.start();      
    }
   
    /*creates the appropriate question that corresponds to the created image*/
    public QuestionPanel createQuestion(final CardLayout layout, final JPanel panelCont, String name, JProgressBar bar, int num)
    {
        QuestionPanel panel3;
        QuestionPanel panel4;
        a.add(this.getPlace1());
        a2.add(this.getPlace2());       
       
        if((place1==1)&&(place2==3)||(place2==1)&&(place1==3)||(place1==7)&&(place2==9)||(place2==7)&&(place1==9)){           
            panel3 = new QuestionPanel(layout, panelCont, 2, a, a2, name, bar, num);
            panel3.setWord1Place(a);
            panel3.setWord2Place(a2);             
            return panel3;
        }
        else{            
            panel4 = new QuestionPanel(layout, panelCont, 1, a, a2, name, bar, num);
            panel4.setWord1Place(a);
            panel4.setWord2Place(a2);            
            return panel4;
        }   
    }
    
    /*ensures that the random generated numbers for the modification are equally distributed
    ("flag" images for each modification)*/
    private int calcNumOfChange()    
    {       
       
       if (flag1>=flag){                 
           change_num = n3.nextInt((4-2)+1)+2;
           
           if (flag2>=flag)
               change_num = n3.nextInt((4-3)+1)+3;
           if (flag3>=flag)
               change_num = 2;
           if (flag4>=flag)
               change_num = n3.nextInt((3-2)+1)+2;
           if ((flag2>=flag)&&(flag4>=flag))
               change_num=3;           
           if ((flag2>=flag)&&(flag3>=flag))
               change_num=4;           
           if ((flag4>=flag)&&(flag3>=flag))
               change_num=2;           
       }
      else if (flag2>=flag){                   
            change_num = n3.nextInt((4-3)+1)+3;           
            if (flag1>=flag)
               change_num = n3.nextInt((4-3)+1)+3;
            if (flag3>=flag)
               change_num = 1;
            if (flag4>=flag)
               change_num = 3;
            if ((flag1>=flag)&&(flag4>=flag))
               change_num=3;            
            if ((flag4>=flag)&&(flag3>=flag))
               change_num=1;            
            if ((flag1>=flag)&&(flag3>=flag))
               change_num=4;           
            
       }
       else if (flag3>=flag){                  
           change_num = n3.nextInt((2-1)+1)+1;  
           if (flag2>=flag)
               change_num = 4;
           if (flag1>=flag)
               change_num = 2;
           if (flag4>=flag)
               change_num = n3.nextInt((2-1)+1)+1;
           if ((flag1>=flag)&&(flag4>=flag))
               change_num=2;           
           if ((flag2>=flag)&&(flag4>=flag))
               change_num=1;           
           if ((flag1>=flag)&&(flag2>=flag))
               change_num=4;           
       }
       else if (flag4>=flag){
                  
           change_num = n3.nextInt((3-1)+1)+1;         
           if (flag2>=flag)
               change_num = 3;
           if (flag3>=flag)
               change_num = 1;
           if (flag1>=flag)
               change_num = n3.nextInt((3-2)+1)+2;
           if ((flag1>=flag)&&(flag2>=flag))
              change_num=3;          
           if ((flag2>=flag)&&(flag3>=flag))
               change_num=1;           
           if ((flag4>=flag)&&(flag3>=flag))
               change_num=1;                
        }
    
       if (change_num==1)
           flag1++;
       else if (change_num==2)
           flag2++;
       else if (change_num==3)
           flag3++;
       else
           flag4++;           
       return change_num;
   
    }
    
    /*Generates and returns a random String, 5-8 letters that includes olny consonants*/
    private String GenerateWords()
    {          
        String s = "";
        boolean stop= true;        
        int numberOfWord = n2.nextInt(4) + 5;        
        while(s.length()< numberOfWord)
        {
            int numberOfConsonant = n2.nextInt(20) ;
            if(stop){
                s=s+consonant[numberOfConsonant];
                stop=false;            
            }
            else{
                s=s+consonant[numberOfConsonant];
                stop=true;            
            }            
        }
        return s;
    }
    
    /*creates the small word in TagImgPanel. Small word is modified in one of the 4 implemented ways
    in order to match the criteria and be equal with the big word.*/
    private BufferedImage createSmall(String string1, String string2, int i){
       
        int boxSize;
        String s;
        if(string1.length()<=string2.length())
        {
            boxSize= string1.length();
            s=string1;
        }
        else{
            boxSize=string2.length();
            s=string2;
        }
        Graphics2D g2;
        BufferedImage bufferedImage = new BufferedImage(boxSize*font_size, width, BufferedImage.TYPE_INT_RGB);
        g2 = bufferedImage.createGraphics();        
        
       
        Font font = new Font(FONT, Font.PLAIN, font_size);
        FontMetrics metrics = g2.getFontMetrics(font);        
        int hgt = metrics.getHeight();       
        int adv = metrics.stringWidth(s);
       
        Dimension size = new Dimension(adv+2, hgt+2);
        int width2 = (int)(size.getWidth()*2f);
	int height = (int)(size.getHeight()*2f);
        
        bufferedImage = new BufferedImage(width2, height, BufferedImage.TYPE_INT_RGB);
        g2 = bufferedImage.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
       
        g2.setPaint(Color.WHITE);
        g2.fill(new Rectangle(0, 0, width2, height ));
        g2.setPaint(Color.BLACK);
            
        String small = smallerStr(string1, string2, g2);
        String big = biggerStr(string1, string2, g2);
        
        double xSmall= strX(small, g2);
        double ySmall= strY(small, g2);
        double xBig= strX(big, g2);
        double yBig= strY(big, g2);       
        /*scale2= scaling number for same width. It is used to change lenght and heigth*/ 
        double scale2= xBig/xSmall;       
        
        double c= xSmall/ySmall;
        double y2Small= sqrt((xBig*yBig)/c);
        double x2Small= y2Small*c;
        /*scale_3x= scaling number for same area. It is used to change lenght*/
        /*scale_3y= scaling number for same area. It is used to change height*/
        double scale_3x= x2Small/xSmall;
        double scale_3y= y2Small/ySmall;      
        /*scale_4x= scaling number for intermediate approach. It is used to change lenght*/
        /*scale_4y= scaling number for intermediate approach. It is used to change height*/
        double scale_4x= (scale2+scale_3x)/2;
        double scale_4y= (scale2+scale_3y)/2;             
        
        if (i==1){   
            //same font size
            g2.drawString(small, 0 ,position);             
        }
        else if(i==2){
            //same width
            AffineTransform scale = new AffineTransform();  
            scale.concatenate(AffineTransform.getScaleInstance(scale2, scale2));
            g2.setTransform(scale);       
            g2.drawString(small,0 ,position);          
        }
        else if(i==3){       
            //same area
            AffineTransform scale3 = new AffineTransform();             
            scale3.concatenate(AffineTransform.getScaleInstance(scale_3x, scale_3y));
            g2.setTransform(scale3);        
            g2.drawString(small, 0 ,position);      
        }   
        else if(i==4){ 
            //intermediate approach between same length and same area
            AffineTransform scale4 = new AffineTransform();             
            scale4.concatenate(AffineTransform.getScaleInstance(scale_4x, scale_4y));
            g2.setTransform(scale4); 
            g2.drawString(small, 0 ,position);
        }
        g2.dispose();
        return bufferedImage;
       
    }
    
    /*creates the big word in TagImgPanel- note- Big word doesn't need any alternation only draw it with the specific font*/
    private BufferedImage createBig(String string1, String string2){
        int boxSize;
        if(string1.length()>=string2.length())
            boxSize= string1.length();
        else
            boxSize=string2.length();
        
        BufferedImage bufferedImage = new BufferedImage(boxSize*font_size, width, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = bufferedImage.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setPaint(Color.white);
        g2.fill(new Rectangle(0, 0, boxSize*font_size, 50));
        g2.setPaint(Color.BLACK);       
        String big = biggerStr(string1, string2, g2);
        g2.setFont(new Font(FONT, Font.PLAIN, font_size));                     
        g2.drawString(big, 0 ,position);                 
        g2.dispose();
        return bufferedImage;       
    } 
    
    /*calculates and return width*/
    private double strX(String str, Graphics2D g2){          
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);                     
            FontRenderContext frc = g2.getFontRenderContext();
            Rectangle2D r1=g2.getFont().getStringBounds(str, frc);
            return r1.getWidth();             
    }
     
    /*calculates and return height*/
    private double strY(String str, Graphics2D g2){          
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);                       
            FontRenderContext frc = g2.getFontRenderContext();
            Rectangle2D r1=g2.getFont().getStringBounds(str, frc);
            return r1.getHeight();                
    }
      
    /*returns the smaller of the two strings*/
    private  String smallerStr(String str1, String str2, Graphics2D g2) {
        if(strLength(str1, g2)<=strLength(str2, g2))
            return str1;
        else
            return str2;
    }

    /*returns the bigger of the two strings*/
    private String biggerStr(String str1, String str2, Graphics2D g2) {
        if(strLength(str1, g2)>strLength(str2, g2))
            return str1;
        else
            return str2;
    }
       
    /*calculates the length of str*/
    private int strLength(String str, Graphics2D g2) {       
        Font font = new Font(FONT, Font.PLAIN, font_size);
        g2.setFont(font);
        FontMetrics metrics = g2.getFontMetrics(font);
        return metrics.stringWidth(str);
    }
 
    
}

