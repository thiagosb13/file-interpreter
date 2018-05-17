package org.fileinterpreter.positionalprotocol;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class WritingPositionalLineTest {
	@Test
	public void shouldBuildTheObjectBasedOnFieldsDefinition() {
		PositionalLineSample sample = new PositionalLineSample();
		sample.name.value = "JOHN DOE";
		sample.userID.value = "1-00";
		
		assertThat(sample.toContent(), is("1-00                JOHN DOE                      "));
	}

	@Test
    public void shouldCompleteFieldValueWithDefaultFilling() {
	    PositionalLineWithDefaultFillingSample sample = new PositionalLineWithDefaultFillingSample();
        sample.name.value = "JOHN DOE";
        sample.userID.value = "1-00";
        
        assertThat(sample.toContent(), is("1-00################JOHN DOE**********************"));
    }
    
    @Test
    public void shouldTruncFieldsToSizeDefinition() {
        PositionalLineSample sample = new PositionalLineSample();
        sample.name.value = "JOHN DOE 999999999999999999999999999999";
        sample.userID.value = "1-00 99999999999999999999999999999999";

        assertThat(sample.toContent(), is("1-00 999999999999999JOHN DOE 999999999999999999999"));
    }

    @Test
    public void whenValueIsNotFilledShouldUseDefaultValue() {
        PositionalLineSample sample = new PositionalLineSample();
        sample.userID.value = "1-00";
        
        assertThat(sample.toContent(), is("1-00                NC                            "));
    }
    
    @Test
    public void ifAFieldIsConfiguredToRTLShouldWriteItRightAligned() {
        PositionalLineRTLSample sample = new PositionalLineRTLSample();
        sample.name.value = "JOHN DOE";
        sample.userID.value = "1-00";
        
        assertThat(sample.toContent(), is("1-00                                      JOHN DOE"));
    }
}
