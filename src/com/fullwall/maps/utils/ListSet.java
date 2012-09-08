package com.fullwall.maps.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * A list set sacrifices memory by maintaining both a list and a set to allow
 * O(1) {@link #contains(Object)} and indexed {@link #get(int)}. Note that
 * {@link #remove(Object)} must remove items from the list and remains O(n).
 * 
 * @author fullwall
 */
public class ListSet<T> implements Iterable<T> {
    private final List<T> list = new ArrayList<T>();
    private final Set<T> set = new HashSet<T>();

    public void add(T t) {
        if (set.contains(t))
            return;
        list.add(t);
        set.add(t);
    }

    public void addAll(Collection<T> collection) {
        list.addAll(collection);
        set.addAll(list);
    }

    public List<T> asList() {
        return Collections.unmodifiableList(list);
    }

    public Set<T> asSet() {
        return Collections.unmodifiableSet(set);
    }

    public boolean contains(T t) {
        return set.contains(t);
    }

    public T get(int index) {
        return list.get(index);
    }

    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }

    public boolean remove(T t) {
        if (!set.contains(t))
            return false;
        set.remove(t);
        list.remove(t);
        return true;
    }

    public int size() {
        return list.size();
    }
}
