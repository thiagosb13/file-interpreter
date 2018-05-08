package org.fileinterpreter.core;

public class PositionalField extends Field {
    public int initialPos;
    public int size;
    public boolean rtl;

    public PositionalField(ILine line) {
        super(line);
        rtl = false;
    }
    
    public String getRawValue() {
        return trunc(pad(super.getRawValue()));
    }
    
    private String pad(String value) {
        String format = String.format("%s%s%s%s", "%1$", rtl ? "" : "-", size, "s");
        
        return String.format(format, value);
    }
    
    private String trunc(String value) {
        return value.substring(0, Math.min(value.length(), size));
    }
}
