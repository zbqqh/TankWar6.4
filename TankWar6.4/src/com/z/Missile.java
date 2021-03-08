package com.z;

import java.awt.*;
import java.util.List;

public class Missile {
    //左上角坐标
    int x, y;
    public static final int WIDTH = 10;
    public static final int HEIGHT = 10;
    public static final int SPEED = 10;

    Direction direction = Direction.U;

    private boolean live = true;

    TankFrame tankFrame;

    boolean good = true;

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public Missile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Missile() {
    }

    public Missile(int x, int y, Direction direction) {
        this(x, y);
        this.direction = direction;
    }

    public Missile(int x, int y, Direction direction, TankFrame tankFrame) {
        this(x, y, direction);
        this.tankFrame = tankFrame;
    }

    public Missile(int x, int y, Direction direction, TankFrame tankFrame, boolean good) {
        this(x, y, direction);
        this.tankFrame = tankFrame;
        this.good = good;
    }

    public void paint(Graphics g) {
        if (!live) {
            tankFrame.missiles.remove(this);
            return;
        }
        Color c = g.getColor();
        if (good) {
            g.setColor(Color.MAGENTA);
        } else {
            g.setColor(Color.YELLOW);
        }
        g.fillOval(x, y, WIDTH, HEIGHT);
        g.setColor(c);

        move();
    }

    public void move() {
        switch (direction) {
            case L:
                x -= SPEED;
                break;
            case LU:
                x -= SPEED;
                y -= SPEED;
                break;
            case U:
                y -= SPEED;
                break;
            case RU:
                x += SPEED;
                y -= SPEED;
                break;
            case R:
                x += SPEED;
                break;
            case RD:
                x += SPEED;
                y += SPEED;
                break;
            case D:
                y += SPEED;
                break;
            case LD:
                x -= SPEED;
                y += SPEED;
                break;
        }
        if (x < 0 || x > TankFrame.GAME_WIDTH || y < 0 || y > TankFrame.GAME_HEIGHT) {
            live = false;
        }
    }

    public Rectangle getRectangle() {
        Rectangle rectangle = new Rectangle(x, y, WIDTH, HEIGHT);
        return rectangle;
    }

    //导弹打坦克方法
    boolean hitTank(Tank tank) {
        if (this.getRectangle().intersects(tank.getRectangle()) && this.isLive() && tank.isLive()
                && good != tank.goodTank) {
            if (tank.goodTank) {
                tank.setLife();
                if (tank.getLife() <= 0) {
                    tank.setLive(false);
                    Explode explode = new Explode(tank.x, tank.y, tankFrame);
                    tankFrame.explodes.add(explode);
                }
            } else {
                tank.setLive(false);
                this.setLive(false);
                Explode explode = new Explode(tank.x, tank.y, tankFrame);
                tankFrame.explodes.add(explode);
            }
            return true;
        }
        return false;
    }

    public boolean hitTanks(List<Tank> tanks) {
        for (int i = 0; i < tanks.size(); i++) {
            Tank tank = tanks.get(i);
            if (hitTank(tank)) {
                return true;
            }
        }
        return false;
    }

    public boolean collidesWithWall(Wall wall) {
        if (this.getRectangle().intersects(wall.getRectangle())) {
            this.setLive(false);
            return true;
        }
        return false;
    }
}
