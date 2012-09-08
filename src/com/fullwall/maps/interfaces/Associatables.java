package com.fullwall.maps.interfaces;

public class Associatables {
    /**
     * Represents an object that can be associated with another, for use in
     * associators.
     * 
     * @see Associator
     * 
     * @author fullwall
     */
    public static interface Associatable<T> {
    }

    /**
     * Utility method to return a blank associatable.
     * 
     * @return
     */
    public static <T> Associatable<T> create() {
        return new Associatable<T>() {
        };
    }
}