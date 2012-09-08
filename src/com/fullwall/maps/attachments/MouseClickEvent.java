package com.fullwall.maps.attachments;

import com.fullwall.maps.interfaces.Screen;
import com.fullwall.maps.listeners.PlayerListen.MouseClick;
import com.fullwall.maps.os.MapScreen.Mouse;

public class MouseClickEvent extends MouseEvent {
    private final MouseClick click;
    private final boolean pressed;

    public MouseClickEvent(Mouse mouse, Screen screen, MouseClick click, boolean pressed) {
        super(Type.Click, screen, mouse);
        this.click = click;
        this.pressed = pressed;
    }

    @Override
    public void forward(MouseListener listener) {
        if (this.pressed)
            listener.onMouseDown(this);
        else
            listener.onMouseUp(this);
    }

    public MouseClick getClick() {
        return click;
    }

    public boolean isPressed() {
        return pressed;
    }
}
