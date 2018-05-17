package org.fileinterpreter.core;

import com.google.common.base.Ascii;
import com.google.common.base.Strings;

public class PositionalField_OLD extends Campo {
    private int startIndex;
    private int size;
    private char defaultFilling;
    private boolean rtl;

    private PositionalField_OLD() {
        rtl = false;
    }
    
    public static PositionalField_OLD create() {
        return new PositionalField_OLD();
    }
    
//    public PositionalField_OLD named(String name) {
//        return (PositionalField_OLD) super.named(name);
//    }
//
//    public PositionalField_OLD withDefaultValue(String defaultValue) {
//        return (PositionalField_OLD) super.withDefaultValue(defaultValue);
//    }
    
    public PositionalField_OLD startingAt(int index) {
        this.startIndex = index;
        return this;
    }

    public PositionalField_OLD withSize(int size) {
        this.size = size;
        return this;
    }
        
    public Campo withDefaultFilling(char defaultFilling) {
        this.defaultFilling = defaultFilling;
        return this;
    }    

    public PositionalField_OLD rtl() {
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
