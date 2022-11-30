import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.awt.*;
import java.util.ArrayList;

public class annotation {
    // Arraylist for selected lines
    ArrayList selectedLines;

    annotation(){

        selectedLines = new ArrayList<String>();

    }

    public void highlightLines(JTextArea t){
        DefaultHighlighter.DefaultHighlightPainter yellow = new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);
        String wholeText = t.getText();
        String selectedText = t.getSelectedText();
        Highlighter highLight = t.getHighlighter();
        if(selectedText != null) {
            selectedLines.add(selectedText);
            try {
                highLight.addHighlight(wholeText.indexOf(selectedText), wholeText.indexOf(selectedText) + selectedText.length(), yellow);
            } catch (BadLocationException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
