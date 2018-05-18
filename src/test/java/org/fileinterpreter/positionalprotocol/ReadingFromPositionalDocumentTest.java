package org.fileinterpreter.positionalprotocol;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.text.IsEmptyString.isEmptyString;
import static org.junit.Assert.assertThat;

import org.fileinterpreter.core.Document;
import org.junit.Test;

public class ReadingFromPositionalDocumentTest {

    @Test
    public void shouldFillAllOfPropertiesOfTheObject() {
        Document document = new PositionalDocumentSample();
        document.setLineDelimiter("~");
        document.parse("1-00                JOHN DOE                      ~2-00                JOE BLACK                     ");
        
        PositionalLineSample line1 = (PositionalLineSample)document.getLines().findFirst().get();
        assertThat(line1.userID.trim(), is("1-00"));
        assertThat(line1.name.trim(), is("JOHN DOE"));
        
        PositionalLineSample line2 = (PositionalLineSample)document.getLines().skip(1).findFirst().get();
        assertThat(line2.userID.trim(), is("2-00"));
        assertThat(line2.name.trim(), is("JOE BLACK"));
    }

    @Test
    public void shouldFillOnlyTheFirstLineWithTheText() {
        Document document = new PositionalDocumentSample();
        document.setLineDelimiter("~");
        document.parse("1-00                JOHN DOE                      ");

        PositionalLineSample line1 = (PositionalLineSample)document.getLines().findFirst().get();
        assertThat(line1.userID.trim(), is("1-00"));
        assertThat(line1.name.trim(), is("JOHN DOE"));

        PositionalLineSample line2 = (PositionalLineSample)document.getLines().skip(1).findFirst().get();
        assertThat(line2.userID, isEmptyString());
        assertThat(line2.name, is(equalTo("NC")));
    }
}
