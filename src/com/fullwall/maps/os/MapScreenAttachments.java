package com.fullwall.maps.os;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import org.bukkit.map.MapCanvas;

import com.fullwall.maps.attachments.Animation;
import com.fullwall.maps.attachments.Clickable;
import com.fullwall.maps.attachments.ClickableArea;
import com.fullwall.maps.attachments.KeyEvent;
import com.fullwall.maps.attachments.KeyListener;
import com.fullwall.maps.attachments.MouseEvent;
import com.fullwall.maps.attachments.MouseListener;
import com.fullwall.maps.attachments.ScreenAttachment;
import com.fullwall.maps.interfaces.Screen;
import com.fullwall.maps.interfaces.ScreenAttachments;
import com.fullwall.maps.listeners.PlayerListen.Key;
import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Iterators;
import com.google.common.collect.Multiset;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;

class MapScreenAttachments implements ScreenAttachments {
    private final Multiset<Class<? extends ScreenAttachment>> classes = HashMultiset.create();
    // TODO: remove this?
    private final SetMultimap<Key, KeyListener> keyListeners = HashMultimap.create();
    private final Set<MouseListener> mouseListeners = Sets.newLinkedHashSet();
    private final Set<Animation> runningAnimations = Sets.newLinkedHashSet();
    @SuppressWarnings("unused")
    private final Screen screen;

    MapScreenAttachments(Screen screen) {
        this.screen = screen;
    }

    @Override
    public void attach(ScreenAttachment toAttach) {
        Preconditions.checkNotNull(toAttach, "attachment can't be null");
        internalAttach(toAttach);
    }

    @Override
    public void attachAll(Collection<ScreenAttachment> toAttach) {
        for (ScreenAttachment attachment : toAttach) {
            attach(attachment);
        }
    }

    @Override
    public void clearAttached() {
        mouseListeners.clear();
        keyListeners.clear();
        runningAnimations.clear();
        classes.clear();
    }

    @Override
    public boolean contains(Class<? extends ScreenAttachment> attachmentClass) {
        return classes.contains(attachmentClass);
    }

    @Override
    public boolean contains(ScreenAttachment attachment) {
        if (attachment instanceof KeyListener
                && keyListeners.get(((KeyListener) attachment).listenFor()).contains(attachment))
            return true;
        if (attachment instanceof MouseListener && mouseListeners.contains(attachment))
            return true;
        if (attachment instanceof Animation && runningAnimations.contains(attachment))
            return true;
        return false;
    }

    private void internalAttach(ScreenAttachment toAttach) {
        Preconditions.checkNotNull(toAttach, "attachment can't be null");

        if (toAttach instanceof KeyListener)
            keyListeners.put(((KeyListener) toAttach).listenFor(), (KeyListener) toAttach);
        if (toAttach instanceof MouseListener)
            mouseListeners.add((MouseListener) toAttach);
        if (toAttach instanceof Animation)
            runningAnimations.add((Animation) toAttach);
        classes.add(toAttach.getClass());
    }

    private void internalRemove(ScreenAttachment attached) {
        Preconditions.checkNotNull(attached, "attachment can't be null");

        if (attached instanceof KeyListener)
            keyListeners.remove(((KeyListener) attached).listenFor(), attached);
        if (attached instanceof MouseListener)
            mouseListeners.remove(attached);
        if (attached instanceof Animation)
            runningAnimations.remove(attached);
        classes.remove(attached.getClass());
    }

    @Override
    public void processKeyEvent(KeyEvent event) {
        Iterator<KeyListener> iter = Iterators.concat(keyListeners.get(event.getKey()).iterator(),
                keyListeners.get(null).iterator());
        while (iter.hasNext()) {
            KeyListener listener = iter.next();
            if (event.isPressed())
                listener.onKeyPress(event);
            else
                listener.onKeyRelease(event);
        }
    }

    @Override
    public void processMouseEvent(MouseEvent event) {
        for (MouseListener listener : mouseListeners) {
            if (listener instanceof Clickable) {
                ClickableArea area = ((Clickable) listener).getClickableArea();
                if (!area.intersects(event))
                    continue;
            }
            event.forward(listener);
        }
    }

    @Override
    public void remove(ScreenAttachment attached) {
        internalRemove(attached);
    }

    @Override
    public void removeAll(Collection<ScreenAttachment> previous) {
        for (ScreenAttachment prev : previous) {
            remove(prev);
        }
    }

    @Override
    public void render(Screen screen, MapCanvas canvas) {
        Iterator<Animation> i = this.runningAnimations.iterator();
        while (i.hasNext()) {
            Animation running = i.next();
            running.incrementFrame();
            if (running.isFinished())
                i.remove();
        }
    }
}
