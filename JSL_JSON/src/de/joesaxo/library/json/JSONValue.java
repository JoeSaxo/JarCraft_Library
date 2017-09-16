package de.joesaxo.library.json;

import de.joesaxo.library.array.EntryList;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

/**
 * Created by Jens on 22.08.2017.
 */
public abstract class JSONValue<T> {

    private Type type;
    public final T value;

    protected JSONValue(T value) {
        if (value != null) {
            this.type = Type.getType(this);
        } else type = null;
        this.value = value;
    }

    public Type getType() {
        return type;
    }

    protected abstract Class<T> getGenericClass();

    protected boolean isType(Object value) {
        return type.isType(value.getClass());
    }

    @Override
    public abstract String toString();

    protected String toFormattedString(int tabs) {
        return toString();
    }

    protected abstract int isValueType(String stringJSONValue);

    protected abstract JSONValue getObject(String stringJSONValue);

    protected static final Map.Entry<JSONValue, Integer> getValueEntry(String stringJSONValue) {
        return Type.getValueEntry(stringJSONValue);
    }

    public JSONArray castToJSONArray() {
        if (Type.ARRAY.equals(type)) return (JSONArray) this;
        return null;
    }

    public JSONMap castToJSONMap() {
        if (Type.MAP.equals(type)) return (JSONMap) this;
        return null;
    }

    public JSONString castToJSONString() {
        if (Type.STRING.equals(type)) return (JSONString) this;
        return null;
    }

    public JSONDouble castToJSONDouble() {
        if (Type.DOUBLE.equals(type)) return (JSONDouble) this;
        return null;
    }

    public JSONInteger castToJSONInteger() {
        if (Type.INTEGER.equals(type)) return (JSONInteger) this;
        return null;
    }

    public JSONBoolean castToJSONBoolean() {
        if (Type.BOOLEAN.equals(type)) return (JSONBoolean) this;
        return null;
    }

    public JSONValue[] castToArray() {
        if (Type.ARRAY.equals(type)) return (JSONValue[]) value;
        return null;
    }

    public EntryList<JSONValue, JSONValue> castToEntryList() {
        if (Type.MAP.equals(type)) return (EntryList<JSONValue, JSONValue>) value;
        return null;
    }

    public String castToString() {
        if (Type.STRING.equals(type)) return (String) value;
        return null;
    }

    public Double castToDouble() {
        if (Type.DOUBLE.equals(type)) return (Double) value;
        return null;
    }

    public Integer castToInteger() {
        if (Type.INTEGER.equals(type)) return (Integer) value;
        return null;
    }

    public Boolean castToBoolean() {
        if (Type.BOOLEAN.equals(type)) return (Boolean) value;
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        try {
            JSONValue v = (JSONValue) o;
            if (value == null) return v.value == null;
            return value.equals(v.value);
        } catch (ClassCastException e) {
            return false;
        }
    }

    public enum Type {

        ARRAY(new JSONArray(null)),
        MAP(new JSONMap(null)),
        STRING(new JSONString(null)),
        DOUBLE(new JSONDouble(null)),
        INTEGER(new JSONInteger(null)),
        BOOLEAN(new JSONBoolean(null));

        private JSONValue typeObject;

        Type(JSONValue typeObject) {
            typeObject.type = this;
            this.typeObject = typeObject;
        }

        public boolean isType(JSONValue value) {
            return typeObject.getClass().equals(value.getClass());
        }

        protected boolean isType(Class<?> valueClass) {
            if (valueClass.equals(Object.class)) return false;
            return typeObject.getGenericClass().equals(valueClass) ? true : isType(valueClass.getSuperclass());
        }

        private static Type getType(JSONValue value) {
            for (Type type : values()) {
                if (type.isType(value)) return type;
            }
            return null;
        }

        protected static Type getType(Object value) {
            for (Type type : values()) {
                if (type.typeObject.isType(value)) return type;
            }
            return null;
        }

        private static final Map.Entry<JSONValue, Integer> getValueEntry(String stringJSONValue) {
            JSONValue<?> key = null;
            int value = -1;

            for (Type type : values()) {
                value = type.typeObject.isValueType(stringJSONValue);
                if (value != -1) {
                    key = type.typeObject.getObject(stringJSONValue.substring(0, value));
                    //System.out.println(key);
                    break;
                }
            }

            if (key == null) throw new Error("Illegal string format: string can not be converted to json");

            final JSONValue entryKey = key;
            final int entryValue = value;
            return new Map.Entry<JSONValue, Integer>() {

                @Override
                public JSONValue getKey() {
                    return entryKey;
                }

                @Override
                public Integer getValue() {
                    return entryValue;
                }

                @Override
                public Integer setValue(Integer value) {
                    return null;
                }
            };
        }
    }
}
