package de.joesaxo.library.json;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Jens on 22.08.2017.
 */
public class JSONArray extends JSONValue<JSONValue[]> {

    public JSONArray(JSONValue[] value) {
        super(value);
    }

    @Override
    protected Class<JSONValue[]> getGenericClass() {
        return JSONValue[].class;
    }

    @Override
    protected boolean isType(Object value) {
        Object[] array;
        if (value.getClass().equals(Object[].class)) {
            array = (Object[]) value;
        } else if (value.getClass().equals(JSONValue[].class)) {
            array = (JSONValue[]) value;
        } else return false;
        for (Object arrayObject : array) {
            if (Type.getType(arrayObject) == null) return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append('[');
        for (JSONValue valueJSON : value) {
            b.append(valueJSON.toString());
            b.append(',');
        }
        if (value.length > 0) b.deleteCharAt(b.length()-1);
        b.append(']');
        return b.toString();
    }

    @Override
    protected String toFormattedString(int tabs) {
        StringBuilder b = new StringBuilder();
        b.append('[');
        tabs++;
        for (JSONValue valueObject : value) {
            b.append('\n');
            for (int i = 0; i < tabs; i++) {
                b.append('\t');
            }
            b.append(valueObject.toFormattedString(tabs));
            b.append(',');
        }
        tabs--;
        if (value.length != 0) {
            b.deleteCharAt(b.length()-1);
            b.append('\n');
            for (int i = 0; i < tabs; i++) {
                b.append('\t');
            }
        }
        b.append(']');
        return b.toString();
    }

    protected JSONValue<JSONValue[]> getObject(String stringJSONValue) {
        List<JSONValue> values = new ArrayList<>();
        if (stringJSONValue.charAt(1) != ']') {
            int startIndex = 1, endIndex;
            while (stringJSONValue.length() > startIndex) {
                Map.Entry<JSONValue, Integer> entry = getValueEntry(stringJSONValue.substring(startIndex));
                values.add(entry.getKey());
                startIndex += entry.getValue() + 1;
            }
        }
        return new JSONArray(values.toArray(new JSONValue[0]));
    }

    protected int isValueType(String stringJSONValue) {
        if (stringJSONValue.length() < 2) return -1;
        if (!stringJSONValue.startsWith("[")) return -1;
        int index = 1;
        if (stringJSONValue.charAt(index) == ']') return 2;
        while (stringJSONValue.length() > index) {
            Map.Entry<JSONValue, Integer> entry = getValueEntry(stringJSONValue.substring(index));
            if (entry.getKey() == null || entry.getValue() == -1) return -1;
            index += entry.getValue();
            if (stringJSONValue.substring(index).length() == 0) return -1;

            if (stringJSONValue.substring(index).charAt(0) == ']') return index+1;
            if (stringJSONValue.substring(index).charAt(0) != ',') return -1;

            index++;
        }
        return  -1;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (super.equals(o)) return true;
        try {
            JSONArray a = (JSONArray) o;
            if (value == null) return false;
            for (int i = 0; i < value.length; i++) {
                if (value[i] == null) {
                    if (a.value[i] != null) return false;
                } else {
                    if (!value[i].equals(a.value[i])) return false;
                }
            }
            return true;
        } catch (ClassCastException e) {
            return false;
        }
    }
}
