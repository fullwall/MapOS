package com.fullwall.maps.attachments.builtin;

import com.fullwall.maps.attachments.ClickableArea;
import com.fullwall.maps.attachments.MouseEvent;

public class EmptyClickableArea implements ClickableArea {
    @Override
    public boolean intersects(MouseEvent event) {
        return false;
    }
}
