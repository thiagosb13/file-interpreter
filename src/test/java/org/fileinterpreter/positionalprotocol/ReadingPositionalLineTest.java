package org.fileinterpreter.positionalprotocol;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class ReadingPositionalLineTest {
    
    @Test
    public void shouldSplitFieldsFillInRawValuesBasedOnConfiguration() {
        PositionalLineSample sample = new PositionalLineSample();
        sample.setLineValue("1-00                JOHN DOE                      ");
        
        assertThat(sample.userID.getRawValue(), is("1-00                "));
        assertThat(sample.name.getRawValue(), is("JOHN DOE                      "));
    }

    @Test
    public void shouldSplitFieldsAndTrimValuesBasedOnConfiguration() {
        PositionalLineSample sample = new PositionalLineSample();
        sample.setLineValue("1-00                JOHN DOE                      ");

        assertThat(sample.userID.getValue(), is("1-00"));
        assertThat(sample.name.getValue(), is("JOHN DOE"));
    }

    @Test
    public void whenTextLineSizeIsLessThanPositionalLineSizeObjectShouldFillInFieldsWithEmptyValue() {
        PositionalLineSample sample = new PositionalLineSample();
        sample.setLineValue("1-00                JOHN DOE      ");
        
        assertThat(sample.userID.getValue(), is("1-00"));
        assertThat(sample.name.getValue(), is(""));
    }
}
