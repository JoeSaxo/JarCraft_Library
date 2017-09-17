package de.joesaxo.library.json;

import de.joesaxo.library.array.EntryList;
import de.joesaxo.library.array.EntryList.Entry;
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
            this.type = Type.getType(this);
        } else type = null;
        this.value = value;
    }

    public Type getType() {
        return type;
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

    protected String toFormattedString(int tabs) {
        return toString();
    }

    protected static Entry<JSONValue, Integer> getValueEntry(String stringJSONValue) {
        JSONValue<?> key;
        int value;

        for (Type type : Type.values()) {
            value = type.typeObject.isValueType(stringJSONValue);
            if (value != -1) {
                key = type.typeObject.getObject(stringJSONValue.substring(0, value));
                return new EntryList.Entry(key, value);
            }
        }
        throw new Error("Illegal string format: string can not be converted to json");
    }

    public JSONList castToJSONArray() {
        if (Type.LIST.equals(type)) return (JSONList) this;
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

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!ClassDependencyHandler.dependsOn(type.typeObject.getClass(), o.getClass())) return false;
        try {
            JSONValue v = (JSONValue) o;
            if (value == null) return v.value == null;
            return value.equals(v.value);
        } catch (ClassCastException e) {
            return false;
        }
    }

    public static boolean isJSONValue(Object value) {
        return ClassDependencyHandler.dependsOn(JSONValue.class, value.getClass());
    }

    public enum Type {

        LIST(new JSONList(true)),
        MAP(new JSONMap(true)),
        STRING(new JSONString()),
        DOUBLE(new JSONDouble()),
        INTEGER(new JSONInteger()),
        BOOLEAN(new JSONBoolean());

        private JSONValue typeObject;

        Type(JSONValue typeObject) {
            typeObject.type = this;
            this.typeObject = typeObject;
        }

        public boolean isType(JSONValue value) {
            return value == null ? false : equals(value.type);
        }

        private static Type getType(JSONValue value) {
            for (Type type : values()) {
                if (ClassDependencyHandler.dependsOn(type.typeObject.getClass(), value.getClass())) return type;
            }
            return null;
        }

        protected static Type getType(Object value) {
            for (Type type : values()) {
                if (type.typeObject.isType(value)) return type;
            }
            return null;
        }

        protected static JSONValue getObject(Object value) {
            Type type = getType(value);
            if (type == null) {
                return null;
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
                    return null;
            }
        }

    }
}
