package org.fileinterpreter.positionalprotocol;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class ReadingPositionalLineTest {
    
    @Test
    public void shouldSplitFieldsFillInValuesBasedOnConfiguration() {
        PositionalLineSample sample = new PositionalLineSample();
        sample.parse("1-00                JOHN DOE                      ");
        
        assertThat(sample.userID, is("1-00                "));
        assertThat(sample.name, is("JOHN DOE                      "));
    }

    @Test
    public void whenTextLineSizeIsLessThanPositionalLineSizeObjectShouldFillInFieldsWithEmptyValue() {
        PositionalLineSample sample = new PositionalLineSample();
        sample.parse("1-00                JOHN DOE      ");
        
        assertThat(sample.userID.trim(), is("1-00"));
        assertThat(sample.name.trim(), is(""));
    }
}
