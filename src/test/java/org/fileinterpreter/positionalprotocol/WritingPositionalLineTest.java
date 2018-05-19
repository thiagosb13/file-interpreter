package org.fileinterpreter.positionalprotocol;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.fileinterpreter.annotation.PositionalLine;
import org.fileinterpreter.positionalprotocol.document.PositionalLineRTLSample;
import org.fileinterpreter.positionalprotocol.document.PositionalLineSample;
import org.fileinterpreter.positionalprotocol.document.PositionalLineWithDefaultFillingSample;
import org.junit.Test;

public class WritingPositionalLineTest {
	@Test
	public void shouldBuildTheObjectBasedOnFieldsDefinition() throws InstantiationException, IllegalAccessException {
		PositionalLineSample sample = new PositionalLineSample();
		sample.name = "JOHN DOE";
		sample.userID = "1-00";
		
		PositionalLine positionalLine = sample.getClass().getDeclaredAnnotation(PositionalLine.class);
		
		assertThat(positionalLine.parser().newInstance().toContent(sample), 
		           is("1-00                JOHN DOE                      "));
	}

	@Test
    public void shouldCompleteFieldValueWithDefaultFilling() throws InstantiationException, IllegalAccessException {
	    PositionalLineWithDefaultFillingSample sample = new PositionalLineWithDefaultFillingSample();
        sample.name = "JOHN DOE";
        sample.userID = "1-00";
        
        PositionalLine positionalLine = sample.getClass().getDeclaredAnnotation(PositionalLine.class);
        
        assertThat(positionalLine.parser().newInstance().toContent(sample), 
                   is("1-00################JOHN DOE**********************"));
    }
    
    @Test
    public void shouldTruncFieldsToSizeDefinition() throws InstantiationException, IllegalAccessException {
        PositionalLineSample sample = new PositionalLineSample();
        sample.name = "JOHN DOE 999999999999999999999999999999";
        sample.userID = "1-00 99999999999999999999999999999999";

        PositionalLine positionalLine = sample.getClass().getDeclaredAnnotation(PositionalLine.class);
        
        assertThat(positionalLine.parser().newInstance().toContent(sample), 
                   is("1-00 999999999999999JOHN DOE 999999999999999999999"));
    }

    @Test
    public void whenValueIsNotFilledShouldUseDefaultValue() throws InstantiationException, IllegalAccessException {
        PositionalLineSample sample = new PositionalLineSample();
        sample.userID = "1-00";
        
        PositionalLine positionalLine = sample.getClass().getDeclaredAnnotation(PositionalLine.class);
        
        assertThat(positionalLine.parser().newInstance().toContent(sample), 
                   is("1-00                NC                            "));
    }
    
    @Test
    public void ifAFieldIsConfiguredToRTLShouldWriteItRightAligned() throws InstantiationException, IllegalAccessException {
        PositionalLineRTLSample sample = new PositionalLineRTLSample();
        sample.name = "JOHN DOE";
        sample.userID = "1-00";
        
        PositionalLine positionalLine = sample.getClass().getDeclaredAnnotation(PositionalLine.class);
        
        assertThat(positionalLine.parser().newInstance().toContent(sample), is("1-00                                      JOHN DOE"));
    }
}
