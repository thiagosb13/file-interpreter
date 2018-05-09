package org.fileinterpreter.positionalline;

import java.util.ArrayList;

import org.fileinterpreter.core.Document;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ReadingFromPositionalDocumentTest {

    @Test
    public void shouldFillAllOfPropertiesOfTheObject() {
        Document document = new DocumentSample();
        document.setLineDelimiter("~");
        document.setText("1-00                JOHN DOE                      ~2-00                JOE BLACK                     ");
        
        assertThat(((PositionalLineSample)document.getLines().get(0)).userID.getValue(), is("1-00"));
        assertThat(((PositionalLineSample)document.getLines().get(0)).name.getValue(), is("JOHN DOE"));
        assertThat(((PositionalLineSample)document.getLines().get(1)).userID.getValue(), is("2-00"));
        assertThat(((PositionalLineSample)document.getLines().get(1)).name.getValue(), is("JOE BLACK"));
    }
    
    private class DocumentSample extends Document {
        public DocumentSample() {
            lines = new ArrayList<>();
            
            PositionalLineSample line1 = new PositionalLineSample();
            line1.setLineValue("1-00                JOHN DOE                      ");
            lines.add(line1);
            
            PositionalLineSample line2 = new PositionalLineSample();
            line2.setLineValue("2-00                JOE BLACK                     ");
            lines.add(line2);
        }
    }
}
