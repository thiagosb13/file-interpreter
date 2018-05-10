package org.fileinterpreter.positionalline;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class WritingPositionalLineTest {
	@Test
	public void shouldBuildTheObjectBasedOnFieldsDefinition() {
		PositionalLineSample sample = new PositionalLineSample();
		sample.name.setValue("JOHN DOE");
		sample.userID.setValue("1-00");
		
		assertThat(sample.getLineValue(), is("1-00                JOHN DOE                      "));
	}

	@Test
    public void shouldCompleteFieldValueWithDefaultFilling() {
        PositionalLineSample sample = new PositionalLineSample();
        sample.name.withDefaultFilling('*');
        sample.name.setValue("JOHN DOE");
        sample.userID.withDefaultFilling('#');
        sample.userID.setValue("1-00");
        
        assertThat(sample.getLineValue(), is("1-00################JOHN DOE**********************"));
    }
    
    @Test
    public void shouldTruncFieldsToSizeDefinition() {
        PositionalLineSample sample = new PositionalLineSample();
        sample.name.setValue("JOHN DOE 999999999999999999999999999999");
        sample.userID.setValue("1-00 99999999999999999999999999999999");

        assertThat(sample.getLineValue(), is("1-00 999999999999999JOHN DOE 999999999999999999999"));
    }

    @Test
    public void whenValueIsNotFilledShouldUseDefaultValue() {
        PositionalLineSample sample = new PositionalLineSample();
        sample.userID.setValue("1-00");
        
        assertThat(sample.getLineValue(), is("1-00                NC                            "));
    }
    
    @Test
    public void ifAFieldIsConfiguredToRTLShouldWriteItRightAligned() {
        PositionalLineSample sample = new PositionalLineSample();
        sample.name.rtl();
        sample.name.setValue("JOHN DOE");
        sample.userID.setValue("1-00");
        
        assertThat(sample.getLineValue(), is("1-00                                      JOHN DOE"));
    }
}
