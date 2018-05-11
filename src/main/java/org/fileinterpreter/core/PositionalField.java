package org.fileinterpreter.core;

import com.google.common.base.Ascii;
import com.google.common.base.Strings;

public class PositionalField extends Field {
    private int startIndex;
    private int size;
    private char defaultFilling;
    private boolean rtl;

    private PositionalField(Line line) {
        super(line);
        rtl = false;
    }
    
    public static PositionalField createTo(Line line) {
        return new PositionalField(line);
    }
    
    public PositionalField named(String name) {
        return (PositionalField) super.named(name);
    }

    public PositionalField withDefaultValue(String defaultValue) {
        return (PositionalField) super.withDefaultValue(defaultValue);
    }
    
    public PositionalField startingAt(int index) {
        this.startIndex = index;
        return this;
    }

    public PositionalField withSize(int size) {
        this.size = size;
        return this;
    }
        
    public Field withDefaultFilling(char defaultFilling) {
        this.defaultFilling = defaultFilling;
        return this;
    }    

    public PositionalField rtl() {
        this.rtl = true;
        return this;
    }
    
    public int getStartIndex() {
        return startIndex;
    }
    
    public int size() {
        return size;
    }

    public String getRawValue() {
        String rawValue = super.getRawValue();

        return rawValue == null ? "" : trunc(pad(rawValue));
    }

    private String pad(String value) {
    	return rtl ? Strings.padStart(value, size, getDefaultFilling()) 
    			   : Strings.padEnd(value, size, getDefaultFilling());
    }

    private String trunc(String value) {
    	return Ascii.truncate(value, Math.min(value.length(), size), "");
    }
    
    private char getDefaultFilling() {
        return defaultFilling == Character.MIN_VALUE ? ' ' : defaultFilling;
    }
}
