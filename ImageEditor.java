import java.awt.*;
/*import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Color*/
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageEditor {
    int time;
    public ImageEditor() {
        this.time = 0;
    }
    public void updateTime(int forward) {
        time += forward;
    }
    public BufferedImage textWriter (BufferedImage bi, String font, String message, String orient, int offx, int offy, int size) {
        Graphics2D g2d = bi.createGraphics();
        Font f;
        try {
            f = Font.createFont(Font.TRUETYPE_FONT, new File("font files/" + font + ".ttf")).deriveFont((float)size);
            g2d.setFont(f);
        } catch (FontFormatException | IOException e) {
            System.out.println("What the hecc!?!?");
            System.exit(1); //a panic button to prevent Global Emergency State K-NCT 1st. Degree Assured-Civilization-Destruction Level Worldending Event that might arise when this one goes wrong
            e.printStackTrace();
        }
        int x = offx, y = offy;
        int txtW = g2d.getFontMetrics().stringWidth(message);
        int txtH = g2d.getFontMetrics().getHeight();
        if (orient.equals("upper left") || orient.equals("freeform")) {

        } else if (orient.equals("lower left")) {
            y = bi.getHeight() - txtH - offy;
        } else if (orient.equals("upper right")) {
            x = bi.getWidth() - txtW - offx;
        } else if (orient.equals("lower right")) {
            x = bi.getWidth() - txtW - offx;
            y = bi.getHeight() - txtH - offy;
        } else if (orient.equals("center")) {
            x = bi.getWidth() / 2 - txtW / 2 + offx;
            y = bi.getHeight() / 2 - txtH / 2 + offy;
        } else {
            message = "Invalid orientatiion!";
        }
        for (int dx = -(1 + size / 20); dx <= (1 + size / 20); dx++) {
            for (int dy = -(1 + size / 20); dy <= (1 + size / 20); dy++) {
                if (dx != 0 || dy != 0) {
                    g2d.setColor(Color.BLACK);
                    g2d.drawString(message, x + dx, y + dy);
                }
            }
        }
        g2d.setPaint(Color.WHITE);
        g2d.drawString(message, x, y);
        g2d.dispose();
        return bi;
    }
    public BufferedImage chromatoNoise(BufferedImage bi, int separFactor) {
        for (int y = 0; y < bi.getHeight(); y++) {
            for (int x = 0; x < bi.getWidth(); x++) {
                int rgb = bi.getRGB(x, y);
                int rgbs = bi.getRGB((x + separFactor) % bi.getWidth(), y);
                int r = (rgbs >> 16) & 0xff;
                int g = (rgb >> 8) & 0xff;
                int b = rgb & 0xff;
                bi.setRGB(x, y, (r << 16) | (g << 8) | b);
            }
        }
        return bi;
    }
    public BufferedImage colorNoise(BufferedImage bi, int RGBShift) {
        int w = bi.getWidth();
        int h = bi.getHeight();
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                int rgb = bi.getRGB(i, j);
                int third = (int)(Math.random() * 3);
                if (Math.random() > 0.5) {
                    RGBShift *= -1;
                }
                if (((rgb - 0x131313) > 0) && ((rgb - 0xECECEC) < 0)) {
                    if (third == 0) {
                        rgb += 0x13 * RGBShift;
                    } else if (third == 1) {
                        rgb += 0x1300 * RGBShift;
                    } else {
                        rgb += 0x130000 * RGBShift;
                    }
                }
                bi.setRGB(i, j, rgb);
            }
        }
        return bi;
    }
    public BufferedImage panningNoise(BufferedImage bi, double panF, int panM, double noise) {
        int w = bi.getWidth();
        int h = bi.getHeight();
        for (int i = 0; i < bi.getHeight(); i++) {
            double x = (double)(i - time % (1.3 * h)) / h;
            double div = Math.pow(x, panF) * 1e7;
            if (div == 0) {
                div++;
            }
            int pan = (int)((Math.sin(300 * x) / div) * 500);
            for (int j = 0; j < w; j++) {
                int jmkII;
                if (Math.random() <= noise) {
                    jmkII = (j + (int)(Math.random() * 1000)) % w;
                } else {
                    jmkII = (j + pan) % w;
                }
                if (jmkII < 0) {
                    jmkII += w;
                }
                int rgb = bi.getRGB(j, i);
                bi.setRGB(jmkII, i, rgb);
            }
        }
        return bi;
    }
    public BufferedImage lensNoise(BufferedImage bi, double lensFactor) {
        int w = bi.getWidth();
        int h = bi.getHeight();
        double cx = w / 2.0;
        double cy = h / 2.0;
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                double dx = x - cx;
                double dy = y - cy;
                double r = Math.sqrt(dx * dx + dy * dy);
                double factor = 1 - 0.000001 * lensFactor * (r * r);
                int srcX = (int)(cx + dx / factor);
                int srcY = (int)(cy + dy / factor);
                if (srcX >= 0 && srcX < w && srcY >= 0 && srcY < h) {
                    int rgb = bi.getRGB(srcX, srcY);
                    bi.setRGB(x, y, rgb);
                } else {
                    bi.setRGB(x, y, 0);
                }
            }
        }
        return bi;
    }
    public BufferedImage rotateImage(BufferedImage bi, double angle) {
        BufferedImage gg = new BufferedImage(bi.getWidth(), bi.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = gg.createGraphics();
        g.rotate(Math.toRadians(angle), bi.getWidth() / 2, bi.getHeight() / 2);
        g.drawImage(bi, (bi.getWidth() - gg.getWidth()) / 2, (bi.getHeight() - gg.getHeight()) / 2, null);
        g.dispose();
        return gg;
    }
    public BufferedImage recolorImage(BufferedImage bi, int hue) {
        for (int i = 0; i < bi.getWidth(); i++) {
            for (int j = 0; j < bi.getHeight(); j++) {
                Color c = new Color(bi.getRGB(i, j), true);
                Color cc = Color.getHSBColor((float)(hue / 360.0), 1 - (float)(c.getRed() / 255.0), (float)(c.getRed() / 255.0));
                bi.setRGB(i, j, new Color(cc.getRed(), cc.getGreen(), cc.getBlue(), c.getAlpha()).getRGB());
            }
        }
        return bi;
    }
}
