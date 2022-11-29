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
    void main() {

    }

    @org.junit.jupiter.api.Test
    void mouseReleased() {

    }
}