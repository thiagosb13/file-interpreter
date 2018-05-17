package org.fileinterpreter.core;

public class Campo {
    private String rawValue;
    
    public String getValue() {
        return getRawValue().trim();
    }

    public void setValue(String value) {
        this.rawValue = value;
    }
    
    public String getRawValue() {
        return rawValue;
    }
}
