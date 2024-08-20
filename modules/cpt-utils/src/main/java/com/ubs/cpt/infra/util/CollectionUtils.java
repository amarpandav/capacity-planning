package com.ubs.cpt.infra.util;

import java.util.*;

/**
 * @author Amar Pandav
 */
public class CollectionUtils {

    /**
     * Returns an iterable that is empty, not null if input was null.
     */
    public static <E> Iterable<E> nullSafeIterable(E[] iterable) {
        return iterable == null ? Collections.<E>emptyList() : Arrays.asList(iterable);
    }

    public static boolean isEmpty(Collection... collections) {
        for (Collection collection : collections) {
            if (!collection.isEmpty()) return false;
        }
        return true;
    }

    public static int calculateAggregateSize(Collection... collections) {
        int cnt = 0;
        for (Collection collection : collections) {
            cnt += collection.size();
        }
        return cnt;
    }

    /**
     * Returns an unmodifiable list for the specified collection.
     * <p/>
     * If the collection is null or empty {@link java.util.Collections#emptyList()} is
     * returned. Otherwise the collection is converted to a list (if necessary) and the
     * result of {@link java.util.Collections#unmodifiableList(java.util.List)} is
     * returned.
     *
     * @param collection collection to be converted to an unmodifiable list
     * @param <T>        type of collection items
     * @return unmodifiable list for the specified collection
     */
    public static <T> List<T> unmodifiableList(Collection<T> collection) {
        if (collection == null || collection.isEmpty()) {
            return Collections.emptyList();
        } else {
            if (collection instanceof List) {
                return Collections.unmodifiableList((List<T>) collection);
            } else {
                return Collections.unmodifiableList(new ArrayList<T>(collection));
            }
        }
    }

    /**
     * Looks for an object EQUAL to the given object in the list. If an object is found it returns the
     * object contained in the list and NOT the one given to this method
     *
     * @param objects list
     * @param object  objects
     * @param <T>     T
     * @return The object contained in the list
     */
    public static <T> T removeFromList(List<T> objects, T object) {
        int i = objects.indexOf(object);
        if (i == -1) {
            return null;
        }
        return objects.remove(i);
    }


    public static <K, E> void addToMappedCollection(Map<K, Collection<E>> map, K key, E elem) {
        Collection<E> coll = map.get(key);
        if (coll == null) {
            coll = new HashSet<E>();
            map.put(key, coll);
        }
        coll.add(elem);
    }

    public static <K, E> void addToMappedSet(Map<K, Set<E>> map, K key, E elem) {
        //noinspection unchecked
        addToMappedCollection((Map) map, key, elem);
    }

    public static <K, E> void addToMappedList(Map<K, List<E>> map, K key, E elem) {
        List<E> coll = map.get(key);
        if (coll == null) {
            coll = new ArrayList<E>();
            map.put(key, coll);
        }
        coll.add(elem);
    }

    public static <I, O> List<O> transform(Collection<I> list, Transformer<I, O> transformer) {
        List<O> output = new ArrayList<O>(list.size());
        for (I input : list) {
            output.add(transformer.transform(input));
        }
        return output;
    }

    public static <I, O> List<O> transform(I[] inputArray, Transformer<I, O> transformer) {
        List<O> output = new ArrayList<O>(inputArray.length);
        for (I input : inputArray) {
            output.add(transformer.transform(input));
        }
        return output;
    }

    public static interface Transformer<I, O> {
        O transform(I input);
    }

    public static <I, O> List<O> filterAndTransform(List<I> list, FilterAndTransformer<I, O> transformer) {
        List<O> output = new ArrayList<O>(list.size());
        for (I input : list) {
            if (transformer.matches(input)) {
                output.add(transformer.transform(input));
            }
        }
        return output;
    }

    public static interface FilterAndTransformer<I, O> extends Transformer<I, O> {
        boolean matches(I input);
    }

    public static <T> List<T> asListIgnoreNull(T... items) {
        if (items == null || items.length == 0) {
            return Collections.emptyList();
        }
        List<T> itemsWithoutNull = new ArrayList<T>(items.length);
        for (T item : items) {
            if (item != null) {
                itemsWithoutNull.add(item);
            }
        }
        return itemsWithoutNull;
    }

    public static interface Filter<T> {
        boolean matches(T t);
    }

    public static <T> List<T> filter(Collection<T> collection, Filter<T> filter) {
        List<T> list = new ArrayList<T>();
        for (T t : collection) {
            if (filter.matches(t)) {
                list.add(t);
            }
        }
        return list;
    }

    public static interface Finder<T> {
        boolean check(T t);
    }

    public static <T> T find(List<T> list, Finder<T> finder) {
        for (T t : list) {
            if (finder.check(t)) {
                return t;
            }
        }
        return null;
    }

    public static <T> List<T> union(Collection<T>... collections) {
        List<T> list = new ArrayList<T>();
        for (Collection<T> collection : collections) {
            for (T t : collection) {
                if (!list.contains(t)) {
                    list.add(t);
                }
            }
        }
        return list;
    }

    public static <K, V, T> Map<K, List<V>> toMap(Collection<T> collection, CollectionMapper<K, V, T> mapper) {
        Map<K, List<V>> map = new HashMap<K, List<V>>();
        for (T t : collection) {
            K key = mapper.extractKey(t);
            if (!map.containsKey(key)) {
                map.put(key, new ArrayList<V>());
            }
            map.get(key).add(mapper.extractValue(t));
        }
        return map;
    }

    public static interface CollectionMapper<K, V, T> {
        K extractKey(T t);

        V extractValue(T t);
    }

    public static <I, O> List<O> expand(List<I> list, ExpandCallback<I, O> callback) {
        List<O> output = new ArrayList<O>();
        for (I i : list) {
            output.addAll(callback.expand(i));
        }
        return output;
    }

    public static interface ExpandCallback<I, O> {
        List<O> expand(I i);
    }

    public static <T> void split(List<T> list, int blockSize, SubListCallback<T> callback) {
        Iterator<List<T>> iterator = new ListSplitter<T>(list, blockSize).iterator();
        while (iterator.hasNext()) {
            callback.doWithSubList(iterator.next());
        }
    }

    public static interface SubListCallback<T> {
        void doWithSubList(List<T> list);
    }
}
