package org.fileinterpreter.core;

public class Field {
    protected final ILine line;

    public String name;
    public String defaultValue;
    private String rawValue;
    
    protected Field(ILine line) {
        this.line = line;
        this.line.add(this);
    }
    
    public String getValue() {
        return getRawValue().trim();
    }

    public void setValue(String value) {
        this.rawValue = value;
    }
    
    public String getRawValue() {
        return rawValue == null ? defaultValue : rawValue;
    }
}
