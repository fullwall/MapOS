package com.fullwall.maps.os;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;

import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.craftbukkit.map.CraftMapCanvas;
import org.bukkit.craftbukkit.map.CraftMapView;
import org.bukkit.craftbukkit.map.RenderData;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapView;

import com.fullwall.maps.applications.CanvasRenderer;
import com.fullwall.maps.applications.CanvasRenderer.RenderPriority;
import com.fullwall.maps.interfaces.Screen;
import com.fullwall.maps.interfaces.ScreenRenderer;
import com.fullwall.maps.utils.MapPixel;
import com.fullwall.maps.utils.Point;
import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.SetMultimap;

class CanvasScreenRenderer implements ScreenRenderer {
    private final RenderData buffer = new RenderData();
    private final Multiset<Class<? extends CanvasRenderer>> classes = HashMultiset.create();
    private MapView map;
    private final SetMultimap<CanvasRenderer, MapPixel> pixels = HashMultimap.create();
    private final Player player;
    private final SetMultimap<RenderPriority, CanvasRenderer> renderers = HashMultimap.create();

    CanvasScreenRenderer(Player player, MapView map) {
        this.player = player;
        this.map = map;
    }

    @Override
    public void attach(CanvasRenderer toAttach) {
        this.renderers.put(toAttach.getPriority(), toAttach);
        classes.add(toAttach.getClass());
    }

    @Override
    public void attachAll(Collection<CanvasRenderer> toAttach) {
        for (CanvasRenderer renderer : toAttach) {
            attach(renderer);
        }
    }

    @Override
    public void clearAttached() {
        this.renderers.clear();
        this.classes.clear();
        this.pixels.clear();
    }

    @Override
    public void clearPixels(CanvasRenderer renderer) {
        if (!pixels.containsKey(renderer))
            return;
        for (MapPixel pixel : pixels.removeAll(renderer)) {
            Point point = pixel.getPoint();
            buffer.buffer[point.getY() * 128 + point.getX()] = 0;
        }
    }

    @Override
    public void clearScreen() {
        Arrays.fill(buffer.buffer, (byte) 0);
    }

    @Override
    public boolean contains(CanvasRenderer attachment) {
        Preconditions.checkNotNull(attachment, "renderer can't be null");
        return this.renderers.containsEntry(attachment.getPriority(), attachment);
    }

    @Override
    public boolean contains(Class<? extends CanvasRenderer> attachmentClass) {
        return classes.contains(attachmentClass);
    }

    private void directRender(Screen screen, CanvasRenderer renderer, MapCanvas canvas) {
        setBase(canvas, buffer.buffer);
        renderer.render(screen, canvas);
        byte[] canvasBuffer = getBuffer(canvas);
        for (int i = 0; i < canvasBuffer.length; ++i) {
            if (canvasBuffer[i] >= 0) {
                MapPixel pixel = new MapPixel(new Point(i % 128, i / 128), canvasBuffer[i]);
                if (pixels.containsValue(pixel))
                    pixels.values().remove(pixel);
                pixels.put(renderer, pixel);
                buffer.buffer[i] = canvasBuffer[i];
            }
        }
        setBase(canvas, buffer.buffer);
        Arrays.fill(getBuffer(canvas), (byte) -1);
    }

    @Override
    public void forceRefresh(boolean rerender) {
        if (rerender) {
            ((CraftMapView) map).render((CraftPlayer) player);
        }
        player.sendMap(map);
    }

    @Override
    public byte[] getScreenRaw() {
        return buffer.buffer;
    }

    private void merge(byte[] source, byte[] into) {
        for (int i = 0; i < source.length; ++i) {
            if (source[i] >= 0)
                into[i] = source[i];
        }
    }

    @Override
    public void remove(CanvasRenderer previous) {
        Preconditions.checkNotNull(previous, "renderer can't be null");
        if (!this.renderers.containsKey(previous))
            return;
        this.renderers.remove(previous.getPriority(), previous);
        classes.remove(previous.getClass());
        if (previous.clearPixelsOnRemove())
            clearPixels(previous);
        else
            pixels.removeAll(previous);
    }

    @Override
    public void removeAll(Collection<CanvasRenderer> previous) {
        for (CanvasRenderer prev : previous) {
            remove(prev);
        }
    }

    @Override
    public void render(Screen screen, MapCanvas canvas) {
        if (canvas.getMapView() != this.map)
            map = canvas.getMapView();
        Arrays.fill(getBuffer(canvas), (byte) -1);
        for (RenderPriority priority : RenderPriority.values()) {
            if (!renderers.containsKey(priority))
                continue;
            for (CanvasRenderer renderer : renderers.get(priority)) {
                if (!renderer.isRendering())
                    continue;
                directRender(screen, renderer, canvas);
            }
        }
        merge(buffer.buffer, getBuffer(canvas));
    }

    @Override
    public void setScreenRaw(byte[] buffer) {
        if (buffer.length != this.buffer.buffer.length)
            throw new IllegalArgumentException("expected array length of " + this.buffer.buffer.length
                    + ", got " + buffer.length);
        for (int i = 0; i < buffer.length; ++i)
            this.buffer.buffer[i] = buffer[i];
    }

    private static Method getBufferMethod;

    private static Method setBaseMethod;

    private static byte[] getBuffer(MapCanvas canvas) {
        try {
            return (byte[]) getBufferMethod.invoke(canvas);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private static void setBase(MapCanvas canvas, byte[] buffer) {
        try {
            setBaseMethod.invoke(canvas, buffer);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    static {
        try {
            setBaseMethod = CraftMapCanvas.class.getDeclaredMethod("setBase", byte[].class);
            setBaseMethod.setAccessible(true);
            getBufferMethod = CraftMapCanvas.class.getDeclaredMethod("getBuffer", (Class<?>[]) null);
            getBufferMethod.setAccessible(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
