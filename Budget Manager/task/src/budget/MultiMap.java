package budget;

import java.util.*;

public class MultiMap {
    private SortedMap<Double, Collection<String>> map = new TreeMap<>((Comparator<Double>) (s1, s2) -> s2.compareTo(s1));

    /**
     * Add the specified value with the specified key in this multimap.
     */
    public void put(Double key, String value) {
        if (map.get(key) == null)
            map.put(key, new ArrayList<String>());

        map.get(key).add(value);
    }

    /**
     * Associate the specified key with the given value if not
     * already associated with a value
     */
    public void putIfAbsent(Double key, String value) {
        if (map.get(key) == null)
            map.put(key, new ArrayList<>());

        // if value is absent, insert it
        if (!map.get(key).contains(value)) {
            map.get(key).add(value);
        }
    }

    /**
     * Returns the Collection of values to which the specified key is mapped,
     * or null if this multimap contains no mapping for the key.
     */
    public Collection<String> get(Object key) {
        return map.get(key);
    }

    /**
     * Returns a Set view of the keys contained in this multimap.
     */
    public Set<Double> keySet() {
        return map.keySet();
    }

    /**
     * Returns a Set view of the mappings contained in this multimap.
     */
    public Set<Map.Entry<Double, Collection<String>>> entrySet() {
        return map.entrySet();
    }

    /**
     * Returns a Collection view of Collection of the values present in
     * this multimap.
     */
    public Collection<Collection<String>> values() {
        return map.values();
    }

    /**
     * Returns true if this multimap contains a mapping for the specified key.
     */
//    public boolean containsDoubleey(Object key) {
//        return map.containsDoubleey(key);
//    }

    /**
     * Removes the mapping for the specified key from this multimap if present
     * and returns the Collection of previous values associated with key, or
     * null if there was no mapping for key.
     */
    public Collection<String> remove(Object key) {
        return map.remove(key);
    }

    /**
     * Returns the number of key-value mappings in this multimap.
     */
    public int size() {
        int size = 0;
        for (Collection<String> value: map.values()) {
            size += value.size();
        }
        return size;
    }

    /**
     * Returns true if this multimap contains no key-value mappings.
     */
    public boolean isEmpty() {
        return map.isEmpty();
    }

    /**
     * Removes all of the mappings from this multimap.
     */
    public void clear() {
        map.clear();
    }

    /**
     * Removes the entry for the specified key only if it is currently
     * mapped to the specified value and return true if removed
     */
    public boolean remove(Double key, String value) {
        if (map.get(key) != null) // key exists
            return map.get(key).remove(value);

        return false;
    }

    /**
     * Replaces the entry for the specified key only if currently
     * mapped to the specified value and return true if replaced
     */
    public boolean replace(Double key, String oldValue, String newValue) {

        if (map.get(key) != null) {
            if (map.get(key).remove(oldValue))
                return map.get(key).add(newValue);
        }
        return false;
    }
}