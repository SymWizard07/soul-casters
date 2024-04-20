package soulcasters;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

public final class Constants {
    
    private Constants() {}

    public static final int COLOR_STATUS_SUCCESS = new Color(24, 173, 42).getRGB();
    public static final int COLOR_STATUS_FAILURE = new Color(173, 12, 12).getRGB();

    public static final double ASPECT_RATIO = 320.0 / 200.0;
    public static final int GAME_WIDTH = 320;
    public static final int GAME_HEIGHT = 200;

    public static final Image resizeImage(Image originalImage, int targetWidth, int targetHeight) {
        // Create a new buffered image with the target dimensions and the type that supports transparency
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
    
        // Get the graphics object for the resized image and draw the original image onto it
        Graphics2D g2d = resizedImage.createGraphics();
        
        // Optionally apply rendering hints for better image quality
        g2d.setRenderingHint(java.awt.RenderingHints.KEY_INTERPOLATION, java.awt.RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
    
        // Draw the original image scaled to the new size
        g2d.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        g2d.dispose();
    
        return resizedImage;
    }
}
