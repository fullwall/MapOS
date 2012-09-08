package com.fullwall.maps.menus;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import com.fullwall.maps.attachments.ScreenAttachment;
import com.fullwall.maps.interfaces.Screen;
import com.fullwall.maps.interfaces.ScreenAttachments;
import com.fullwall.maps.interfaces.ScreenRenderer;
import com.google.common.collect.Sets;

public class SimpleMenu implements Menu {
    private final Set<ScreenAttachment> attachments = Sets.newHashSet();
    private final Collection<MenuItem> items;
    private final MenuRenderer renderer;
    private final MenuStyle style;
    private final MenuTitle title;

    public SimpleMenu(Collection<MenuItem> items) {
        this(items, new CenteredTitle(""), MenuStyle.Default);
    }

    public SimpleMenu(Collection<MenuItem> items, MenuTitle title, MenuStyle style) {
        // TODO: Add a custom menu item for going back to parent menus
        this.title = title;
        this.style = style;
        this.items = Collections.unmodifiableCollection(items);
        renderer = new SimpleMenuRenderer(style, title, items);
        for (MenuItem item : items) {
            attachments.add(item);
        }
    }

    @Override
    public void attach(Screen screen) {
        screen.getRenderController().attach(renderer);
        screen.getAttachments().attachAll(attachments);
    }

    @Override
    public void beginRendering() {
        renderer.beginRendering();
    }

    @Override
    public void disposeFrom(ScreenAttachments attachments) {
        attachments.removeAll(this.attachments);
    }

    @Override
    public void disposeFrom(ScreenRenderer renderer) {
        renderer.remove(this.renderer);
    }

    @Override
    public Collection<MenuItem> getItems() {
        return this.items;
    }

    @Override
    public MenuRenderer getRenderer() {
        return renderer;
    }

    @Override
    public MenuStyle getStyle() {
        return style;
    }

    @Override
    public MenuTitle getTitle() {
        return this.title;
    }
}
