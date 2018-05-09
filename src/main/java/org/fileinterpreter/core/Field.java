package org.fileinterpreter.core;

public class Field {
    protected final ILine line;

    private String name;
    private String defaultValue;
    private String rawValue;
    
    protected Field(ILine line) {
        this.line = line;
        this.line.add(this);
        
        defaultValue = "";
    }
    
    public Field named(String name) {
        this.name = name;
        return this;
    }
    
    public Field withDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
        return this;
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

    public String getName() {
        return name;
    }

    public String getDefaultValue() {
        return defaultValue;
    }
}
