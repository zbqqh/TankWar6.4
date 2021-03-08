package com.z;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class Explode {
    int x = 375;
    int y = 500;
    static Toolkit toolkit = Toolkit.getDefaultToolkit();
//    static Image[] images;
    static BufferedImage b1,b2,b3,b4,b5,b6,b7,b8,b9,b10,b11,b12,b13,b14,b15,b16;

    static {

//        for (int i = 0; i < 14; i++) {
//            try {
//                bufferedImages[i] = ImageIO.read(
//                        Explode.class.getClassLoader().getResourceAsStream("images/e" + i + ".gif"));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        try {
            b1 = ImageIO.read(Tank.class.getClassLoader().getResourceAsStream("images/e1.gif"));
            b2 = ImageIO.read(Tank.class.getClassLoader().getResourceAsStream("images/e2.gif"));
            b3 = ImageIO.read(Tank.class.getClassLoader().getResourceAsStream("images/e3.gif"));
            b4 = ImageIO.read(Tank.class.getClassLoader().getResourceAsStream("images/e4.gif"));
            b5 = ImageIO.read(Tank.class.getClassLoader().getResourceAsStream("images/e5.gif"));
            b6 = ImageIO.read(Tank.class.getClassLoader().getResourceAsStream("images/e6.gif"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    int step = 0;

    private boolean live = true;

    TankFrame tankFrame;

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }


    public Explode(int x, int y, TankFrame tankFrame) {
        this.x = x;
        this.y = y;
        this.tankFrame = tankFrame;
    }

    void paint(Graphics g) {
        if (!live) {
            tankFrame.explodes.remove(this);
            return;
        }
//        if (step == ) {
//            live = false;
//            step = 0;
//            return;
//        }
        g.drawImage(b6, x, y, null);
//        step++;
    }
}
