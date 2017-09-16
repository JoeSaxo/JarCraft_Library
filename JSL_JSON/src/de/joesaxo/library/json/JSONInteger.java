package de.joesaxo.library.json;

/**
 * Created by Jens on 22.08.2017.
 */
public class JSONInteger extends JSONValue<Integer> {

    public JSONInteger(Integer value) {
        super(value);
    }

    @Override
    protected Class<Integer> getGenericClass() {
        return Integer.class;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    protected JSONValue<Integer> getObject(String stringJSONValue) {
        return new JSONInteger(Integer.valueOf(stringJSONValue));
    }

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
}
