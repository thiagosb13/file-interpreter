package org.fileinterpreter.positionalprotocol;

import org.fileinterpreter.core.Document;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class WritingToPositionalDocumentTest {

    @Test
    public void shouldTransfomLineIntoPlainText() {
        Document document = new PositionalDocumentSample();
        PositionalLineSample line1 = (PositionalLineSample)document.getLines().findFirst().get();
        line1.userID.value = "1-00";
        line1.name.value = "JOHN DOE";

        PositionalLineSample line2 = (PositionalLineSample)document.getLines().skip(1).findFirst().get();
        line2.userID.value = "2-00";
        line2.name.value = "JOE BLACK";
        
        assertThat(document.toContent(), is("1-00                JOHN DOE                      \r\n2-00                JOE BLACK                     "));
    }
}
