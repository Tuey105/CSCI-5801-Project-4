import org.junit.Test;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ppalmsTest {

    @org.junit.jupiter.api.Test
    void importing() {
        ppalms p = new ppalms();
        ActionEvent e = new ActionEvent(new Object(), 0, "Import");
        //p.f = new File("/../generatedParsonProblems.txt");
        p.actionPerformed(e);
        //file loaded figuring out if it's displayed in main screen
        String text = p.text.getText();
        assertNotEquals("",text);
    }


    @org.junit.jupiter.api.Test
    void importing_nonselection() {
        ppalms p = new ppalms();
        ActionEvent e = new ActionEvent(new Object(), 0, "Import");
        //p.f = new File("/../generatedParsonProblems.txt");
        p.actionPerformed(e);
        //file loaded figuring out if it's displayed in main screen
        String text = p.text.getText();
        //check if there are no selection and notified
        assertEquals("",text);
    }

    @org.junit.jupiter.api.Test
    @Test
    void highlighting() {
        annotation a = new annotation();
        ppalms p = new ppalms();
        p.text.setText("something is here");
        p.text.select(0, p.text.getText().length());
        a.highlightLines(p.text);
        assertEquals("something is here", a.selectedLines.get(0));


    }

    @org.junit.jupiter.api.Test
    void generating() throws IOException {
        ppalms p = new ppalms();
        ActionEvent e = new ActionEvent(new Object(), 0, "Import");
        p.actionPerformed(e);
        //ask to select code file
        ActionEvent a = new ActionEvent(new Object(), 0, "Generate");
        p.actionPerformed(a);
        //parson problems generated
        FileReader fr = new FileReader("generatedParsonProblems.txt");
        char[] buf = new char[50];
        fr.read(buf);
        //read from txt file
        assertNotEquals('\0',buf[1]);
        //check if empty
    }


    @org.junit.jupiter.api.Test
    void generating_randomness() throws IOException {
        ppalms p = new ppalms();
        ActionEvent e = new ActionEvent(new Object(), 0, "Import");
        p.actionPerformed(e);
        //ask to select code file
        String text = p.text.getText();
        ActionEvent a = new ActionEvent(new Object(), 0, "Generate");
        p.actionPerformed(a);
        //parson problems generated
        FileReader fr = new FileReader("generatedParsonProblems.txt");
        char[] buf = new char[50];
        fr.read(buf);
        String first = String.valueOf(buf[1]);
        //check the first element in txt
        assertNotEquals(text,first);
        //check if is properly randomized
    }
}

