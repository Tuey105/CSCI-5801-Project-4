import javax.swing.*;

import java.io.*;
import java.awt.event.*;
import javax.swing.plaf.metal.*;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;
import javax.swing.text.BadLocationException;

import java.awt.*;
import java.util.*;
import java.util.List;

// Class for PPALMS {Design Document 4.2.2}
class ppalms extends JFrame implements ActionListener, MouseListener{

    // Text area
    JTextArea text;

    // Frame
    JFrame frame;

    // File for importing
    File file;

    // annotation class
    annotation annotate;

    String type;

    // Constructor
    ppalms()
    {

        //Intialization
        file = null;
        annotate = new annotation();
        frame = new JFrame("PPALMS");
        text = new JTextArea();
        type = "reorder";

        // Create a menubar
        JMenuBar mb = new JMenuBar();

        JMenu problemType = new JMenu("Problem Type");

        JMenuItem reorder = new JMenuItem("Reorder");
        reorder.addActionListener(this);
        problemType.add(reorder);

        JMenuItem mc = new JMenuItem("Multiple Choice");
        mc.addActionListener(this);
        problemType.add(mc);

        JMenuItem fb = new JMenuItem("Fill in the Blank");
        fb.addActionListener(this);
        problemType.add(fb);

        mb.add(problemType);

        // Create import button
        JMenuItem importButton = new JMenuItem("Import");
        importButton.addActionListener(this);
        mb.add(importButton);

        // Create generate button
        JMenuItem generate = new JMenuItem("Generate");
        generate.addActionListener(this);
        mb.add(generate);

        // Create scrollbar
        JScrollPane scroll = new JScrollPane(text, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Text area properties
        text.addMouseListener(this);
        text.setLineWrap(true);
        text.setWrapStyleWord(true);

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
        JFileChooser fileChooser = new JFileChooser("f:");

        // Invoke the showsOpenDialog function to show the save dialog
        int r = fileChooser.showOpenDialog(null);

        // If the user selects a file
        if (r == JFileChooser.APPROVE_OPTION) {
            // Set the label to the path of the selected directory
            file = new File(fileChooser.getSelectedFile().getAbsolutePath());

            try {
                String s1, sl;
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                sl = br.readLine();

                // Take the input from the file
                while ((s1 = br.readLine()) != null) {
                    sl = sl + "\n" + s1;
                }

                // Set the text
                text.setText(sl);
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

        String annotatedLines[];

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
            annotatedLines = Arrays.copyOf(tempArray, tempArray.length, String[].class);
        }

        // Or use whole text area and store to array
        else{
            String fileText = text.getText();
            annotatedLines = fileText.split("\\r?\\n");
        }

        if(type.equals("reorder")){
            //Randomize array
            Random r = new Random();
            for(int i = 0; i < annotatedLines.length; i++){
                int randindex = r.nextInt(annotatedLines.length);
                String temp = annotatedLines[randindex];
                annotatedLines[randindex] = annotatedLines[i];
                annotatedLines[i] = temp;
            }

            // Write randomized array of annotatedLines to file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("generatedParsonProblems.txt", false))) {
                for(int i = 0; i < annotatedLines.length; i++){
                    // Exclude comments
                    if(!annotatedLines[i].contains("//")){
                        writer.write(annotatedLines[i]);
                        writer.newLine();
                    }
                }
                writer.flush();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            System.out.println("file generate success");
        }

        else if(type.equals("mc")){
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("generatedParsonProblems.txt", false))) {
                Random r = new Random();
                for(int i = 0; i < annotatedLines.length; i++){
                    // Exclude comments
                    if(!annotatedLines[i].contains("//")){

                        String words[] = annotatedLines[i].split(" ");
                        int randInt = r.nextInt(words.length);
                        String choosenWord = words[randInt];
                        while(choosenWord.length() <= 1){
                            int anotherRandInt = r.nextInt(words.length);
                            choosenWord = words[anotherRandInt];
                        }

                        String blank = "";
                        for(int j = 0; j < choosenWord.length(); j++){
                            blank += '_';
                        }

                        annotatedLines[i] = annotatedLines[i].replaceFirst(choosenWord, blank);

                        ArrayList<String> options = new ArrayList<>();
                        options.add(choosenWord);
                        for(int k = 0 ; k < 3; k++){
                            String shuffledWord = "";
                            List<String> wordChars = Arrays.asList(choosenWord.split(""));
                            Collections.shuffle(wordChars);
                            for(String c : wordChars){
                                shuffledWord += c;
                            }
                            options.add(shuffledWord);
                        }

                        writer.write(annotatedLines[i]);
                        writer.newLine();

                        char alphabet = 'a';
                        while(options.size() > 0){
                            int randIndex = r.nextInt(options.size());
                            writer.write(alphabet + ") " + options.get(randIndex));
                            alphabet++;
                            options.remove(randIndex);
                            writer.newLine();
                        }

                        writer.newLine();
                    }

                }
                writer.flush();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            System.out.println("file generate success");
        }

        else if(type.equals("fb")){
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("generatedParsonProblems.txt", false))) {
                Random r = new Random();
                for(int i = 0; i < annotatedLines.length; i++){
                    // Exclude comments
                    if(!annotatedLines[i].contains("//")){
                        String words[] = annotatedLines[i].split(" ");
                        int randInt = r.nextInt(words.length);
                        String choosenWord = words[randInt];
                        while(choosenWord.length() <= 1){
                            int anotherRandInt = r.nextInt(words.length);
                            choosenWord = words[anotherRandInt];
                        }

                        String blank = "";
                        for(int j = 0; j < choosenWord.length(); j++){
                            blank += '_';
                        }

                        annotatedLines[i] = annotatedLines[i].replaceFirst(choosenWord, blank);

                        writer.write(annotatedLines[i]);
                        writer.newLine();
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

        // Action for reorder button pressed
        else if (s.equals("Reorder")){
            type = "reorder";
        }

        // Action for multiple choice button pressed
        else if (s.equals("Multiple Choice")){
            type = "mc";
        }

        // Action for multiple choice button pressed
        else if (s.equals("Fill in the Blank")){
            type = "fb";
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
        annotate.highlightLines(text);
    }

    @Override
    public void mouseEntered(java.awt.event.MouseEvent e) {}


    @Override
    public void mouseExited(java.awt.event.MouseEvent e) {}
}
