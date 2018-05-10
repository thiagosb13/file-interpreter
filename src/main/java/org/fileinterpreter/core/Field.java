package org.fileinterpreter.core;

import com.google.common.base.Strings;

public abstract class Field {
    protected final Line line;

    private String name;
    private String defaultValue;
    private String rawValue;
    
    protected Field(Line line) {
        this.line = line;
        this.line.add(this);
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
        return rawValue == null ? getDefaultValue() : rawValue;
    }

    public String getName() {
        return name;
    }
    
    private String getDefaultValue() {
    	return Strings.emptyToNull(defaultValue);
    }
}
