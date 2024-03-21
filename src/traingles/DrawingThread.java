package traingles;

import java.awt.geom.Point2D;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.AffineTransformOp;
import java.awt.geom.AffineTransform;
import java.awt.Component;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.GeneralPath;
import java.awt.Paint;

/**
 * <p>Title: triagles</p>
 *
 * <p>Description: nice triangles using recursive drawing
 * the drawing depends on 6 directional lines 1 for each 60 degree
 * </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: IT</p>
 *
 * @author Yasser Almohammad
 * @version 1.0
 */
public class DrawingThread extends Thread {
    public DrawingThread() {
    }

    public int height;
    public int height0;
    public int level = 1;

    private int x0;
    private int y0;
    private int x;
    private int y;

    /**
     * used repeatedly so fix it the better
     */
    static final double cos60 = Math.cos(Math.PI / 3);
    static final double sin60 = Math.sin(Math.PI / 3);

    public static boolean enableDrawing = true;
    public long step = 500; //500 millis by default
    public Graphics2D targetGraphics;
    public int targetHeight;
    public static BufferedImage image = null;
    public Graphics2D img = null; //imageGraphics
    //accessing the graphics will be syncronized
    AffineTransformOp op = new AffineTransformOp(new AffineTransform(),
                                                 AffineTransformOp.
                                                 TYPE_NEAREST_NEIGHBOR);
    Component ui;

    public DrawingThread(Graphics2D g, int height, int level, Component c) {
        super();
        ui = c;
        this.targetGraphics = g;
        this.height = height;
        this.level = level;
        image = new BufferedImage(height, height, BufferedImage.TYPE_INT_RGB);
        img = image.createGraphics();
        img.setColor(Color.white);
   //     img.fillRect(0,0,height,height);

        /*      AffineTransform t=new AffineTransform();
              Dimension dim = ui.getSize();
              double xFactor=(double)dim.width/height;
              double yFactor=(double)dim.height/height;
              t.scale(xFactor,yFactor);
         op= new AffineTransformOp(t,AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
         */
    }

    GeneralPath path = new GeneralPath();
    public void run() {
        height0 = height - 2;
        x0 = x = height - 1;
        y0 = y = 10;
        img.setColor(Color.WHITE);
        path.moveTo(x0, y0);
        drawA4(height0, level, img);

        drawA6(height0, level, img);

        drawA2(height0, level, img);
        path.closePath();
        img.setColor(Color.lightGray);
        img.fill(path);
        step();
        enableDrawing = false;
    }

    /**
     * 0 degree line
     * @param h int  the length of the edge
     * @param level int
     * @param g Graphics2D
     */
    public void drawA1(int h, int level, Graphics2D g) {
        if (!enableDrawing)
            return;
        if (level == 1) {
            x = x0 + h;
            g.drawLine(x0, y0, x, y);
            path.lineTo(x, y);
            step();
            x0 = x;
        } else {
            drawA1(h / 3, level - 1, g);
            drawA2(h / 3, level - 1, g);
            drawA6(h / 3, level - 1, g);
            drawA1(h / 3, level - 1, g);
        }
    }


    /**
     * 60 degrees line
     * @param h int  the length of the edge
     * @param level int
     * @param g Graphics2D
     */
    public void drawA2(int h, int level, Graphics2D g) {
        if (!enableDrawing)
            return;
        if (level == 1) {
            x = x0 + (int) (h * cos60);
            y = y0 - (int) (h * sin60);
            g.drawLine(x0, y0, x, y);
            path.lineTo(x, y);
            step();
            x0 = x;
            y0 = y;
        } else {
            drawA2(h / 3, level - 1, g);
            drawA3(h / 3, level - 1, g);
            drawA1(h / 3, level - 1, g);
            drawA2(h / 3, level - 1, g);
        }
    }


    /**
     * 120 degrees line
     * @param h int  the length of the edge
     * @param level int
     * @param g Graphics2D
     */

    public void drawA3(int h, int level, Graphics2D g) {
        if (!enableDrawing)
            return;
        if (level == 1) {
            x = x0 - (int) (h * cos60);
            y = y0 - (int) (h * sin60);
            g.drawLine(x0, y0, x, y);
            path.lineTo(x, y);
            step();
            x0 = x;
            y0 = y;
        } else {
            drawA3(h / 3, level - 1, g);
            drawA4(h / 3, level - 1, g);
            drawA2(h / 3, level - 1, g);
            drawA3(h / 3, level - 1, g);
        }
    }


    /**
     * 180 degrees line
     * @param h int  the length of the edge
     * @param level int
     * @param g Graphics2D
     */

    public void drawA4(int h, int level, Graphics2D g) {
        if (!enableDrawing)
            return;
        if (level == 1) {
            x = x0 - h;
            g.drawLine(x0, y0, x, y);
            path.lineTo(x, y);
            step();
            x0 = x;
        } else {
            drawA4(h / 3, level - 1, g);
            drawA5(h / 3, level - 1, g);
            drawA3(h / 3, level - 1, g);
            drawA4(h / 3, level - 1, g);
        }
    }


    /**
     * 240 degrees line
     * @param h int  the length of the edge
     * @param level int
     * @param g Graphics2D
     */

    public void drawA5(int h, int level, Graphics2D g) {
        if (!enableDrawing)
            return;
        if (level == 1) {
            x = x0 - (int) (h * cos60);
            y = y0 + (int) (h * sin60);
            g.drawLine(x0, y0, x, y);
            path.lineTo(x, y);
            step();
            x0 = x;
            y0 = y;

        } else {
            drawA5(h / 3, level - 1, g);
            drawA6(h / 3, level - 1, g);
            drawA4(h / 3, level - 1, g);
            drawA5(h / 3, level - 1, g);
        }
    }


    /**
     * 300 degrees line
     * @param h int  the length of the edge
     * @param level int
     * @param g Graphics2D
     */

    public void drawA6(int h, int level, Graphics2D g) {
        if (!enableDrawing)
            return;
        if (level == 1) {
            x = x0 + (int) (h * cos60);
            y = y0 + (int) (h * sin60);
            g.drawLine(x0, y0, x, y);
            path.lineTo(x, y);
            step();
            x0 = x;
            y0 = y;
        } else {
            drawA6(h / 3, level - 1, g);
            drawA1(h / 3, level - 1, g);
            drawA5(h / 3, level - 1, g);
            drawA6(h / 3, level - 1, g);
        }
    }


    /**
     * one step by sleeping for a while and updating the figure drawn
     */
    public void step() {
        try {
            sleep(step);
        } catch (InterruptedException ex) {
        }
        commitScene();
        //the next line is bad, but i had to put it for some troubles in my Sys. [??]
        ui.repaint(0, 0, 1, 1);

    }

    /**
     * all changes to the image draw is commited to the screen now
     */
    public void commitScene() {
        targetGraphics.drawImage(image, op, 0, 0);
    }

    private void jbInit() throws Exception {
    }


}
