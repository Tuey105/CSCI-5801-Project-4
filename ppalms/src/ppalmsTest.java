import java.awt.event.ActionEvent;
import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class ppalmsTest {

    @org.junit.jupiter.api.Test
    void importing() {
        ppalms p = new ppalms();
        ActionEvent e = new ActionEvent(new Object(), 0, "Import");
        //p.f = new File("/../generatedParsonProblems.txt");
        p.actionPerformed(e);
        String text = p.t.getText();
        assertNotEquals("",text);
    }

    @org.junit.jupiter.api.Test
    void highlighting() {
        ppalms p = new ppalms();
        ActionEvent e = new ActionEvent(new Object(), 0, "Import");
        p.actionPerformed(e);
        String highlight = p.t.getSelectedText();
        assertNotEquals("",highlight);
    }

    @org.junit.jupiter.api.Test
    void generating() throws IOException {
        ppalms p = new ppalms();
        ActionEvent e = new ActionEvent(new Object(), 0, "Import");
        p.actionPerformed(e);
        ActionEvent a = new ActionEvent(new Object(), 0, "Generate");
        p.actionPerformed(a);
        FileReader fr = new FileReader("generatedParsonProblems.txt");
        char[] buf = new char[50];
        fr.read(buf);
        assertNotEquals('\0',buf[1]);
    }
}
