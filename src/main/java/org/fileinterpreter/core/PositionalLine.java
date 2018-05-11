package org.fileinterpreter.core;

import java.util.ArrayList;
import java.util.List;

import org.pmw.tinylog.Logger;

public class PositionalLine extends Line {
    private List<PositionalField> fields;

    public void add(Field field) {
        if (fields == null) {
            fields = new ArrayList<>();
        }

        fields.add((PositionalField) field);
    }

    @Override
    public void parse(String content) {
        for (PositionalField field : fields) {
            try {
                int beginIndex = field.getStartIndex() - 1;
    
                field.setValue(content.substring(beginIndex, beginIndex + field.size()));
            } catch (Exception e) {
                field.setValue("");
                
                Logger.error(String.format("Could not get the value of the '%s' field from '%s' line.", 
                                           field.getName(), content));
                Logger.error(e);
            }
        }
    }

    @Override
    public String toContent() {
        return fields.stream()
                     .map(Field::getRawValue)
                     .reduce("", String::concat);
    }
}
