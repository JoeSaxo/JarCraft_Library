package de.joesaxo.library.json;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.jarcraft.library.iotools.ClassDependencyHandler;

/**
 * Created by Jens on 22.08.2017.
 */
public class JSONBoolean extends JSONValue<Boolean> {

    protected JSONBoolean() {
        super(null);
    }

    public JSONBoolean(Boolean value) {
        super(value);
    }

    @Override
    protected boolean isType(Object value) {
        if (value == null) return false;
        return ClassDependencyHandler.dependsOn(Boolean.class, value.getClass());
    }

    @Override
    public String toString() {
        return String.valueOf(getValue());
    }

    @Override
    protected JSONValue<Boolean> getObject(String stringJSONValue) {
        return new JSONBoolean(stringJSONValue.equalsIgnoreCase("true"));
    }

    @Override
    protected int isValueType(String stringJSONValue) {
        if (stringJSONValue.toLowerCase().startsWith("true")) return "true".length();
        if (stringJSONValue.toLowerCase().startsWith("false")) return "false".length();
        return -1;
    }

    @Override
    public Boolean getValue() {
        return super.getValue();
    }

    @Override
    public void setValue(Boolean value) {
        super.setValue(value);
    }
}
