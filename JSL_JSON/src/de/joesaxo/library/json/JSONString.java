package de.joesaxo.library.json;

import jdk.nashorn.internal.parser.JSONParser;
import org.jarcraft.library.iotools.ClassDependencyHandler;

/**
 * Created by Jens on 22.08.2017.
 */
public class JSONString extends JSONValue<String> {

    protected JSONString() {
        super(null);
    }

    public JSONString(String value) {
        super(value);
    }

    @Override
    protected boolean isType(Object value) {
        if (value == null) return false;
        return ClassDependencyHandler.dependsOn(String.class, value.getClass());
    }

    @Override
    public String toString() {
        return JSONParser.quote(getValue());
    }

    @Override
    protected JSONValue<String> getObject(String stringJSONValue) {
        StringBuilder b = new StringBuilder();
        for (int i = 1; i < stringJSONValue.length()-1; i++) {
            char c = stringJSONValue.charAt(i);
            if (c == '\\') {
                switch (stringJSONValue.charAt(i+1)) {
                    case 'b':
                        b.append('\b');
                        break;
                    case 't':
                        b.append('\t');
                        break;
                    case 'n':
                        b.append('\n');
                        break;
                    case 'f':
                        b.append('\f');
                        break;
                    case 'r':
                        b.append('\r');
                        break;
                    case '"':
                        b.append('\"');
                        break;
                    case '\\':
                        b.append('\\');
                        break;
                    default:
                        b.append(stringJSONValue.charAt(i+1));
                        break;
                }
                i++;
            } else {
                b.append(c);
            }
        }
        return new JSONString(b.toString());
    }

    @Override
    protected int isValueType(String stringJSONValue) {
        if (!stringJSONValue.startsWith("\"")) return -1;
        for (int i = 1; i < stringJSONValue.length(); i++) {
            char c = stringJSONValue.charAt(i);
            if (c == '\\') {
                i++;
            } else if (c == '"'){
                return i+1;
            }
        }
        return -1;
    }

    @Override
    public String getValue() {
        return super.getValue();
    }

    @Override
    public void setValue(String value) {
        if (value == null) throw new NullPointerException(value + " must not be null!");
        super.setValue(value);
    }
}
