package com.z;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class TankFrame extends Frame {
    int x = 350;
    int y = 0;
    public static final int GAME_WIDTH = 800;
    public static final int GAME_HEIGHT = 800;

    Image offScreenImage;

    Tank tank = new Tank(this, true);

    List<Tank> tanks = new ArrayList<Tank>();

    List<Tank> enemyTanks = new ArrayList<Tank>();

    List<Missile> missiles = new ArrayList<Missile>();

    List<Explode> explodes = new ArrayList<Explode>();

    Wall wallOne = new Wall(200,300,300,50,this);
    Wall wallTwo = new Wall(450,300,50,200,this);


    public void launchFrame() {
        setVisible(true);
        setLocation(x, y);
        setSize(GAME_WIDTH, GAME_HEIGHT);
        setBackground(Color.BLACK);
        setTitle("com.z.Tank War");
        new Thread(new RepaintThread()).start();
        addWindowListener(new MyWindowMonitor());
        addKeyListener(new MyKeyMonitor());

        for (int i = 0; i < 10; i++) {
            Tank tank = new Tank(50 + 70 * i, 100, false,
                    Direction.D, this, Direction.D);
            tanks.add(tank);
        }
    }

    //添加监听器类
    class MyWindowMonitor extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            setVisible(false);
            System.exit(0);
        }
    }

    class MyKeyMonitor extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            tank.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            tank.keyReleased(e);
        }
    }
    //画个圆代替坦克

    @Override
    public void paint(Graphics g) {


        for (int i = 0; i < explodes.size(); i++) {
            Explode explode = explodes.get(i);
            explode.paint(g);
        }
        for (int i = 0; i < missiles.size(); i++) {
            Missile missile = missiles.get(i);
            missile.hitTanks(tanks);
            missile.hitTank(tank);
            missile.collidesWithWall(wallOne);
            missile.collidesWithWall(wallTwo);
            missile.paint(g);
        }

        for (int i = 0; i < tanks.size(); i++) {
            Tank tank = tanks.get(i);
            tank.collidesWithWall(wallOne);
            tank.collidesWithWall(wallTwo);
//            System.out.println(tank.x + "," + tank.y);
//            System.out.println(tank.oldX + "," + tank.oldY);

            tank.collidesWithTanks(tanks);
            tank.paint(g);
        }
        tank.collidesWithTanks(tanks);
        tank.paint(g);

        Color c = g.getColor();
        g.setColor(Color.CYAN);
        g.drawString("missileCounts" + missiles.size(), 10, 50);
        g.drawString("explodeCounts" + explodes.size(), 10, 70);
        g.drawString("tank   Counts" + tanks.size(), 10, 90);
        g.setColor(c);

        wallOne.paint(g);
        wallTwo.paint(g);
//        repaint();//如果不创建repaint线程，方块刷新过快，导致看不到方块
    }

    //重写update方法，实现双缓冲
    @Override
    public void update(Graphics g) {
        if (offScreenImage == null){
            offScreenImage = createImage(GAME_WIDTH, GAME_HEIGHT);
        }
        Graphics goffScreen = offScreenImage.getGraphics();
        Color color = goffScreen.getColor();
        goffScreen.setColor(Color.BLACK);
        goffScreen.fillRect(0,0,GAME_WIDTH,GAME_HEIGHT);
        goffScreen.setColor(color);
        paint(goffScreen);
        g.drawImage(offScreenImage, 0, 0, null);

    }

    //创建线程，定义repaint刷新频率
    class RepaintThread implements Runnable {
        @Override
        public void run() {
            while (true) {
                repaint();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //坦克越界情况

}
