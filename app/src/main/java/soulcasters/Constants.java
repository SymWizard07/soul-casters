package soulcasters;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

public final class Constants {

    private Constants() {
    }

    public static final int COLOR_STATUS_SUCCESS = new Color(24, 173, 42).getRGB();
    public static final int COLOR_STATUS_FAILURE = new Color(173, 12, 12).getRGB();

    public static final double ASPECT_RATIO = 320.0 / 200.0;
    public static final int GAME_WIDTH = 320;
    public static final int GAME_HEIGHT = 200;

    public static final Image resizeImage(Image originalImage, int targetWidth, int targetHeight) {
        // Create a new buffered image with the target dimensions and the type that
        // supports transparency
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);

        // Get the graphics object for the resized image and draw the original image
        // onto it
        Graphics2D g2d = resizedImage.createGraphics();

        // Optionally apply rendering hints for better image quality
        g2d.setRenderingHint(java.awt.RenderingHints.KEY_INTERPOLATION,
                java.awt.RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

        // Draw the original image scaled to the new size
        g2d.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        g2d.dispose();

        return resizedImage;
    }

    public static final Image flipImage(Image originalImage) {
        BufferedImage buffered;
        // Create a new buffered image with transparency
        buffered = new BufferedImage(originalImage.getWidth(null), originalImage.getHeight(null),
                BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = buffered.createGraphics();
        bGr.drawImage(originalImage, 0, 0, null);
        bGr.dispose();

        // Create a new BufferedImage for the output which is horizontally flipped
        BufferedImage flipped = new BufferedImage(buffered.getWidth(), buffered.getHeight(),
                BufferedImage.TYPE_INT_ARGB);

        // Create a Graphics2D object to draw the flipped image
        Graphics2D g = flipped.createGraphics();

        // Draw the original image into the flipped BufferedImage with a transformation
        // that causes the horizontal flip by drawing from right to left
        g.drawImage(buffered, 0, 0, buffered.getWidth(), buffered.getHeight(), buffered.getWidth(), 0, 0,
                buffered.getHeight(), null);
        g.dispose();

        return flipped;
    }

    public static final String formatTime(int totalSeconds) {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;

        return String.format("%d:%02d", minutes, seconds); // Format time as m:ss
    }
}
