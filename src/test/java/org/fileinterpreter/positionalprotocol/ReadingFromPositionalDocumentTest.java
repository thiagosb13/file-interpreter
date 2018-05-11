package org.fileinterpreter.positionalprotocol;

import org.fileinterpreter.core.Document;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.text.IsEmptyString.isEmptyString;
import static org.junit.Assert.assertThat;

public class ReadingFromPositionalDocumentTest {

    @Test
    public void shouldFillAllOfPropertiesOfTheObject() {
        Document document = new PositionalDocumentSample();
        document.setLineDelimiter("~");
        document.setText("1-00                JOHN DOE                      ~2-00                JOE BLACK                     ");
        
        PositionalLineSample line1 = (PositionalLineSample)document.getLines().findFirst().get();
        assertThat(line1.userID.getValue(), is("1-00"));
        assertThat(line1.name.getValue(), is("JOHN DOE"));
        
        PositionalLineSample line2 = (PositionalLineSample)document.getLines().skip(1).findFirst().get();
        assertThat(line2.userID.getValue(), is("2-00"));
        assertThat(line2.name.getValue(), is("JOE BLACK"));
    }

    @Test
    public void shouldFillOnlyTheFirstLineWithTheText() {
        Document document = new PositionalDocumentSample();
        document.setLineDelimiter("~");
        document.setText("1-00                JOHN DOE                      ");

        PositionalLineSample line1 = (PositionalLineSample)document.getLines().findFirst().get();
        assertThat(line1.userID.getValue(), is("1-00"));
        assertThat(line1.name.getValue(), is("JOHN DOE"));

        PositionalLineSample line2 = (PositionalLineSample)document.getLines().skip(1).findFirst().get();
        assertThat(line2.userID.getValue(), isEmptyString());
        assertThat(line2.name.getValue(), is(equalTo("NC")));
    }
}
