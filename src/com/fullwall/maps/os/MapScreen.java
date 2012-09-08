package com.fullwall.maps.os;

import java.util.Arrays;

import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapCursor;
import org.bukkit.map.MapCursorCollection;

import com.fullwall.maps.attachments.KeyEvent;
import com.fullwall.maps.attachments.MouseClickEvent;
import com.fullwall.maps.attachments.MouseMoveEvent;
import com.fullwall.maps.interfaces.Screen;
import com.fullwall.maps.interfaces.ScreenAttachments;
import com.fullwall.maps.interfaces.ScreenRenderer;
import com.fullwall.maps.interfaces.StateHolder;
import com.fullwall.maps.listeners.PlayerListen.Key;
import com.fullwall.maps.listeners.PlayerListen.MouseClick;
import com.fullwall.maps.storage.DataKey;

public class MapScreen implements Screen {
    private final ScreenAttachments attachments = new MapScreenAttachments(this);
    private final Mouse mouse = new Mouse();
    private final ScreenRenderer screenRenderer;

    MapScreen(ScreenRenderer renderer, StateHolder<?> stateLoader) {
        stateLoader.addLoader(this);
        stateLoader.addSaver(this);
        screenRenderer = renderer;
    }

    @Override
    public void forwardKeyEvent(Key key, boolean pressed) {
        KeyEvent event = new KeyEvent(key, pressed);
        this.attachments.processKeyEvent(event);
    }

    @Override
    public void forwardMouseEvent(MouseClick click, boolean pressed) {
        if (click == MouseClick.Right && screenRenderer.contains(SpringboardRenderer.class)) {
            mouse.setDown(!mouse.isDown());
        }
        this.attachments.processMouseEvent(new MouseClickEvent(mouse, this, click, pressed));
    }

    @Override
    public void forwardRender(MapCanvas canvas) {
        MapCursorCollection cursors = canvas.getCursors();
        if (cursors.size() == 0 || cursors.getCursor(0) != mouse.mouse) {
            if (cursors.size() > 0)
                cursors.removeCursor(cursors.getCursor(0));
            cursors.addCursor(mouse.mouse);
        }
        this.attachments.render(this, canvas);
        this.screenRenderer.render(this, canvas);
    }

    @Override
    public ScreenAttachments getAttachments() {
        return this.attachments;
    }

    @Override
    public Mouse getMouse() {
        return this.mouse;
    }

    @Override
    public ScreenRenderer getRenderController() {
        return screenRenderer;
    }

    @Override
    public String getRootName() {
        return "screen";
    }

    @Override
    public void load(DataKey root, boolean empty) {
        if (empty) {
            Arrays.fill(screenRenderer.getScreenRaw(), (byte) 0);
            return;
        }
        mouse.setX((byte) root.getInt("mouseX"));
        mouse.setY((byte) root.getInt("mouseY"));
        mouse.setDown(root.getBoolean("mouseDown"));
        mouse.setVisible(root.getBoolean("mouseVisible"));
        screenRenderer.setScreenRaw(root.getByteArray("image"));
    }

    @Override
    public void moveMouse(int horizontal, int vertical) {
        if (!mouse.isDown())
            return;
        horizontal *= HORIZONTAL_SENSITIVITY;
        vertical *= vertical < 0 ? VERTICAL_SENSITIVITY_UP : VERTICAL_SENSITIVITY_DOWN;
        mouse.setX((byte) (mouse.getX() + horizontal));
        mouse.setY((byte) (mouse.getY() + vertical));
        attachments.processMouseEvent(new MouseMoveEvent(this, mouse, horizontal, vertical));
    }

    @Override
    public void save(DataKey root) {
        root.setInt("mouseX", mouse.getX());
        root.setInt("mouseY", mouse.getY());
        root.setBoolean("mouseVisible", mouse.isVisible());
        root.setBoolean("mouseDown", mouse.isDown());
        root.setByteArray("image", screenRenderer.getScreenRaw());
    }

    public class Mouse {
        private boolean down = true;
        private final MapCursor mouse = new MapCursor((byte) 0, (byte) 0, (byte) 14,
                MapCursor.Type.WHITE_POINTER.getValue(), true);

        private Mouse() {
        }

        public byte getX() {
            return mouse.getX();
        }

        public byte getY() {
            return mouse.getY();
        }

        public boolean isDown() {
            return this.down;
        }

        public boolean isVisible() {
            return mouse.isVisible();
        }

        private byte normalise(byte pos) {
            return pos < -127 ? -127 : pos > 127 ? 127 : pos;
        }

        public void setDown(boolean down) {
            this.down = down;
        }

        public void setVisible(boolean visible) {
            mouse.setVisible(visible);
        }

        public void setX(byte x) {
            mouse.setX(normalise(x));
        }

        public void setY(byte y) {
            mouse.setY(normalise(y));
        }
    }

    private static final double HORIZONTAL_SENSITIVITY = 1, VERTICAL_SENSITIVITY_UP = 1.4,
            VERTICAL_SENSITIVITY_DOWN = 3;
}
