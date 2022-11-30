import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.awt.*;
import java.util.ArrayList;

// Class for annotating in PPALMS {Design Document 4.2.4}
public class annotation {

    // Arraylist for selected lines {Design Document 4.2.4.1}
    ArrayList selectedLines;

    // Constructor
    annotation(){

        // Initialization of selectedLines ArrayList
        selectedLines = new ArrayList<String>();

    }

    // Function for highlighting user selected lines {Design Document 4.2.4.2}
    public void highlightLines(JTextArea t){

        // Defining color of highlighter
        DefaultHighlighter.DefaultHighlightPainter yellow = new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);

        // Fetching selected text, the whole text, and the highlighter object from text area
        String wholeText = t.getText();
        String selectedText = t.getSelectedText();
        Highlighter highLight = t.getHighlighter();

        // Excluding when user selects no lines
        if(selectedText != null) {

            // Add selected text to ArrayList
            selectedLines.add(selectedText);

            // Highlight selected text with highlighter
            try {
                highLight.addHighlight(wholeText.indexOf(selectedText), wholeText.indexOf(selectedText) + selectedText.length(), yellow);
            } catch (BadLocationException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
