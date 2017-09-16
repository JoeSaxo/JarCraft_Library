package de.joesaxo.library.json;

import com.sun.org.apache.xpath.internal.operations.Bool;

/**
 * Created by Jens on 22.08.2017.
 */
public class JSONBoolean extends JSONValue<Boolean> {

    public JSONBoolean(Boolean value) {
        super(value);
    }

    @Override
    protected Class<Boolean> getGenericClass() {
        return Boolean.class;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    protected JSONValue<Boolean> getObject(String stringJSONValue) {
        return new JSONBoolean(stringJSONValue.equalsIgnoreCase("true"));
    }

    protected int isValueType(String stringJSONValue) {
        if (stringJSONValue.toLowerCase().startsWith("true")) return "true".length();
        if (stringJSONValue.toLowerCase().startsWith("false")) return "false".length();
        return -1;
    }
}
