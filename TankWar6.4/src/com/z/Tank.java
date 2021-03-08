package com.z;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;
public class Tank {
    //坐标
    int x = 390;
    int y = 725;
    public static final int WIDTH = 30;
    public static final int HEIGHT = 30;
    public static final int SPEED = 5;

    boolean goodTank = true;
    private boolean live = true;

    int oldX;
    int oldY;

    public Direction direction = Direction.STOP;

    public Direction ptDirection = Direction.U;
    boolean isLeft = false, isUp = false, isRight = false, isDown = false;

    TankFrame tankFrame;

    public static Random random = new Random();

    int step = random.nextInt(12) + 3;
//    static BufferedImage[] bufferedImages;

    static BufferedImage tankL, tankLU, tankU, tankRU, tankR, tankRD, tankD, tankLD;

    //    Map map =
    static {
        try {
//            bufferedImages = new BufferedImage[]{
            tankL = ImageIO.read(Tank.class.getClassLoader().getResourceAsStream("images/tankL.gif"));
            tankLU = ImageIO.read(Tank.class.getClassLoader().getResourceAsStream("images/tankLU.gif"));
            tankU = ImageIO.read(Tank.class.getClassLoader().getResourceAsStream("images/tankU.gif"));
            tankRU = ImageIO.read(Tank.class.getClassLoader().getResourceAsStream("images/tankRU.gif"));
            tankR = ImageIO.read(Tank.class.getClassLoader().getResourceAsStream("images/tankR.gif"));
            tankRD = ImageIO.read(Tank.class.getClassLoader().getResourceAsStream("images/tankRD.gif"));
            tankD = ImageIO.read(Tank.class.getClassLoader().getResourceAsStream("images/tankD.gif"));
            tankLD = ImageIO.read(Tank.class.getClassLoader().getResourceAsStream("images/tankLD.gif"));
//            };
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Tank(int x, int y, boolean goodTank,
                Direction ptDirection, TankFrame tankFrame) {
        this.x = x;
        this.y = y;
        this.goodTank = goodTank;
        this.ptDirection = ptDirection;
        this.tankFrame = tankFrame;
    }

    public Tank(int x, int y, boolean goodTank, TankFrame tankFrame) {
        this.x = x;
        this.y = y;
        this.goodTank = goodTank;
        this.tankFrame = tankFrame;
    }

    public Tank(int x, int y, boolean goodTank,
                Direction ptDirection, TankFrame tankFrame, Direction direction) {
        this.x = x;
        this.y = y;
        this.goodTank = goodTank;
        this.ptDirection = ptDirection;
        this.tankFrame = tankFrame;
        this.direction = direction;
        this.oldX = x;
        this.oldY = y;
    }

//    public void setRandomDirection(){
//        com.z.Direction[] directions = com.z.Direction.values();
//        int rn = random.nextInt(directions.length);
//        com.z.Direction randomDirection = directions[rn];
//        this.randomDirection =  randomDirection;
//    }

    Tank(TankFrame tankFrame) {
        this.tankFrame = tankFrame;
    }

    Tank(TankFrame tankFrame, boolean goodTank) {
        this(tankFrame);
        this.goodTank = goodTank;
    }

    public Tank() {
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public void paint(Graphics g) {
        if (!live) {
            tankFrame.tanks.remove(this);
            return;
        }
//        g.drawImage(bufferedImages[0],300,300,null);

        int x1 = x + WIDTH / 2;
        int y1 = y + HEIGHT / 2;

        if (x < 10) x = 10;//此处的数字比计算数字大5，目的是让坦克不在同一位置重画，突出运动效果
        if (x + Tank.WIDTH > TankFrame.GAME_WIDTH - 10)
            x = 800 - 10 - Tank.WIDTH; //com.z.TankFrame.GAME_WIDTH - com.z.Tank.WIDTH - 10;
        if (y < 40) y = 40;
        if (y + Tank.HEIGHT > TankFrame.GAME_HEIGHT - 10) y = 800 - 10 - Tank.HEIGHT;

//        Color c = g.getColor();
        if (goodTank) {
            bloodBar.paint(g);
//            g.setColor(Color.MAGENTA);
        } else {
//            g.setColor(Color.CYAN);
        }
//        g.fillOval(x, y, WIDTH, HEIGHT);
//        g.setColor(c);

//        g.setColor(Color.BLACK);
        switch (ptDirection) {
            case L:
                g.drawImage(tankL,x,y,null);
                break;
            case LU:
                g.drawImage(tankLU,x,y,null);
                break;
            case U:
                g.drawImage(tankU,x,y,null);
                break;
            case RU:
                g.drawImage(tankRU,x,y,null);
                break;
            case R:
                g.drawImage(tankR,x,y,null);
                break;
            case RD:
                g.drawImage(tankRD,x,y,null);
                break;
            case D:
                g.drawImage(tankD,x,y,null);
                break;
            case LD:
                g.drawImage(tankLD,x,y,null);
                break;
        }
        if (direction != Direction.STOP) {
            ptDirection = direction;
        }
//        g.setColor(c);
        move();
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_CONTROL:
                fire();
                break;
            case KeyEvent.VK_Q:
                superFire();
                break;
            case KeyEvent.VK_A:
                revive();
                break;
            case KeyEvent.VK_LEFT:
                isLeft = true;
                break;
            case KeyEvent.VK_UP:
                isUp = true;
                break;
            case KeyEvent.VK_RIGHT:
                isRight = true;
                break;
            case KeyEvent.VK_DOWN:
                isDown = true;
                break;
        }
        getDirection();
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_LEFT:
                isLeft = false;
                break;
            case KeyEvent.VK_UP:
                isUp = false;
                break;
            case KeyEvent.VK_RIGHT:
                isRight = false;
                break;
            case KeyEvent.VK_DOWN:
                isDown = false;
                break;
        }
        getDirection();
    }

    public void getDirection() {
        if (!isLeft && !isUp && !isRight && !isDown) direction = Direction.STOP;
        else if (isLeft && !isUp && !isRight && !isDown) direction = Direction.L;
        else if (isLeft && isUp && !isRight && !isDown) direction = Direction.LU;
        else if (!isLeft && isUp && !isRight && !isDown) direction = Direction.U;
        else if (!isLeft && isUp && isRight && !isDown) direction = Direction.RU;
        else if (!isLeft && !isUp && isRight && !isDown) direction = Direction.R;
        else if (!isLeft && !isUp && isRight && isDown) direction = Direction.RD;
        else if (!isLeft && !isUp && !isRight && isDown) direction = Direction.D;
        else if (isLeft && !isUp && !isRight && isDown) direction = Direction.LD;
    }

    //坦克移动
    public void move() {
        oldX = x;
        oldY = y;
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
            case STOP:
                break;
        }

        if (!goodTank) {
            Direction[] directions = Direction.values();
            if (step == 0) {
                step = random.nextInt(24) + 3;
                int rn = random.nextInt(directions.length);
                direction = directions[rn];
            }
            step--;
            if (random.nextInt(40) > 35) fire();
        }
    }

    public Missile fire() {
        if (!live) return null;
        int x = this.x + WIDTH / 2 - Missile.WIDTH / 2;
        int y = this.y + HEIGHT / 2 - Missile.HEIGHT / 2;
        Missile missile = new Missile(x, y, ptDirection, tankFrame, goodTank);
        tankFrame.missiles.add(missile);
        return missile;
    }

    public Missile fire(Direction direction) {
        if (!live) return null;
        int x = this.x + WIDTH / 2 - Missile.WIDTH / 2;
        int y = this.y + HEIGHT / 2 - Missile.HEIGHT / 2;
        Missile missile = new Missile(x, y, direction, tankFrame, goodTank);
        tankFrame.missiles.add(missile);
        return missile;
    }

    public void superFire() {
        Direction[] directions = Direction.values();
        for (int i = 0; i < directions.length; i++) {
            if (directions[i] != Direction.STOP) {
                fire(directions[i]);
            }
        }
    }

    public Rectangle getRectangle() {
        Rectangle rectangle = new Rectangle(x, y, WIDTH, HEIGHT);
        return rectangle;
    }

    private void stay() {
        x = oldX;
        y = oldY;
    }

    public boolean collidesWithWall(Wall wall) {
        if (getRectangle().intersects(wall.getRectangle())) {
            stay();
            return true;
        }
        return false;
    }

    public boolean collidesWithTank(Tank tank) {
        if (getRectangle().intersects(tank.getRectangle()) && this != tank) {
//            direction = com.z.Direction.STOP;
            stay();
            return true;
        }
        return false;
    }

    public boolean collidesWithTanks(List<Tank> tanks) {
        for (int i = 0; i < tanks.size(); i++) {
            Tank tank = tanks.get(i);
            if (collidesWithTank(tank)) return true;
        }
        return false;
    }

    private Tank revive() {
        if (!this.isLive()) {
            this.setLive(true);
            life = 100;
        }
        return this;
    }

    private int life = 100;

    public int getLife() {
        return life;
    }

    public void setLife() {
        life -= 10;
    }

    private BloodBar bloodBar = new BloodBar();

    private class BloodBar {

        private void paint(Graphics g) {
            Color color = g.getColor();
//            g.setColor(Color.GREEN);
            g.drawRect(x, y - 10, WIDTH, 10);
            g.setColor(Color.MAGENTA);
            g.fillRect(x, y - 10, WIDTH * life / 100, 10);
//            g.fillRect(200,200,200,200);
            g.setColor(Color.YELLOW);
            g.drawString("" + life, x, y);
            g.setColor(color);
        }
    }
}
