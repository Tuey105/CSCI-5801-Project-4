import javax.swing.*;

import java.io.*;
import java.awt.event.*;
import javax.swing.plaf.metal.*;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;
import javax.swing.text.BadLocationException;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
// Class for PPALMS {Design Document 4.2.2}
class ppalms extends JFrame implements ActionListener, MouseListener{

    // Text area
    JTextArea t;

    // Frame
    JFrame frame;

    // File for importing
    File f;

    // annotation class
    annotation annotate;

    // Constructor
    ppalms()
    {

        //Intialization
        f = null;
        annotate = new annotation();
        frame = new JFrame("PPALMS");
        t = new JTextArea();

        // Create a menubar
        JMenuBar mb = new JMenuBar();

        // Create import button
        JMenuItem i = new JMenuItem("Import");
        i.addActionListener(this);
        mb.add(i);

        // Create generate button
        JMenuItem generate = new JMenuItem("Generate");
        generate.addActionListener(this);
        mb.add(generate);

        // Create scrollbar
        JScrollPane scroll = new JScrollPane(t, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Text area properties
        t.addMouseListener(this);
        t.setLineWrap(true);
        t.setWrapStyleWord(true);

        //  Frame properties
        frame.setJMenuBar(mb);
        frame.add(scroll);
        frame.setSize(1000, 1000);
        frame.setVisible(true);
    }

    // Function for importing file {Design Document 4.2.2.2}
    public void importFile(){

        // Clearing all selected lines from previous import
        annotate.selectedLines.clear();

        // Create an object of JFileChooser class
        JFileChooser j = new JFileChooser("f:");

        // Invoke the showsOpenDialog function to show the save dialog
        int r = j.showOpenDialog(null);

        // If the user selects a file
        if (r == JFileChooser.APPROVE_OPTION) {
            // Set the label to the path of the selected directory
            f = new File(j.getSelectedFile().getAbsolutePath());

            try {
                String s1, sl;
                FileReader fr = new FileReader(f);
                BufferedReader br = new BufferedReader(fr);
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

    // Function for generating parson problems object {Design Document 4.2.2.2}
    public void generate(){

        String lines[];

        // Use annotate's selectedLines and convert to array
        if(!annotate.selectedLines.isEmpty()){
            for(int i = 0; i < annotate.selectedLines.size(); i++){
                String elementLines[] = annotate.selectedLines.get(i).toString().split("\\r?\\n");
                annotate.selectedLines.remove(i);
                for(int j = 0; j < elementLines.length; j++){
                    annotate.selectedLines.add(elementLines[j]);
                }
            }
            Object tempArray[] = annotate.selectedLines.toArray();
            lines = Arrays.copyOf(tempArray, tempArray.length, String[].class);
        }

        // Or use whole text area and store to array
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

        // Write randomized array of lines to file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("generatedParsonProblems.txt", false))) {
            for(int i = 0; i < lines.length; i++){

                // Exclude comments
                if(!lines[i].contains("//")){
                    writer.write(lines[i]);
                    writer.newLine();
                }
            }
            writer.flush();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        System.out.println("file generate success");
    }

    // If a button is pressed
    public void actionPerformed(ActionEvent e)
    {
        String s = e.getActionCommand();

        // Action for import button pressed
        if (s.equals("Import")) {
            importFile();
        }

        // Action for generate button pressed
        else if (s.equals("Generate")){
            generate();
        }
    }
    public static void main(String args[])
    {
        // PPALMS Object Creation
        ppalms p = new ppalms();
    }


    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) {}


    @Override
    public void mousePressed(java.awt.event.MouseEvent e) {}


    @Override
    public void mouseReleased(java.awt.event.MouseEvent e) {

        //Highlight selected lines
        annotate.highlightLines(t);
    }

    @Override
    public void mouseEntered(java.awt.event.MouseEvent e) {}


    @Override
    public void mouseExited(java.awt.event.MouseEvent e) {}
}
