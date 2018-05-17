package org.fileinterpreter.core;

import java.lang.reflect.Field;

import org.fileinterpreter.annotation.PositionalField;
import org.pmw.tinylog.Logger;

public class PositionalLine extends Line {
    public void parse(String content) {
        Field[] fields = this.getClass().getFields();

        for (Field field : fields) {
            PositionalField positionalField = field.getDeclaredAnnotation(PositionalField.class);

            int beginIndex = positionalField.startIndex() - 1;

            try {
                Campo campo = (Campo) field.get(this);

                try {
                    campo.setValue(content.substring(beginIndex, beginIndex + positionalField.size()));
                } catch (Exception e) {
                    campo.setValue("");

                    Logger.error(String.format("Could not get the value of the '%s' field from '%s' line.", field.getName(), content));
                    Logger.error(e);
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                Logger.error(String.format("Could not get the field from '%s' line.", field.getName(), content));
                Logger.error(e);
            }
        }
    }

    @Override
    public String toContent() {
        StringBuilder builder = new StringBuilder();
        Field[] fields = this.getClass().getFields();

        for (Field field : fields) {
            try {
                Campo campo = (Campo) field.get(this);
                builder.append(campo.getRawValue());
            } catch (IllegalArgumentException | IllegalAccessException e) {
                Logger.error(String.format("Could not get the field '%s'.", field.getName()));
                Logger.error(e);
            }
        } 
        
        return builder.toString();
        // return fields.stream()
        // .map(Field::getRawValue)
        // .reduce("", String::concat);
    }
}
