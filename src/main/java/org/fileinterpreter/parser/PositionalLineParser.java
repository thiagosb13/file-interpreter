package org.fileinterpreter.parser;

import java.lang.reflect.Field;
import java.util.logging.Logger;

import org.fileinterpreter.annotation.PositionalField;
import org.fileinterpreter.annotation.PositionalLine;
import org.fileinterpreter.commons.Strings;
import org.fileinterpreter.exception.MisconfiguredDocumentException;

public class PositionalLineParser implements LineParser {

    @Override
    public void parse(String content, Object object) {
        Field[] fields = object.getClass().getFields();

        for (Field field : fields) {
            PositionalField positionalField = getPositionalFieldFrom(field);

            int beginIndex = positionalField.startIndex() - 1;

            try {
                String value = "";

                if (content != null) {
                    try {
                        value = content.substring(beginIndex, beginIndex + positionalField.size());
                    } catch (Exception e) {
                    	Logger.getLogger(this.getClass().getCanonicalName())
                    		  .severe(String.format("Could not get the value of the '%s' field from '%s' line.", field.getName(), content));
                        Logger.getLogger(this.getClass().getCanonicalName())
                        	  .severe(e.getMessage());
                    }
                } else {
                    value = positionalField.defaultValue();
                }

                field.set(object, value);
            } catch (IllegalArgumentException | IllegalAccessException e) {
            	Logger.getLogger(this.getClass().getCanonicalName())
      		  		  .severe(String.format("Could not get the value of the '%s' field from '%s' line.", field.getName(), content));
            	Logger.getLogger(this.getClass().getCanonicalName())
          	  	 	  .severe(e.getMessage());
            }
        }
    }

    @Override
    public String toContent(Object line) {
        if (line == null) return "";
        
        StringBuilder builder = new StringBuilder();
        Field[] fields = line.getClass().getFields();

        for (Field field : fields) {
            try {
            	PositionalField positionalField = getPositionalFieldFrom(field);
            	
                Object value = field.get(line);
                String rawValue = value != null ? value.toString() : positionalField.defaultValue();
                builder.append(trunc(pad(rawValue, positionalField.size(), positionalField.rtl(), positionalField.spaceFilling()), positionalField.size()));
            } catch (IllegalArgumentException | IllegalAccessException e) {
            	Logger.getLogger(this.getClass().getCanonicalName())
            		  .severe(String.format("Could not get the field '%s'.", field.getName()));
            	Logger.getLogger(this.getClass().getCanonicalName())
          	  		  .severe(e.getMessage());
            }
        } 
        
        return builder.toString();
    }
    
    private PositionalField getPositionalFieldFrom(Field field) {
    	PositionalField positionalField = field.getDeclaredAnnotation(PositionalField.class);
    	
    	if (positionalField == null)
    		throw new MisconfiguredDocumentException(String.format("Field '%s' is not annotated correctly.", field.getName()));
    	
    	return positionalField;
    }
    
    private String pad(String value, int size, boolean rtl, char defaultFilling) {
        return rtl ? Strings.padLeft(value, size, defaultFilling) 
                   : Strings.padRight(value, size, defaultFilling);
    }
    
    private String trunc(String value, int size) {
        return Strings.truncate(value, Math.min(value.length(), size), "");
    }

    public static PositionalLine getConfigFrom(Field field) {
    	PositionalLine positionalLine = field.getDeclaredAnnotation(PositionalLine.class);
    	
    	if (positionalLine == null)
    		throw new MisconfiguredDocumentException(String.format("Line '%s' is not annotated correctly.", field.getName()));
    	
    	return positionalLine;
    }
}
