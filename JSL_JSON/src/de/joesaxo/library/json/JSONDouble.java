package de.joesaxo.library.json;

/**
 * Created by Jens on 22.08.2017.
 */
public class JSONDouble extends JSONValue<Double> {

    public JSONDouble(Double value) {
        super(value);
    }

    @Override
    protected Class<Double> getGenericClass() {
        return Double.class;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    protected JSONValue<Double> getObject(String stringJSONValue) {
        return new JSONDouble(Double.valueOf(stringJSONValue));
    }

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
}
