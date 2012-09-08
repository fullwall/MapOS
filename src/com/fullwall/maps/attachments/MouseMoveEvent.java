package com.fullwall.maps.attachments;

import com.fullwall.maps.interfaces.Screen;
import com.fullwall.maps.os.MapScreen.Mouse;

public class MouseMoveEvent extends MouseEvent {
    private final int distX, distY;
    private final int prevX;
    private final int prevY;

    public MouseMoveEvent(Screen screen, Mouse mouse, int horizontal, int vertical) {
        super(Type.Move, screen, mouse);
        this.prevX = mouse.getX() - horizontal;
        this.prevY = mouse.getY() - vertical;
        this.distX = horizontal;
        this.distY = vertical;
    }

    @Override
    public void forward(MouseListener listener) {
        listener.onMouseMove(this);
    }

    public int getDistanceX() {
        return distX;
    }

    public int getDistanceY() {
        return distY;
    }

    public int getNewX() {
        return mouse.getX();
    }

    public int getNewY() {
        return mouse.getY();
    }

    public int getPreviousX() {
        return prevX;
    }

    public int getPreviousY() {
        return prevY;
    }
}
