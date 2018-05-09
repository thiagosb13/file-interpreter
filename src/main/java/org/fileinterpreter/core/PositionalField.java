package org.fileinterpreter.core;

public class PositionalField extends Field {
    private int initialPos;
    private int size;
    private boolean rtl;

    public PositionalField(ILine line) {
        super(line);
        rtl = false;
    }
    
    public PositionalField named(String name) {
        return (PositionalField) super.named(name);
    }

    public PositionalField withDefaultValue(String defaultValue) {
        return (PositionalField) super.withDefaultValue(defaultValue);
    }
    
    public PositionalField startingAt(int position) {
        this.initialPos = position;
        return this;
    }

    public PositionalField withSize(int size) {
        this.size = size;
        return this;
    }

    public PositionalField rtl() {
        this.rtl = true;
        return this;
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

    public int getInitialPos() {
        return initialPos;
    }

    public int size() {
        return size;
    }

    public boolean isRtl() {
        return rtl;
    }
}
