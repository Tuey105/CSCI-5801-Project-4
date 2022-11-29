import javax.swing.*;
import javax.swing.event.MouseInputListener;

import java.io.*;
import java.awt.event.*;
import javax.swing.plaf.metal.*;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Document;
import javax.swing.text.Highlighter;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;
import javax.swing.text.Highlighter.Highlight;
import javax.swing.text.BadLocationException;
import org.w3c.dom.events.MouseEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
class ppalms extends JFrame implements ActionListener, MouseListener{
    // Text component
    JTextArea t;
 
    // Frame
    JFrame frame;

    // File
    File f;

    // Arraylist for selected lines
    ArrayList linesArraylist = new ArrayList<String>();
 
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

        t.addMouseListener(this);

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

            //Use linesArrayList and convert to array Or use whole textarea and store to array
            String lines[];
            if(!linesArraylist.isEmpty()){
                Object tempArray[] = linesArraylist.toArray();
                lines = Arrays.copyOf(tempArray, tempArray.length, String[].class);
            }
            else{
                String fileText = t.getText();
                lines = fileText.split("\\r?\\n");
            }

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
    public static void main(String args[])
    {
        ppalms p = new ppalms();
    }


    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) {
        // TODO Auto-generated method stub
        
    }


    @Override
    public void mousePressed(java.awt.event.MouseEvent e) {
        // TODO Auto-generated method stub
        // System.out.println(e.getSource());

    }


    @Override
    public void mouseReleased(java.awt.event.MouseEvent e) {
        // TODO Auto-generated method stub
        try{
            DefaultHighlightPainter yellow = new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);
            String wholeText = t.getText();
            String selectedText = t.getSelectedText();
            Highlighter highLight = t.getHighlighter();
            highLight.addHighlight(wholeText.indexOf(selectedText), wholeText.indexOf(selectedText) + selectedText.length(), yellow);
            linesArraylist.add(selectedText);
        }
        catch(BadLocationException ex){
            System.err.println("NO NO");
        }
    }

    @Override
    public void mouseEntered(java.awt.event.MouseEvent e) {
        // TODO Auto-generated method stub
        
    }


    @Override
    public void mouseExited(java.awt.event.MouseEvent e) {
        // TODO Auto-generated method stub
        
    }
}