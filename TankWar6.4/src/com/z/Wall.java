package com.z;

import java.awt.*;

public class Wall {
    int x,y,h,w;
    public TankFrame tankFrame;

    public Wall(int x, int y, int h, int w, TankFrame tankFrame) {
        this.x = x;
        this.y = y;
        this.h = h;
        this.w = w;
        this.tankFrame = tankFrame;
    }
    public Rectangle getRectangle() {
        Rectangle rectangle = new Rectangle( x,y,w,h);
        return rectangle;
    }

    public void paint(Graphics g){
        Color color = g.getColor();
        g.setColor(Color.GRAY);
        g.fillRect(x,y,w,h);
        g.setColor(color);
    }
}
