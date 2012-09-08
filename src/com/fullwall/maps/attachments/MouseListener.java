package com.fullwall.maps.attachments;

public interface MouseListener extends ScreenAttachment {
    void onMouseDown(MouseClickEvent event);

    void onMouseMove(MouseMoveEvent event);

    void onMouseUp(MouseClickEvent event);
}
