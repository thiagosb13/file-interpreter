package org.fileinterpreter.positionalprotocol;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.fileinterpreter.annotation.PositionalLine;
import org.junit.Test;

public class ReadingPositionalLineTest {
    
    @Test
    public void shouldSplitFieldsFillInValuesBasedOnConfiguration() throws InstantiationException, IllegalAccessException {
        PositionalLineSample sample = new PositionalLineSample();
        PositionalLine positionalLine = sample.getClass().getDeclaredAnnotation(PositionalLine.class);
        positionalLine.parser().newInstance().parse("1-00                JOHN DOE                      ", sample);
        
        assertThat(sample.userID, is("1-00                "));
        assertThat(sample.name, is("JOHN DOE                      "));
    }

    @Test
    public void whenTextLineSizeIsLessThanPositionalLineSizeObjectShouldFillInFieldsWithEmptyValue() throws InstantiationException, IllegalAccessException {
        PositionalLineSample sample = new PositionalLineSample();
        PositionalLine positionalLine = sample.getClass().getDeclaredAnnotation(PositionalLine.class);
        positionalLine.parser().newInstance().parse("1-00                JOHN DOE      ", sample);
        
        assertThat(sample.userID.trim(), is("1-00"));
        assertThat(sample.name.trim(), is(""));
    }
}
