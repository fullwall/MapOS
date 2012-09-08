package com.fullwall.maps.attachments;

import com.fullwall.maps.listeners.PlayerListen.Key;

public interface KeyListener extends ScreenAttachment {
    /**
     * Returns which key this listener is listening for. Should return null to
     * listen for all keys.
     */
    Key listenFor();

    void onKeyPress(KeyEvent event);

    void onKeyRelease(KeyEvent event);
}
