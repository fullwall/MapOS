package com.fullwall.maps.interfaces;

import com.fullwall.maps.attachments.AttachableSurface;
import com.fullwall.maps.attachments.KeyEvent;
import com.fullwall.maps.attachments.MouseEvent;
import com.fullwall.maps.attachments.ScreenAttachment;

public interface ScreenAttachments extends AttachableSurface<ScreenAttachment>, Renderable {
    void processKeyEvent(KeyEvent event);

    void processMouseEvent(MouseEvent event);
}
