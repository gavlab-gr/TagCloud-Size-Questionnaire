package tag.cloud.app;

/**
 *
 * @author georgia
 */

public class File {
    
    private String name;   
    
    public File() {                
        name="out.txt";
    }  

    public String getName() {
        return name;
    }
 
    /* changes the output file name */
    public void setName(String name) {
        String n=replaceWithTime(name);
        this.name = n+".txt";
    }   
    
    /* Replaces special characters of the timestamp */
    private String replaceWithTime(String time)
    {
        int i;
        char[] ch = new char[time.length()];
        for(i=0; i<time.length(); i++)
        {
            ch[i]=time.charAt(i);
            if(ch[i]==':')
                ch[i]='_';
            else if(ch[i]=='.')
                ch[i]='_';
            else if(ch[i]=='-')
                ch[i]='_';
        }
        String s = String.valueOf(ch);
        return s;
    }
       
    
}
