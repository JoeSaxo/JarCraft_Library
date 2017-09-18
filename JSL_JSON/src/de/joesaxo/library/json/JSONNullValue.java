package de.joesaxo.library.json;

import jdk.nashorn.internal.parser.JSONParser;
import org.jarcraft.library.iotools.ClassDependencyHandler;

/**
 * Created by Jens on 22.08.2017.
 */
public class JSONNullValue extends JSONValue<Object> {

    public JSONNullValue() {
        super(null);
    }

    @Override
    protected boolean isType(Object value) {
        return value == null;
    }

    @Override
    public String toString() {
        return "null";
    }

    @Override
    protected JSONValue<Object> getObject(String stringJSONValue) {
        return new JSONNullValue();
    }

    @Override
    protected int isValueType(String stringJSONValue) {
        if (stringJSONValue.startsWith("null")) return 4;
        return -1;
    }

    @Override
    public Object getValue() {
        return null;
    }
}
