package com.fullwall.maps.applications;

import com.fullwall.maps.interfaces.Renderable;

public interface CanvasRenderer extends Renderable {
    boolean clearPixelsOnRemove();

    RenderPriority getPriority();

    boolean isRendering();

    void setDirty();

    public enum RenderPriority {
        High,
        Highest,
        Low,
        Lowest,
        Normal;
    }
}
