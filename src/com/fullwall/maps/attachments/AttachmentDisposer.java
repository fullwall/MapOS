package com.fullwall.maps.attachments;

import com.fullwall.maps.interfaces.ScreenAttachments;

/**
 * Represents an object that may hold any number of attachments.
 * 
 * @see ScreenAttachment
 * 
 * @author fullwall
 * 
 */
public interface AttachmentDisposer {
    /**
     * Disposes any screen attachments that this object may be holding from a
     * given instance of ScreenAttachments.
     */
    void disposeFrom(ScreenAttachments attachments);
}
