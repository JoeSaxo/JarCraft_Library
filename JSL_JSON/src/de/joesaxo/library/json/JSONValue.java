package de.joesaxo.library.json;

import de.joesaxo.library.array.EntryList;
import org.jarcraft.library.iotools.ClassDependencyHandler;

import java.util.List;
import java.util.Map;

/**
 * Created by Jens on 22.08.2017.
 */
public abstract class JSONValue<T> {

    private Type type;
    private T value;

    protected JSONValue(T value) {
        if (value != null) {
            type = Type.getType(this);
        } else type = null;
        this.value = value;
    }

    public static String cleanUpJSONString(String stringJSON) {
        StringBuilder b = new StringBuilder();
        boolean afz = false;
        char[] cArray = stringJSON.toCharArray();
        for (int i = 0; i < cArray.length; i++) {
            char c = cArray[i];
            switch(c) {
                case '\b':
                case '\t':
                case '\n':
                case '\f':
                case '\r':
                    break;
                case '"':
                    if (!(i > 0 && cArray[i-1] == '\\')) afz = !afz;
                    b.append('"');
                    break;
                case ' ':
                    if (afz) {
                        b.append(' ');
                    }
                    break;
                default:
                    b.append(c);
                    break;
            }
        }
        return b.toString();
    }

    public static JSONValue create(String stringJSONValue) {
        String cleanedUpJSONValueString = cleanUpJSONString(stringJSONValue);
        return getValueEntry(cleanedUpJSONValueString).getKey();
    }

    public static JSONValue create(Object objectJSON) {
        switch (Type.getType(objectJSON)) {
            case BOOLEAN:
                return new JSONBoolean((Boolean) objectJSON);
            case INTEGER:
                return new JSONInteger((Integer) objectJSON);
            case DOUBLE:
                return new JSONDouble((Double) objectJSON);
            case STRING:
                return new JSONString((String)objectJSON);
            case MAP:
                return new JSONMap((Map<? extends Object, ? extends Object>)objectJSON);
            case LIST:
                return new JSONList((List<? extends Object>)objectJSON);
            case NULL:
            default:
                return new JSONNullValue();
        }
    }

    public Type getType() {
        return type == null ? Type.NULL : type;
    }

    protected T getValue() {
        return value;
    }

    protected void setValue(T value) {
        this.value = value;
    }

    protected abstract boolean isType(Object value);

    protected abstract int isValueType(String stringJSONValue);

    protected abstract JSONValue getObject(String stringJSONValue);

    @Override
    public abstract String toString();

    public String toFormattedString() {
        return toFormattedString(0);
    }

    protected String toFormattedString(int tabs) {
        return toString();
    }

    protected static Map.Entry<JSONValue, Integer> getValueEntry(String stringJSONValue) {
        JSONValue<?> key;
        int value;

        for (Type type : Type.values()) {
            value = type.typeObject.isValueType(stringJSONValue);
            if (value != -1) {
                key = type.typeObject.getObject(stringJSONValue.substring(0, value));
                return new EntryList.Entry(key, value);
            }
        }

        return new EntryList.Entry(new JSONNullValue(), -1);
    }

    public JSONList castToJSONArray() {
        if (Type.LIST.equals(getType())) return (JSONList) this;
        return null;
    }

    public JSONMap castToJSONMap() {
        if (Type.MAP.equals(getType())) return (JSONMap) this;
        return null;
    }

    public JSONString castToJSONString() {
        if (Type.STRING.equals(getType())) return (JSONString) this;
        return null;
    }

    public JSONDouble castToJSONDouble() {
        if (Type.DOUBLE.equals(getType())) return (JSONDouble) this;
        return null;
    }

    public JSONInteger castToJSONInteger() {
        if (Type.INTEGER.equals(getType())) return (JSONInteger) this;
        return null;
    }

    public JSONBoolean castToJSONBoolean() {
        if (Type.BOOLEAN.equals(getType())) return (JSONBoolean) this;
        return null;
    }

    public boolean isNull() {
        return getType().isNull();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!ClassDependencyHandler.dependsOn(getType().typeObject.getClass(), o.getClass())) return false;
        try {
            JSONValue v = (JSONValue) o;
            if (value == null) return v.value == null;
            return value.equals(v.value);
        } catch (ClassCastException e) {
            return false;
        }
    }

    protected static boolean isJSONValue(Object value) {
        return value == null ? false : ClassDependencyHandler.dependsOn(JSONValue.class, value.getClass());
    }

    public enum Type {

        LIST(new JSONList(true)),
        MAP(new JSONMap(true)),
        STRING(new JSONString()),
        DOUBLE(new JSONDouble()),
        INTEGER(new JSONInteger()),
        BOOLEAN(new JSONBoolean()),
        NULL(new JSONNullValue());

        private JSONValue typeObject;

        Type(JSONValue typeObject) {
            typeObject.type = this;
            this.typeObject = typeObject;
        }

        public boolean isNull() {
            return equals(NULL);
        }

        private static Type getType(JSONValue value) {
            for (Type type : values()) {
                if (ClassDependencyHandler.dependsOn(type.typeObject.getClass(), value.getClass())) return type;
            }
            return NULL;
        }

        protected static Type getType(Object value) {
            for (Type type : values()) {
                if (type.typeObject.isType(value)) return type;
            }
            return NULL;
        }

        protected static JSONValue getObject(Object value) {
            Type type = getType(value);
            if (type == null) {
                return new JSONNullValue();
            }
            switch (type) {
                case LIST:
                    return new JSONList((List<Object>)value);
                case MAP:
                    return new JSONMap((Map<Object, Object>)value);
                case STRING:
                    return new JSONString((String)value);
                case DOUBLE:
                    return new JSONDouble((double)value);
                case INTEGER:
                    return new JSONInteger((int)value);
                case BOOLEAN:
                    return new JSONBoolean((boolean)value);
                default:
                    return new JSONNullValue();
            }
        }

    }
}
