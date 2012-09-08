package com.fullwall.maps.interfaces;

public interface StateHolder<T> {
    void addLoader(Loadable loader);

    void addSaver(Saveable saver);

    T getGlobalStates();

    void load();

    void load(Loadable renderer);

    void save();

    void store(Saveable saver);
}
