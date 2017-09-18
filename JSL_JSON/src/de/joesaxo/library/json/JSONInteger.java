package de.joesaxo.library.json;

import org.jarcraft.library.iotools.ClassDependencyHandler;

/**
 * Created by Jens on 22.08.2017.
 */
public class JSONInteger extends JSONValue<Integer> {

    protected JSONInteger() {
        super(null);
    }

    public JSONInteger(Integer value) {
        super(value);
    }

    @Override
    protected boolean isType(Object value) {
        if (value == null) return false;
        return ClassDependencyHandler.dependsOn(Integer.class, value.getClass());
    }

    @Override
    public String toString() {
        return String.valueOf(getValue());
    }

    @Override
    protected JSONValue<Integer> getObject(String stringJSONValue) {
        return new JSONInteger(Integer.valueOf(stringJSONValue));
    }

    @Override
    protected int isValueType(String stringJSONValue) {
        int length = 0;
        for (char c : stringJSONValue.toCharArray()) {
            switch (c) {
                case '.':
                    return -1;
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                    length++;
                    break;
                default:
                    if (length == 0) return -1;
                    return length;
            }
        }
        if (length == 0) return -1;
        return length;
    }

    @Override
    public Integer getValue() {
        return super.getValue();
    }

    @Override
    public void setValue(Integer value) {
        if (value == null) throw new NullPointerException(value + " must not be null!");
        super.setValue(value);
    }
}
