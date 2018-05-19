package org.fileinterpreter.parser;

import java.lang.reflect.Field;

import org.fileinterpreter.annotation.PositionalField;
import org.pmw.tinylog.Logger;

import com.google.common.base.Ascii;
import com.google.common.base.Strings;

public class PositionalLineParser extends LineParser {

    @Override
    public void parse(String content, Object object) {
        Field[] fields = object.getClass().getFields();

        for (Field field : fields) {
            PositionalField positionalField = field.getDeclaredAnnotation(PositionalField.class);

            int beginIndex = positionalField.startIndex() - 1;

            try {
                String value = "";

                if (content != null) {
                    try {
                        value = content.substring(beginIndex, beginIndex + positionalField.size());
                    } catch (Exception e) {
                        Logger.error(String.format("Could not get the value of the '%s' field from '%s' line.", field.getName(), content));
                        Logger.error(e);
                    }
                } else {
                    value = positionalField.defaultValue();
                }

                field.set(object, value);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                Logger.error(String.format("Could not get the field from '%s' line.", field.getName(), content));
                Logger.error(e);
            }
        }
    }

    @Override
    public String toContent(Object line) {
        StringBuilder builder = new StringBuilder();
        Field[] fields = line.getClass().getFields();

        for (Field field : fields) {
            try {
                PositionalField positionalField = field.getDeclaredAnnotation(PositionalField.class);
                
                Object value = field.get(line);
                String rawValue = value != null ? value.toString() : positionalField.defaultValue();
                builder.append(trunc(pad(rawValue, positionalField.size(), positionalField.rtl(), positionalField.spaceFilling()), positionalField.size()));
            } catch (IllegalArgumentException | IllegalAccessException e) {
                Logger.error(String.format("Could not get the field '%s'.", field.getName()));
                Logger.error(e);
            }
        } 
        
        return builder.toString();
    }
    
    private String pad(String value, int size, boolean rtl, char defaultFilling) {
        return rtl ? Strings.padStart(value, size, defaultFilling) 
                   : Strings.padEnd(value, size, defaultFilling);
    }
    
    private String trunc(String value, int size) {
        return Ascii.truncate(value, Math.min(value.length(), size), "");
    }
}