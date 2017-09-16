package de.joesaxo.library.json;

import de.joesaxo.library.array.EntryList;

import java.util.Map.Entry;

/**
 * Created by Jens on 22.08.2017.
 */
public class JSONMap extends JSONValue<EntryList<JSONValue, JSONValue>> {

    public JSONMap(EntryList<JSONValue, JSONValue> value) {
        super(value);
    }

    public JSONValue get(JSONValue[] array) {
        return value.get(new JSONArray(array));
    }

    public JSONValue get(EntryList<JSONValue, JSONValue> hashMap) {
        return value.get(new JSONMap(hashMap));
    }

    public JSONValue get(String string) {
        return value.get(new JSONString(string));
    }

    public JSONValue get(Double doubleValue) {
        return value.get(new JSONDouble(doubleValue));
    }

    public JSONValue get(Integer integerValue) {
        return value.get(new JSONInteger(integerValue));
    }

    public JSONValue get(Boolean booleanValue) {
        return value.get(new JSONBoolean(booleanValue));
    }
    public boolean containsKey(JSONValue[] array) {
        return value.containsKey(new JSONArray(array));
    }

    public boolean containsKey(EntryList<JSONValue, JSONValue> hashMap) {
        return value.containsKey(new JSONMap(hashMap));
    }

    public boolean containsKey(String string) {
        return value.containsKey(new JSONString(string));
    }

    public boolean containsKey(Double doubleValue) {
        return value.containsKey(new JSONDouble(doubleValue));
    }

    public boolean containsKey(Integer integerValue) {
        return value.containsKey(new JSONInteger(integerValue));
    }

    public boolean containsKey(Boolean booleanValue) {
        return value.containsKey(new JSONBoolean(booleanValue));
    }

    @Override
    protected Class<EntryList<JSONValue, JSONValue>> getGenericClass() {
        return (Class<EntryList<JSONValue, JSONValue>>) new EntryList<JSONValue, JSONValue>().getClass();
    }

    @Override
    protected boolean isType(Object value) {
        if (!super.isType(value)) return false;
        EntryList<Object, Object> map = (EntryList<Object, Object>) value;
        for (Entry<Object, Object> entry : map.entrySet()) {
            if (Type.getType(entry.getKey()) == null) return false;
            if (Type.getType(entry.getValue()) == null) return false;
        }
        return true;
    }

    /**
    private static EntryList<JSONValue, JSONValue> convertEntryList(EntryList<JSONValue, JSONValue> valueMap) {
        if (valueMap == null) return null;
        EntryList<JSONValue, JSONValue> resultMap = new EntryList<JSONValue, JSONValue>() {


            @Override
            public boolean equals(Object o) {
                if (o == null) return false;
                if (super.equals(0)) return true;
                try {
                    EntryList<JSONValue, JSONValue> m = (EntryList<JSONValue, JSONValue>) o;
                    if (m.size() != size()) return false;
                    for (Entry<JSONValue, JSONValue> entry : m.entrySet()) {
                        if (!containsKey(entry.getKey())) return false;
                        if (entry.getValue() == null) {
                            if (get(entry.getKey()) != null) return false;
                        } else {
                            if (!entry.getValue().equals(get(entry.getKey()))) return false;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
                return true;
            }

            @Override
            public boolean containsKey(Object key) {
                if (super.containsKey(key)) return true;
                for (JSONValue keyObject : keySet()) {
                    if (keyObject.equals(key)) return true;
                }
                return false;
            }

            @Override
            public JSONValue get(Object key) {
                if (super.get(key) != null) return super.get(key);
                for (Entry<JSONValue, JSONValue> entry : entrySet()) {
                    if (entry.getKey().equals(key)) return entry.getValue();
                }
                return null;
            }
            // not necessary for generation



            @Override
            public boolean containsValue(Object value) {
                if (super.containsKey(value)) return true;
                for (JSONValue valueObject : values()) {
                    if (valueObject.equals(value)) return true;
                }
                return false;
            }

            @Override
            public JSONValue remove(Object key) {
                if (containsKey(key)) {
                    for (Object keyObject : keySet()) {
                        if (keyObject != null && keyObject.equals(key)) {
                            return super.remove(keyObject);
                        }
                    }
                }
                return super.remove(key);
            }

            @Override
            public boolean remove(Object key, Object value) {
                if (containsKey(key)) {
                    for (Entry<JSONValue, JSONValue> entry : entrySet()) {
                        if (entry.getKey() != null && entry.getKey().equals(key)) {
                            if (entry.getValue() == null) {
                                if (value == null) return super.remove(entry.getKey(), null);
                            } else {
                                if (entry.getValue().equals(value)) return super.remove(entry.getKey(), entry.getValue());
                            }
                        }
                    }
                }
                return super.remove(key, value);
            }

            @Override
            public JSONValue replace(JSONValue key, JSONValue value) {
                if (containsKey(key)) {
                    for (Entry<JSONValue, JSONValue> entry : entrySet()) {
                        if (entry.getKey() != null) {
                            if (entry.getKey().equals(key)) {
                                return super.replace(entry.getKey(), value);
                            }
                        }
                    }
                }
                return super.replace(key, value);
            }

            @Override
            public boolean replace(JSONValue key, JSONValue oldValue, JSONValue newValue) {
                if (containsKey(key)) {
                    for (Entry<JSONValue, JSONValue> entry : entrySet()) {
                        if (entry.getKey() != null) {
                            if (entry.getKey().equals(key)) {
                                if (entry.getValue() == null) {
                                    if (oldValue == null) return super.replace(entry.getKey(), null, newValue);
                                } else if (entry.getValue().equals(oldValue)) {
                                    return super.replace(entry.getKey(), entry.getValue(), newValue);
                                }
                            }
                        }
                    }
                }
                return super.replace(key, oldValue, newValue);
            }

        };
        for (Entry<JSONValue, JSONValue> entry : valueMap.entrySet()) {
            resultMap.put(entry.getKey(), entry.getValue());
        }
        return resultMap;
    }/**/

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append('{');
        for (Entry<JSONValue, JSONValue> valueJSON : value.entrySet()) {
            b.append(valueJSON.getKey().toString());
            b.append(':');
            b.append(valueJSON.getValue().toString());
            b.append(',');
        }
        if (value.size() > 0) b.deleteCharAt(b.length()-1);
        b.append('}');
        return b.toString();
    }

    @Override
    protected String toFormattedString(int tabs) {
        StringBuilder b = new StringBuilder();
        b.append('{');
        tabs++;
        for (Entry<JSONValue, JSONValue> entry : value.entrySet()) {
            b.append('\n');
            for (int i = 0; i < tabs; i++) {
                b.append('\t');
            }
            b.append(entry.getKey().toFormattedString(tabs));
            b.append(": ");
            b.append(entry.getValue().toFormattedString(tabs));
            b.append(',');
        }
        tabs--;
        if (value.size() != 0) {
            b.deleteCharAt(b.length()-1);
            b.append('\n');
            for (int i = 0; i < tabs; i++) {
                b.append('\t');
            }
        }
        b.append('}');
        return b.toString();
    }

    protected JSONValue<EntryList<JSONValue, JSONValue>> getObject(String stringJSONValue) {
        EntryList<JSONValue, JSONValue> valueHashMap = new EntryList<>();
        if (stringJSONValue.charAt(1) != '}') {
            JSONValue key, value;
            Entry<JSONValue, Integer> entry;
            int startIndex = 1, endIndex;
            while (stringJSONValue.length() > startIndex) {
                entry = getValueEntry(stringJSONValue.substring(startIndex));
                key = entry.getKey();
                startIndex += entry.getValue() + 1;

                entry = getValueEntry(stringJSONValue.substring(startIndex));
                value = entry.getKey();
                startIndex += entry.getValue() + 1;

                valueHashMap.put(key, value);
            }
        }
        return new JSONMap(valueHashMap);
    }

    protected int isValueType(String stringJSONValue) {
        if (stringJSONValue.length() < 2) return -1;
        if (!stringJSONValue.startsWith("{")) return -1;
        if (stringJSONValue.charAt(1) == '}') return 2;
        boolean key = true;
        int index = 1;
        while (stringJSONValue.length() > index) {
            Entry<JSONValue, Integer> entry = getValueEntry(stringJSONValue.substring(index));
            if (entry.getKey() == null || entry.getValue() == -1) return -1;
            index += entry.getValue();
            if (stringJSONValue.substring(index).length() == 0) return -1;
            if (key) {
                if (stringJSONValue.substring(index).charAt(0) != ':') return -1;
            } else {
                if (stringJSONValue.substring(index).charAt(0) == '}') return index+1;
                if (stringJSONValue.substring(index).charAt(0) != ',') return -1;
            }
            key = !key;
            index++;
        }
        return -1;
    }
}
