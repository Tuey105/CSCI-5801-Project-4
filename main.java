import javax.swing.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.plaf.metal.*;
import java.awt.*;
import java.util.Random;
class ppalms extends JFrame implements ActionListener {
    // Text component
    JTextArea t;
 
    // Frame
    JFrame frame;

    // File
    File f;
 
    // Constructor
    ppalms()
    {
        f = null;

        // Create a frame
        frame = new JFrame("PPALMS");
 
        try {
            // Set metal look and feel
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
 
            // Set theme to ocean
            MetalLookAndFeel.setCurrentTheme(new OceanTheme());
        }
        catch (Exception e) {
        }
 
        // Text component
        t = new JTextArea();
 
        // Create a menubar
        JMenuBar mb = new JMenuBar();
 
        // Create import button
        JMenuItem i = new JMenuItem("Import");
        i.addActionListener(this);
        mb.add(i);

        JMenuItem generate = new JMenuItem("Generate");
        generate.addActionListener(this);
        mb.add(generate);

        // Create scrollbar
        JScrollPane scroll = new JScrollPane(t, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        t.setLineWrap(true);
        t.setWrapStyleWord(true);
        frame.setJMenuBar(mb);
        frame.add(scroll);
        frame.setSize(500, 500);
        frame.setVisible(true);
    }
 
    // If a button is pressed
    public void actionPerformed(ActionEvent e)
    {
        String s = e.getActionCommand();
 
        if (s.equals("Import")) {
            // Create an object of JFileChooser class
            JFileChooser j = new JFileChooser("f:");
 
            // Invoke the showsOpenDialog function to show the save dialog
            int r = j.showOpenDialog(null);
 
            // If the user selects a file
            if (r == JFileChooser.APPROVE_OPTION) {
                // Set the label to the path of the selected directory
                f = new File(j.getSelectedFile().getAbsolutePath());
 
                try {
                    // String
                    String s1 = "", sl = "";
 
                    // File reader
                    FileReader fr = new FileReader(f);
 
                    // Buffered reader
                    BufferedReader br = new BufferedReader(fr);
 
                    // Initialize sl
                    sl = br.readLine();
 
                    // Take the input from the file
                    while ((s1 = br.readLine()) != null) {
                        sl = sl + "\n" + s1;
                    }
 
                    // Set the text
                    t.setText(sl);
                }
                catch (Exception evt) {
                    JOptionPane.showMessageDialog(frame, evt.getMessage());
                }
            }
            // If the user cancelled the operation
            else
                JOptionPane.showMessageDialog(frame, "the user cancelled the operation");
        }
        else if (s.equals("Generate")){
            //Read text area
            String fileText = t.getText();

            //Store lines in an array
            String lines[] = fileText.split("\\r?\\n");

            //Randomize array
            Random r = new Random();
            for(int i = 0; i < lines.length; i++){
                int randindex = r.nextInt(lines.length);
                String temp = lines[randindex];
                lines[randindex] = lines[i];
                lines[i] = temp;
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter("generatedParsonProblems.txt", false))) {
                for(int i = 0; i < lines.length; i++){
                    //add way to exclude comments and other scenarios here
                    if(!lines[i].contains("//")){
                        writer.write(lines[i].toString());
                        writer.newLine();
                    }
                }
                writer.flush();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            System.out.println("file generate success");
        }
    }
 
    // Main class
    public static void main(String args[])
    {
        ppalms p = new ppalms();
    }
}