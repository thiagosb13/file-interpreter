package org.fileinterpreter;

import static org.junit.Assert.assertEquals;

import org.fileinterpreter.core.PositionalField;
import org.fileinterpreter.core.PositionalLine;
import org.junit.Test;

public class ReadingPositionalLineTest {
    
    @Test
    public void shouldSplitFieldsFillInRawValuesBasedOnConfiguration() {
        PositionalLineSample sample = new PositionalLineSample();
        sample.setLineValue("1-00                JOHN DOE                      ");
        
        assertEquals("1-00                ", sample.userID.getRawValue());
        assertEquals("JOHN DOE                      ", sample.name.getRawValue());
    }

    @Test
    public void shouldSplitFieldsAndTrimValuesBasedOnConfiguration() {
        PositionalLineSample sample = new PositionalLineSample();
        sample.setLineValue("1-00                JOHN DOE                      ");

        assertEquals("1-00", sample.userID.getValue());
        assertEquals("JOHN DOE", sample.name.getValue());
    }

    @Test
    public void whenTextLineSizeIsLessThanPositionalLineSizeObjectShouldFillInFieldsWithEmptyValue() {
        PositionalLineSample sample = new PositionalLineSample();
        sample.setLineValue("1-00                JOHN DOE      ");
        
        assertEquals("1-00", sample.userID.getValue());
        assertEquals("", sample.name.getValue());
    }
    
    private class PositionalLineSample extends PositionalLine {
        public PositionalField userID;
        public PositionalField name;

        public PositionalLineSample() {
            userID = new PositionalField(this);
            userID.name = "User ID";
            userID.initialPos = 1;
            userID.size = 20;

            name = new PositionalField(this);
            name.name = "User Name";
            name.initialPos = 21;
            name.size = 30;
            name.defaultValue = "NC";
        }
    }
}
