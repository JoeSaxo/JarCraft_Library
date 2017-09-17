package de.joesaxo.library.json;

import de.joesaxo.library.array.EntryList;
import org.jarcraft.library.iotools.ClassDependencyHandler;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Created by Jens on 22.08.2017.
 */
public class JSONMap extends JSONValue<EntryList<JSONValue, JSONValue>> implements Map<JSONValue, JSONValue> {

    protected JSONMap(boolean forceNull) {
        super(null);
    }

    public JSONMap(Map<? extends Object, ? extends Object> valueMap) {
        super(new EntryList<>());
        if (valueMap != null) putAllObjective(valueMap);
    }

    @Override
    protected boolean isType(Object value) {
        if (value == null) return false;
        if (!ClassDependencyHandler.dependsOn(Map.class, value.getClass())) return false;
        Map<Object, Object> map = (Map<Object, Object>) value;
        for (Entry<Object, Object> entry : map.entrySet()) {
            if (!isJSONValue(entry.getKey()) && Type.getType(entry.getKey()) == null) return false;
            if (!isJSONValue(entry.getValue()) && Type.getType(entry.getValue()) == null) return false;
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
        for (Entry<JSONValue, JSONValue> valueJSON : entrySet()) {
            b.append(valueJSON.getKey().toString());
            b.append(':');
            b.append(valueJSON.getValue().toString());
            b.append(',');
        }
        if (size() > 0) b.deleteCharAt(b.length()-1);
        b.append('}');
        return b.toString();
    }

    @Override
    protected String toFormattedString(int tabs) {
        StringBuilder b = new StringBuilder();
        b.append('{');
        tabs++;
        for (Entry<JSONValue, JSONValue> entry : getValue().entrySet()) {
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
        if (getValue().size() != 0) {
            b.deleteCharAt(b.length()-1);
            b.append('\n');
            for (int i = 0; i < tabs; i++) {
                b.append('\t');
            }
        }
        b.append('}');
        return b.toString();
    }

    @Override
    protected JSONValue<EntryList<JSONValue, JSONValue>> getObject(String stringJSONValue) {
        EntryList<JSONValue, JSONValue> valueHashMap = new EntryList<>();
        if (stringJSONValue.charAt(1) != '}') {
            JSONValue key, value;
            Entry<JSONValue, Integer> entry;
            int startIndex = 1;
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

    @Override
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

    // ---------------------------------------------------------

    public JSONMap() {
        super(null);
    }

    @Override
    public int size() {
        return getValue().size();
    }

    @Override
    public boolean isEmpty() {
        return getValue().isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        if (!isJSONValue(key)) {
            key = Type.getObject(key);
            if (key == null) return false;
        }
        return getValue().containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        if (!isJSONValue(value)) {
            value = Type.getObject(value);
            if (value == null) return false;
        }
        return getValue().containsValue(value);
    }

    @Override
    public JSONValue get(Object key) {
        if (!isJSONValue(key)) {
            key = Type.getObject(key);
            if (key == null) return null;
        }
        return getValue().get(key);
    }

    @Override
    public JSONValue put(JSONValue key, JSONValue value) {
        return getValue().put(key, value);
    }

    public JSONValue putObjective(Object key, Object value) {
        JSONValue newEntryKey;
        JSONValue newEntryValue;
        if (isJSONValue(key)) {
            newEntryKey = (JSONValue) key;
        } else {
            newEntryKey = Type.getObject(key);
        }
        if (isJSONValue(value)) {
            newEntryValue = (JSONValue) value;
        } else {
            newEntryValue = Type.getObject(value);
        }
        if (newEntryKey == null || newEntryValue == null) {
            return null;
        }
        return getValue().put(newEntryKey, newEntryValue);
    }

    @Override
    public JSONValue remove(Object key) {
        if (!isJSONValue(key)) {
            key = Type.getObject(key);
            if (key == null) return null;
        }
        return getValue().remove(key);
    }

    @Override
    public void putAll(Map<? extends JSONValue, ? extends JSONValue> m) {
        getValue().putAll(m);
    }

    public void putAllObjective(Map<? extends Object, ? extends Object> m) {
        EntryList<JSONValue, JSONValue> value = getValue();
        for (Entry<? extends Object, ? extends Object> entry : m.entrySet()) {
            JSONValue newEntryKey;
            JSONValue newEntryValue;
            if (isJSONValue(entry.getKey())) {
                newEntryKey = (JSONValue) entry.getKey();
            } else {
                newEntryKey = Type.getObject(entry.getKey());
            }
            if (isJSONValue(entry.getValue())) {
                newEntryValue = (JSONValue) entry.getValue();
            } else {
                newEntryValue = Type.getObject(entry.getValue());
            }
            if (newEntryKey == null || newEntryValue == null) continue;
            value.put(newEntryKey, newEntryValue);
        }
    }

    @Override
    public void clear() {
        getValue().clear();
    }

    @Override
    public Set<JSONValue> keySet() {
        return getValue().keySet();
    }

    @Override
    public Collection<JSONValue> values() {
        return getValue().values();
    }

    @Override
    public Set<Entry<JSONValue, JSONValue>> entrySet() {
        return getValue().entrySet();
    }
}
