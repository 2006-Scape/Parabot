package org.parabot.api.io.images;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 * 
 * Caches and loads images from resource
 * 
 * @author Everel, JKetelaar
 *
 */
public class Images {
    private static final HashMap<String, BufferedImage> IMAGE_CACHE = new HashMap<>();
	
	public static BufferedImage getResource(final String resource) {
		if(IMAGE_CACHE.containsKey(resource)) {
			return IMAGE_CACHE.get(resource);
		}
		try {
			final BufferedImage img = ImageIO.read(Images.class.getResourceAsStream(resource));
			IMAGE_CACHE.put(resource, img);
			return img;
		} catch (Throwable t) {
			throw new RuntimeException("Failed to load image from resource. " + t.getMessage());
		}
	}

}