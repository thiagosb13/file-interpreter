package org.fileinterpreter.positionalprotocol;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.fileinterpreter.core.Document;
import org.junit.Test;

public class WritingToPositionalDocumentTest {

    @Test
    public void shouldTransfomLineIntoPlainText() {
        Document document = new PositionalDocumentSample();
        PositionalLineSample line1 = (PositionalLineSample)document.getLines().get(0);
        line1.userID.setValue("1-00");
        line1.name.setValue("JOHN DOE");

        PositionalLineSample line2 = (PositionalLineSample)document.getLines().get(1);
        line2.userID.setValue("2-00");
        line2.name.setValue("JOE BLACK");
        
        assertThat(document.getText(), is("1-00                JOHN DOE                      \r\n2-00                JOE BLACK                     "));
    }
}
