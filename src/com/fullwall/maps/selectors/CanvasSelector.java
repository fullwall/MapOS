package com.fullwall.maps.selectors;

import com.fullwall.maps.interfaces.Screen;

public interface CanvasSelector {
	SelectedArea select(Screen screen, int x, int y);
}
