package com.fullwall.maps.attachments;

import com.fullwall.maps.interfaces.Screen;
import com.fullwall.maps.os.MapScreen.Mouse;

public abstract class MouseEvent {
    protected final Mouse mouse;
    protected final Screen screen;
    protected final Type type;

    MouseEvent(Type type, Screen screen, Mouse mouse) {
        this.type = type;
        this.screen = screen;
        this.mouse = mouse;
    }

    public abstract void forward(MouseListener listener);

    public Mouse getMouse() {
        return mouse;
    }

    public Screen getScreen() {
        return screen;
    }

    public Type getType() {
        return this.type;
    }

    public enum Type {
        Click,
        Move;
    }
}