package de.joesaxo.library.json;

import org.jarcraft.library.iotools.ClassDependencyHandler;

/**
 * Created by Jens on 22.08.2017.
 */
public class JSONDouble extends JSONValue<Double> {

    protected JSONDouble() {
        super(null);
    }

    public JSONDouble(Double value) {
        super(value);
    }

    @Override
    protected boolean isType(Object value) {
        if (value == null) return false;
        return ClassDependencyHandler.dependsOn(Double.class, value.getClass());
    }

    @Override
    public String toString() {
        return String.valueOf(getValue());
    }

    @Override
    protected JSONValue<Double> getObject(String stringJSONValue) {
        return new JSONDouble(Double.valueOf(stringJSONValue));
    }

    @Override
    protected int isValueType(String stringJSONValue) {
        int length = 0;
        boolean dot = false;
        for (char c : stringJSONValue.toCharArray()) {
            switch (c) {
                case '.':
                    if (dot) {
                        if (length == 0) return -1;
                        return length;
                    }
                    dot = true;
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
                    if (length == 0 || !dot) return -1;
                    return length;
            }
        }
        if (!dot || length == 0) return -1;
        return length;
    }

    @Override
    public Double getValue() {
        return super.getValue();
    }

    @Override
    public void setValue(Double value) {
        if (value == null) throw new NullPointerException(value + " must not be null!");
        super.setValue(value);
    }
}
