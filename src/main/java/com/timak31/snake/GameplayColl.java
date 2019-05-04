package com.timak31.snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class GameplayColl extends JPanel implements KeyListener, ActionListener {

    private ArrayList<Integer> snakeXLength = new ArrayList<>();
    private ArrayList<Integer> snakeYLength = new ArrayList<>();

    private boolean left = false;
    private boolean right = false;
    private boolean up = false;
    private boolean down = false;

    private ImageIcon rightMouth;
    private ImageIcon leftMouth;
    private ImageIcon upMouth;
    private ImageIcon downMouth;

    private int lengthOfSnake = 3;

    private Timer timer;
    private int delay = 150;
    private ImageIcon snakeImage;

    private ImageIcon titleImage;

    private int moves = 0;

    private ArrayList<Integer> enemyXPos = new ArrayList<>();
    private ArrayList<Integer> enemyYPos = new ArrayList<>();

    private ImageIcon enemyImage;

    private Random random = new Random();

    private int xPos;
    private int yPos;

    private int scores = 0;

    public GameplayColl() {
        for (int x = 25; x <= 850; x+=25) {
            enemyXPos.add(x);
        }
        for (int y = 75; y <= 625; y+=25) {
            enemyYPos.add(y);
        }

        for (int i = 0; i < 750; i++) {
            snakeYLength.add(0);
            snakeXLength.add(0);
        }

        this.xPos = random.nextInt(enemyXPos.size());
        this.yPos = random.nextInt(enemyYPos.size());

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics graphics) {

        if (moves == 0) {
            for (int i = 0, x = 125; i < 3; i++, x-=25) {
                snakeXLength.set(i, x);
                snakeYLength.set(i, 100);
            }
        }

        graphics.setColor(Color.WHITE);
        graphics.drawRect(24, 10, 851, 55);

        titleImage = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("assets/snaketitle.jpg")));
        titleImage.paintIcon(this, graphics, 25, 11);

        graphics.setColor(Color.WHITE);
        graphics.drawRect(24,74, 851, 577);

        graphics.setColor(Color.BLACK);
        graphics.fillRect(25, 75, 850, 575);

        graphics.setColor(Color.WHITE);
        graphics.setFont(new Font("arial", Font.PLAIN, 14));
        graphics.drawString("Scores: " + scores, 780, 30);

        graphics.setColor(Color.WHITE);
        graphics.setFont(new Font("arial", Font.PLAIN, 14));
        graphics.drawString("Length: " + lengthOfSnake, 780, 50);

        rightMouth = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("assets/rightmouth.png")));
        rightMouth.paintIcon(this, graphics, snakeXLength.get(0), snakeYLength.get(0));

        for (int a = 0; a < lengthOfSnake; a++) {
            if (a == 0 && right) {
                rightMouth = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("assets/rightmouth.png")));
                rightMouth.paintIcon(this, graphics, snakeXLength.get(a), snakeYLength.get(a));
            }
            if (a == 0 && left) {
                leftMouth = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("assets/leftmouth.png")));
                leftMouth.paintIcon(this, graphics, snakeXLength.get(a), snakeYLength.get(a));
            }
            if (a == 0 && up) {
                upMouth = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("assets/upmouth.png")));
                upMouth.paintIcon(this, graphics, snakeXLength.get(a), snakeYLength.get(a));
            }
            if (a == 0 && down) {
                downMouth = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("assets/downmouth.png")));
                downMouth.paintIcon(this, graphics, snakeXLength.get(a), snakeYLength.get(a));
            }
            if (a != 0) {
                snakeImage = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("assets/snakeimage.png")));
                snakeImage.paintIcon(this, graphics, snakeXLength.get(a), snakeYLength.get(a));
            }
        }

        enemyImage = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("assets/enemy.png")));

        if (enemyXPos.get(xPos).equals(snakeXLength.get(0)) && enemyYPos.get(yPos).equals(snakeYLength.get(0))) {
            scores++;
            lengthOfSnake++;
            xPos = random.nextInt(enemyXPos.size());
            yPos = random.nextInt(enemyYPos.size());
        }

        enemyImage.paintIcon(this, graphics, enemyXPos.get(xPos), enemyYPos.get(yPos));

        for (int b = 1; b < lengthOfSnake; b++) {
            if (snakeXLength.get(b).equals(snakeXLength.get(0)) && snakeYLength.get(b).equals(snakeYLength.get(0))) {
                up =false;
                down = false;
                right = false;
                left = false;

                graphics.setColor(Color.WHITE);
                graphics.setFont(new Font("arial", Font.BOLD, 50));
                graphics.drawString("Game Over", 300, 300);

                graphics.setFont(new Font("arial", Font.BOLD, 50));
                graphics.drawString("Space to RESTART", 200, 380);
            }
        }

        graphics.dispose();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            moves = 0;
            scores  = 0;
            lengthOfSnake = 3;
            repaint();
        }

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            moves++;
            right = true;
            if (!left) {
                right = true;
                left = false;
            }

            up = false;
            down = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            moves++;
            left = true;
            if (!right) {
                right = false;
                left = true;
            }

            up = false;
            down = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            moves++;
            down = true;
            if (!up) {
                up = false;
                down = true;
            }

            left = false;
            right = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            moves++;
            up = true;
            if (!down) {
                down = false;
                up = true;
            }

            left = false;
            right = false;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if (right) {
            for (int r = lengthOfSnake-1; r >= 0; r--) {
                snakeYLength.set(r+1, snakeYLength.get(r));
            }
            for (int r = lengthOfSnake; r >= 0; r--) {
                if (r == 0) {
                    snakeXLength.set(r, snakeXLength.get(r) + 25);
                } else {
                    snakeXLength.set(r, snakeXLength.get(r-1));
                }
                if (snakeXLength.get(r) > 850) {
                    snakeXLength.set(r, 25);
                }
            }
            repaint();
        }
        if (left) {
            for (int r = lengthOfSnake-1; r >= 0; r--) {
                snakeYLength.set(r+1, snakeYLength.get(r));
            }
            for (int r = lengthOfSnake; r >= 0; r--) {
                if (r == 0) {
                    snakeXLength.set(r, snakeXLength.get(r) - 25);
                } else {
                    snakeXLength.set(r, snakeXLength.get(r-1));
                }
                if (snakeXLength.get(r) < 25) {
                    snakeXLength.set(r, 850);
                }
            }
            repaint();
        }

        if (up) {
            for (int r = lengthOfSnake-1; r >= 0; r--) {
                snakeXLength.set(r+1, snakeXLength.get(r));
            }
            for (int r = lengthOfSnake; r >= 0; r--) {
                if (r == 0) {
                    snakeYLength.set(r, snakeYLength.get(r)-25);
                } else {
                    snakeYLength.set(r, snakeYLength.get(r-1));
                }
                if (snakeYLength.get(r) < 65) {
                    snakeYLength.set(r, 625);
                }
            }
            repaint();
        }
        if (down) {
            for (int r = lengthOfSnake-1; r >= 0; r--) {
                snakeXLength.set(r+1, snakeXLength.get(r));
            }
            for (int r = lengthOfSnake; r >= 0; r--) {
                if (r == 0) {
                    snakeYLength.set(r, snakeYLength.get(r) + 25);
                } else {
                    snakeYLength.set(r, snakeYLength.get(r-1));
                }
                if (snakeYLength.get(r) > 625) {
                    snakeYLength.set(r, 75);
                }
            }

            repaint();
        }
    }
}