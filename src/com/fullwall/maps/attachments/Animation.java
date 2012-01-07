package com.fullwall.maps.attachments;

import com.fullwall.maps.applications.CanvasRenderer;

/**
 * A representation of an animation. This type of animation runs until
 * {@link #isFinished()} returns true. For best use, implementations should
 * always return true at some point. Note that this can be terminated at any
 * time by the screen.
 * 
 * @author fullwall
 */
public interface Animation extends ScreenAttachment, CanvasRenderer {
	/**
	 * Used to notify the animation of its status. Will be incremented every
	 * render pass.
	 */
	void incrementFrame();

	/**
	 * @return whether the represented animation is finished.
	 */
	boolean isFinished();
}
