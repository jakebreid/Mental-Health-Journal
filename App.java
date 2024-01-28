import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.io.File;
import java.io.FileReader;
import java.awt.event.*;
import java.time.LocalDateTime;  
import java.time.format.DateTimeFormatter;  


public class App implements ActionListener  {
    static int i = -1; 
    public static void main(String[] args) throws Exception 
    {
    boolean firstTime = true; 
    File f1 = new File("data.txt");
    if (f1.createNewFile() == false)
    {
        firstTime = false; 
    }
    File f2= new File("journal.txt");
    File f3 = new File("analysis.txt");
    FileWriter writeD = new FileWriter("data.txt", true);
    FileWriter writeJ = new FileWriter("journal.txt", true);
    FileWriter writeA = new FileWriter("analysis.txt", false);
    FileReader readD = new FileReader(f1);
    FileReader readJ = new FileReader(f2);
    if (firstTime)
    {
        writeD.write("1");
    } 
    JFrame ui = new JFrame("myJournal");
       ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       ui.setSize(1000,150);
       JPanel panel = new JPanel();
       JLabel label = new JLabel("Enter text:");
       JLabel label2 = new JLabel("test");
       JTextField tf = new JTextField(50);
       final JButton next = new JButton("Next");
       panel.add(label);
       panel.add(tf);
       panel.add(next);
       JLabel currentQ = new JLabel("");
       JLabel description = new JLabel("<html>Welcome to my Journal!<P>My Journal is an easy way to track mental and physical health trends.<P>To start, press NEXT!</html>", SwingConstants.LEFT);
       ui.getContentPane().add(BorderLayout.SOUTH, panel);
       ui.getContentPane().add(BorderLayout.NORTH, description);
       ui.getContentPane().add(BorderLayout.CENTER, currentQ);
       ui.setVisible(true);
       String info = "<html>Over the last 2 weeks (or since your last entry) how often have you had the following problems? <P> Please enter a cooersponding number with what you feel resonates with you most. 1 = Not at all. 2 = Sometimes. 3 = Often. 4 = Very Frequently. </html>";
        final String[] qs = {"Little interest or pleasure in doing things.", "Feeling down, depressed, or hopeless.", "Trouble falling or staying asleep, or sleeping too much.", "Feeling tired or having little energy.",
        "Poor appetite or overeating.", "Feeling bad about yourself-or that you are a failure or have let yourself or your family down.", "Trouble concentrating on things.","Feeling anxious or overwhelmed.", "Experienced a panic attack or feeling of losing control.", "Drank too much or overused a substance.",  "Thoughts that you would be better off dead, or of hurting youself.", "Great! Thanks for the data. Please enter a general description of how you have been feeling recently. "}; 
        long millis = System.currentTimeMillis();  
        java.util.Date date = new java.util.Date(millis);    
        writeJ.write("Entry on: " + date + "\n\n");
        next.addActionListener(new ActionListener() 
       {
        public void actionPerformed(ActionEvent e)
    {
        if(i == -1)
        {
            i = 0; 
            String question = qs[i];
            String yes = info + "\n";
            currentQ.setText(question);
            description.setText(yes);

        }
        else if (i != 11)
    {
          if (!(tf.getText().equals("1") || tf.getText().equals("2") || tf.getText().equals("3") || tf.getText().equals("4"))) 
        {
            String errorMsg = qs[i] + " Please enter a valid input";
            currentQ.setText(errorMsg);
            tf.setText("");
        }
        else 
        {
        String question = qs[i];
        String input = tf.getText();
       
        String writeTo = "" + question + ": " + input + "\n";
        currentQ.setText(qs[++i]);
        System.out.println(writeTo);
        try {
            writeJ.write(writeTo);
            writeD.write(input);
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
       
        tf.setText("");
    }
        
    }
    else 
    {
        String report = tf.getText();
         try {
            writeJ.write("\nUsers General Feeling: " + report + "\n\n");
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        description.setText("Thanks for your info. Data has been entered into your journal. Have a great day!");
        currentQ.setText("");
        tf.setText("");
        i++;
    }
    }
       });
    while(i != 12)
    {
        Thread.sleep((long)0.25);
    }
    writeD.close();
    int[] myNums = new int[(int)(f1.length())];
    for (int j = 0; j < f1.length(); j++)
    {
     myNums[j] = readD.read();
    }
    int[] data = new int[11];
    int temp = 0; 
    for(int j = 0; j < 11; j++)
    {
        for(int c = j; c < f1.length(); c+= 11)
        {
            temp++;
            data[j] += Character.getNumericValue(myNums[c]);
        }
        data[j] = data[j] / temp;
        temp = 0; 
    }
    for (int j = 0; j < 11; j++)
    {
        writeA.write("Average rating for: " + qs[j] + data[j] + "\n");
    }
    writeJ.close();
    writeA.close();
    readD.close();
    readJ.close();
    }
    public void actionPerformed(ActionEvent e)
    {

    }

       
     
    

}
