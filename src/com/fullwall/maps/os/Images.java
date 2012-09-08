package com.fullwall.maps.os;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

import org.bukkit.map.MapPalette;

import com.fullwall.maps.applications.ApplicationIcon;
import com.fullwall.maps.applications.ApplicationIcon.IconScale;
import com.fullwall.maps.utils.MapColour;
import com.fullwall.maps.utils.Messaging;

public class Images {
    public static final ApplicationIcon MissingApp;

    /**
     * Loads an image into the given byte array from the provided file. The byte
     * array will be filled with the equivalent map colours, or the white map
     * colour if unable to load from the file.
     */
    public static void loadImage(byte[] image, File src) {
        try {
            BufferedImage img = ImageIO.read(src);
            System.arraycopy(MapPalette.imageToBytes(img), 0, image, 0, image.length);
        } catch (IOException ex) {
            Arrays.fill(image, MapColour.White.colour());
            Messaging.log("Unable to load " + src.getName() + ".");
            ex.printStackTrace();
        }
    }

    /**
     * Loads an image using {@link Images#loadImage(byte[], File)}, and verifies
     * the dimensions of the result using the provided <tt>IconScale</tt>.
     */
    public static void loadImage(byte[] image, File src, IconScale scale) {
        loadImage(image, src);
        if (image.length != scale.getArrayLength()) {
            throw new IllegalStateException(src.getName() + " had an illegal size (expected "
                    + scale.getArrayLength() + ", got " + image.length + ")");
        }
    }

    static {
        byte[] image = new byte[IconScale.Layout.getArrayLength()];
        loadImage(image, new File("plugins/MapPlugin/images/missing.png"), IconScale.Layout);
        MissingApp = new ApplicationIcon("Missing", IconScale.Layout, image);
    }
}
