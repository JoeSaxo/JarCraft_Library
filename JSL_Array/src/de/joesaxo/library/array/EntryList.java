package de.joesaxo.library.array;

import java.util.*;

/**
 * Created by Jens on 26.08.2017.
 */
public class EntryList<K, V> implements Map<K, V> {

    List<Entry<K, V>> entryList;

    public EntryList() {
        entryList = new ArrayList<>();
    }

    @Override
    public int size() {
        return entryList.size();
    }

    @Override
    public boolean isEmpty() {
        return entryList.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return get(key) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        return getKey(value) != null;
    }

    @Override
    public V get(Object key) {
        for (Entry<K, V> entry : entryList) {
            if (entry.getKey().equals(key)) return entry.getValue();
        }
        return null;
    }

    public K getKey(Object value) {
        for (Entry<K, V> entry : entryList) {
            if (entry.getValue().equals(value)) return entry.getKey();
        }
        return null;
    }

    public K getLastKey(Object value) {
        for (int i = entryList.size()-1; i > -1; i--) {
            if (entryList.get(i).getValue().equals(value)) return entryList.get(i).getKey();
        }
        return null;
    }

    @Override
    public V put(K key, V value) {
        entryList.add(new Entry<>(key, value));
        return value;
    }


    public V put(Entry<K, V> entry) {
        entryList.add(new Entry<>(entry));
        return entry.value;
    }

    @Override
    public V remove(Object key) {
        for (Entry<K, V> entry : entryList) {
            if (entry.key.equals(key)) {
                entryList.remove(entry);
                return entry.value;
            }
        }
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (K key : m.keySet()) {
            put(key, m.get(key));
        }
    }

    @Override
    public void clear() {
        entryList.clear();
    }

    public List<K> keyList() {
        List<K> keyList = new ArrayList<>();
        for (Entry<K, V> entry : entryList) {
            keyList.add(entry.key);
        }
        return keyList;
    }

    @Override
    public java.util.Set<K> keySet() {
        return new Set(keyList());
    }

    public List<V> valueList() {
        List<V> valueList = new ArrayList<>();
        for (Entry<K, V> entry : entryList) {
            valueList.add(entry.value);
        }
        return valueList;
    }

    @Override
    public Collection<V> values() {
        return valueList();
    }

    public List<Entry<K, V>> entryList() {
        List<Entry<K, V>> entryList = new ArrayList<>();
        for (Entry<K, V> entry : this.entryList) {
            entryList.add(new Entry(entry));
        }
        return entryList;
    }

    @Override
    public java.util.Set<Map.Entry<K, V>> entrySet() {
        return new Set(entryList());
    }

    public class Entry<K, V> implements Map.Entry<K, V> {

        private final K key;
        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public Entry(Map.Entry<K, V> entry) {
            this(entry.getKey(), entry.getValue());
        }

        public Entry(K key) {
            this(key, null);
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            V tempValue = this.value;
            this.value = value;
            return tempValue;
        }

        @Override
        public boolean equals(Object o) {
            if (super.equals(o)) return true;
            if (o == null) return false;
            try {
                Map.Entry<? extends  K, ? extends V> entry = (Map.Entry<? extends  K, ? extends V>) o;
                if (key == null) {
                    if (entry.getKey() != null) return false;
                } else {
                    if (!key.equals(entry.getKey())) return false;
                }
                if (value == null) {
                    if (entry.getValue() != null) return false;
                } else {
                    if (!value.equals(entry.getValue())) return false;
                }
                return true;
            } catch (ClassCastException e) {
                return false;
            }
        }
    }

    private class Set<T> implements java.util.Set<T> {

        List<T> setList;

        public Set() {
            setList = new ArrayList<>();
        }

        public Set(List<T> list) {
            setList = new ArrayList<>();
            for (T item : list) {
                add(item);
            }
        }

        @Override
        public int size() {
            return setList.size();
        }

        @Override
        public boolean isEmpty() {
            return setList.isEmpty();
        }

        @Override
        public boolean contains(Object o) {
            return setList.contains(o);
        }

        @Override
        public Iterator<T> iterator() {
            return setList.iterator();
        }

        @Override
        public Object[] toArray() {
            return setList.toArray();
        }

        @Override
        public <T1> T1[] toArray(T1[] a) {
            return setList.toArray(a);
        }

        @Override
        public boolean add(T t) {
            if (setList.contains(t)) return false;
            return setList.add(t);
        }

        @Override
        public boolean remove(Object o) {
            return setList.remove(o);
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return setList.containsAll(c);
        }

        @Override
        public boolean addAll(Collection<? extends T> c) {
            boolean changed = false;
            for (T item : c) {
                if (add(item)) changed = true;
            }
            return changed;
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            return setList.retainAll(c);
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            return setList.removeAll(c);
        }

        @Override
        public void clear() {
            setList.clear();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (super.equals(o)) return true;
        if (o == null) return false;
        try {
            Map<? extends K, ? extends V> map = (Map<? extends K, ? extends V>) o;
            if (map.size() != entryList.size()) return false;
            java.util.Set<? extends Map.Entry<? extends K, ? extends V>> mapEntries = map.entrySet();
            java.util.Set<Map.Entry<K, V>> entries = entrySet();
            if (mapEntries.size() != entries.size()) return false;
            for (Map.Entry<? extends K, ? extends V> entry : mapEntries) {
                V value = get(entry.getKey());
                if (value == null) {
                    if (entry.getValue() != null) return false;
                } else {
                    if (!value.equals(entry.getValue())) return false;
                    //System.out.println(value.toString() + " | " + entry.getValue().toString() + " | " + value.equals(entry.getValue()));
                }
            }
            return true;
        } catch (ClassCastException e) {
            return false;
        }
    }
}
