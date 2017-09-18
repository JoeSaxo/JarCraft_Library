package de.joesaxo.library.json;

import org.jarcraft.library.iotools.ClassDependencyHandler;

import java.util.*;

/**
 * Created by Jens on 22.08.2017.
 */
public class JSONList extends JSONValue<List<JSONValue>> implements List<JSONValue> {

    protected JSONList(boolean forceNull) {
        super(null);
    }

    public JSONList(List<? extends Object> value) {
        this();
        if (value != null) addAllObjective(value);
    }

    @Override
    protected boolean isType(Object value) {
        if (value == null) return false;
        if (!ClassDependencyHandler.dependsOn(List.class, value.getClass())) return false;
        List<Object> list = (List<Object>) value;
        for (Object object : list) {
            if (!isJSONValue(object) && Type.getType(object) == null) return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append('[');
        for (JSONValue valueJSON : getValue()) {
            b.append(valueJSON.toString());
            b.append(',');
        }
        if (getValue().size() > 0) b.deleteCharAt(b.length()-1);
        b.append(']');
        return b.toString();
    }

    @Override
    protected String toFormattedString(int tabs) {
        StringBuilder b = new StringBuilder();
        b.append('[');
        tabs++;
        for (JSONValue valueObject : getValue()) {
            b.append('\n');
            for (int i = 0; i < tabs; i++) {
                b.append('\t');
            }
            b.append(valueObject.toFormattedString(tabs));
            b.append(',');
        }
        tabs--;
        if (size() != 0) {
            b.deleteCharAt(b.length()-1);
            b.append('\n');
            for (int i = 0; i < tabs; i++) {
                b.append('\t');
            }
        }
        b.append(']');
        return b.toString();
    }

    @Override
    protected JSONValue<List<JSONValue>> getObject(String stringJSONValue) {
        List<JSONValue> values = new ArrayList<>();
        if (stringJSONValue.charAt(1) != ']') {
            int startIndex = 1;
            while (stringJSONValue.length() > startIndex) {
                Map.Entry<JSONValue, Integer> entry = getValueEntry(stringJSONValue.substring(startIndex));
                values.add(entry.getKey());
                startIndex += entry.getValue() + 1;
            }
        }
        return new JSONList(values);
    }

    @Override
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

    // ---------------------------------------------------------

    public JSONList() {
        super(new ArrayList<JSONValue>(){

            @Override
            public boolean equals(Object o) {
                if (super.equals(o)) return true;
                if (o == null) return false;
                if (!ClassDependencyHandler.dependsOn(List.class, o.getClass())) return false;
                try {
                    List<? extends Object> list = (List<? extends Object>) o;
                    if (list.size() != size()) return false;
                    for (int i = 0; i < size(); i++) {
                        if (get(i) == null) {
                            if (list.get(i) != null) return false;
                        } else {
                            if (!get(i).equals(list.get(i))) return false;
                        }
                    }
                    return true;
                } catch (ClassCastException e) {
                    return false;
                }
            }
        });
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
    public boolean contains(Object o) {
        if (!isJSONValue(o)) {
            o = Type.getObject(o);
            if (o == null) return false;
        }
        return getValue().contains(o);
    }

    @Override
    public Iterator<JSONValue> iterator() {
        return getValue().iterator();
    }

    @Override
    public Object[] toArray() {
        return getValue().toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return getValue().toArray(a);
    }

    @Override
    public boolean add(JSONValue value) {
        return getValue().add(value);
    }

    public boolean addObjective(Object value) {
        JSONValue newValue;
        if (isJSONValue(value)) {
            newValue = (JSONValue) value;
        } else {
            newValue = Type.getObject(value);
            if (newValue == null) return false;
        }
        return getValue().add(newValue);
    }

    @Override
    public boolean remove(Object o) {
        if (!isJSONValue(o)) {
            o = Type.getObject(o);
            if (o == null) return false;
        }
        return getValue().remove(o);
    }

    private Collection<? extends JSONValue> convertCollection(Collection<?> c) {
        List<JSONValue> list = new ArrayList<>();
        for (Object o : c) {
            if (!isJSONValue(o)) {
                o = Type.getObject(o);
            }
            if (o != null) list.add((JSONValue) o);
        }
        return list;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return getValue().containsAll(convertCollection(c));
    }

    @Override
    public boolean addAll(Collection<? extends JSONValue> c) {
        return getValue().addAll(c);
    }

    public boolean addAllObjective(Collection<? extends Object> c) {
        return getValue().addAll(convertCollection(c));
    }

    @Override
    public boolean addAll(int index, Collection<? extends JSONValue> c) {
        return getValue().addAll(index, c);
    }

    public boolean addAllObjective(int index, Collection<? extends Object> c) {
        return getValue().addAll(index, convertCollection(c));
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return getValue().removeAll(convertCollection(c));
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return getValue().retainAll(convertCollection(c));
    }

    @Override
    public void clear() {
        getValue().clear();
    }

    @Override
    public JSONValue get(int index) {
        return getValue().get(index);
    }

    @Override
    public JSONValue set(int index, JSONValue element) {
        return getValue().set(index, element);
    }

    public JSONValue setObjective(int index, Object element) {
        JSONValue newElement;
        if (isJSONValue(element)) {
            newElement = (JSONValue) element;
        } else {
            newElement = Type.getObject(element);
            if (newElement == null) return null;
        }
        return getValue().set(index, newElement);
    }

    @Override
    public void add(int index, JSONValue element) {
        getValue().add(index, element);
    }

    public boolean addObjective(int index, Object element) {
        JSONValue newElement;
        if (isJSONValue(element)) {
            newElement = (JSONValue) element;
        } else {
            newElement = Type.getObject(element);
            if (newElement == null) return false;
        }
        getValue().add(index, newElement);
        return true;
    }

    @Override
    public JSONValue remove(int index) {
        return getValue().remove(index);
    }

    @Override
    public int indexOf(Object o) {
        if (!isJSONValue(o)) {
            o = Type.getObject(o);
            if (o == null) return -1;
        }
        return getValue().indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        if (!isJSONValue(o)) {
            o = Type.getObject(o);
            if (o == null) return -1;
        }
        return getValue().lastIndexOf(o);
    }

    @Override
    public ListIterator<JSONValue> listIterator() {
        return getValue().listIterator();
    }

    @Override
    public ListIterator<JSONValue> listIterator(int index) {
        return getValue().listIterator(index);
    }

    @Override
    public List<JSONValue> subList(int fromIndex, int toIndex) {
        return getValue().subList(fromIndex, toIndex);
    }

}
