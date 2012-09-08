package com.fullwall.maps.attachments;

import com.fullwall.maps.listeners.PlayerListen.Key;

public class KeyEvent {
    private final Key key;
    private final boolean pressed;

    public KeyEvent(Key key, boolean pressed) {
        this.key = key;
        this.pressed = pressed;
    }

    public Key getKey() {
        return key;
    }

    public boolean isPressed() {
        return pressed;
    }
}