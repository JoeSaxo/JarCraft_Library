package de.joesaxo.library.json;

import jdk.nashorn.internal.parser.JSONParser;

/**
 * Created by Jens on 22.08.2017.
 */
public class JSONString extends JSONValue<String> {

    public JSONString(String value) {
        super(value);
    }

    @Override
    protected Class<String> getGenericClass() {
        return String.class;
    }

    @Override
    public String toString() {
        return value == null ? "\"null\"" :JSONParser.quote(value);
    }

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
}
