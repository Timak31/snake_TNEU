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

    // Іконки на кожен поворот головою змійки
    private ImageIcon rightMouth;
    private ImageIcon leftMouth;
    private ImageIcon upMouth;
    private ImageIcon downMouth;

    // Початкова довжина
    private int lengthOfSnake = 3;

    private Timer timer;
    // Швидкість змійки, чим більше тим повільніше
    private int delay = 150;
    private ImageIcon snakeImage;

    // З назви ясно що то заголовок, той "банер" в ігрі
    private ImageIcon titleImage;

    private int moves = 0;

    // Координади "яблука"
    private ArrayList<Integer> enemyXPos = new ArrayList<>();
    private ArrayList<Integer> enemyYPos = new ArrayList<>();

    // Іконка яблука
    private ImageIcon enemyImage;

    private Random random = new Random();

    // Координати яблука
    private int xPos;
    private int yPos;

    // Кількість набраних балів
    private int scores = 0;

    public GameplayColl() {
        for (int x = 25; x <= 850; x+=25) {     //
            enemyXPos.add(x);                   //
        }                                       //  Забиваємо список можливими координатами,
        for (int y = 75; y <= 625; y+=25) {     //  де яблуко може з'явитися
            enemyYPos.add(y);                   //
        }                                       //

        for (int i = 0; i < 750; i++) {         //
            snakeYLength.add(0);                //  Заповнюємо список
            snakeXLength.add(0);                //
        }                                       //

        this.xPos = random.nextInt(enemyXPos.size());       //  Беремо випадкову позицію яблука по Х
        this.yPos = random.nextInt(enemyYPos.size());       //  та по Y

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics graphics) {

        if (moves == 0) {                                       //
            for (int i = 0, x = 125; i < 3; i++, x-=25) {       //  Вказуємо початкові координати для змійки, а саме
                snakeXLength.set(i, x);                         //  (125; 100) (100; 100) (100; 75)
                snakeYLength.set(i, 100);                       //
            }
        }

        graphics.setColor(Color.WHITE);                             //  Малюється фігура білого кольору,
        graphics.drawRect(24, 10, 851, 55);     //  фігура - прямокутник, починається з координат (24; 10), має довжину 851 і висоту 55

        titleImage = new ImageIcon(     // Створюєш іконку, яку берез з ресурсів за шляхом вказаним у ""
                Objects.requireNonNull(getClass().getClassLoader().getResource("assets/snaketitle.jpg"))
        );
        titleImage.paintIcon(this, graphics, 25, 11);   //  Малюєш цю іконку на координатах (25; 11)

        graphics.setColor(Color.WHITE);                         //  Так само малюєш фігуру білого кольору
        graphics.drawRect(24,74, 851, 577); //  це вже біла лінія навколо ігрового поля

        graphics.setColor(Color.BLACK);
        graphics.fillRect(25, 75, 850, 575);    // А от і саме ігрове поле ЧОРНОГО! кольору

        graphics.setColor(Color.WHITE);                                     // І  знову білий колір
        graphics.setFont(new Font("arial", Font.PLAIN, 14));    //  вказуєш шрифт і розмір тексту
        graphics.drawString("Scores: " + scores, 780, 30);      //   вказуєш сам текст і його координати

        graphics.setColor(Color.WHITE);                                         //
        graphics.setFont(new Font("arial", Font.PLAIN, 14));        //  Те саме що і вище
        graphics.drawString("Length: " + lengthOfSnake, 780, 50);   //

        rightMouth = new ImageIcon(             // І знову створюєш іконку, яку берез з ресурсів за шляхом вказаним у ""
                Objects.requireNonNull(getClass().getClassLoader().getResource("assets/rightmouth.png"))
        );
        rightMouth.paintIcon(this, graphics, snakeXLength.get(0), snakeYLength.get(0));     //  Малюєш голову змійки по координатам, які вона вже має (береш їх із списку snakeXLength)

        // Малюється все тіло змійки (початок з голови)
        for (int a = 0; a < lengthOfSnake; a++) {
            if (a == 0 && right) {      //  Якщо у нас голова повернута на право
                rightMouth = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("assets/rightmouth.png")));   //  малюємо їй праву голову
                rightMouth.paintIcon(this, graphics, snakeXLength.get(a), snakeYLength.get(a));                                            //
            }
            if (a == 0 && left) {       //  Якщо у нас голова повернута на ліво
                leftMouth = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("assets/leftmouth.png")));     //  малюємо їй ліву голову і т.д.
                leftMouth.paintIcon(this, graphics, snakeXLength.get(a), snakeYLength.get(a));                                             //
            }
            if (a == 0 && up) {
                upMouth = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("assets/upmouth.png")));
                upMouth.paintIcon(this, graphics, snakeXLength.get(a), snakeYLength.get(a));
            }
            if (a == 0 && down) {
                downMouth = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("assets/downmouth.png")));
                downMouth.paintIcon(this, graphics, snakeXLength.get(a), snakeYLength.get(a));
            }
            if (a != 0) {       // Це вже не голова, а саме тіло
                snakeImage = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("assets/snakeimage.png")));   // малюємо її тіло (картинки зможеш глянути, якщо тоббі буде легше зорієнтуватися)
                snakeImage.paintIcon(this, graphics, snakeXLength.get(a), snakeYLength.get(a));                                            //
            }
        }

        enemyImage = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("assets/enemy.png")));        // Створюєш яблуко

        if (enemyXPos.get(xPos).equals(snakeXLength.get(0)) && enemyYPos.get(yPos).equals(snakeYLength.get(0))) {   // Якщо позиція голови змійки співпадає з позицією яблука
            scores++;                                                               //  бал +1
            lengthOfSnake++;                                                        //  довжина змійки +1
            xPos = random.nextInt(enemyXPos.size());                                //  створюємо нову випадкову позицію для яблука по Х
            yPos = random.nextInt(enemyYPos.size());                                //  і нову позицію по Y
        }

        enemyImage.paintIcon(this, graphics, enemyXPos.get(xPos), enemyYPos.get(yPos));                                             // а тут тільки його малюєш по координатам, які задала вище

        for (int b = 1; b < lengthOfSnake; b++) {
            if (snakeXLength.get(b).equals(snakeXLength.get(0)) && snakeYLength.get(b).equals(snakeYLength.get(0))) {   // Перевіряємо чи змія зловила себе за хвіст
                up =false;                  //
                down = false;               //  В такому випадку ми зупиняємо її рух
                right = false;              //
                left = false;               //

                graphics.setColor(Color.WHITE);                                         //
                graphics.setFont(new Font("arial", Font.BOLD, 50));         //  Тут вказуємо, що ви програли
                graphics.drawString("Game Over", 300, 300);                  //     і для початку нової гри
                                                                                        //      потрібно нажати пробіл
                graphics.setFont(new Font("arial", Font.BOLD, 50));         //
                graphics.drawString("Space to RESTART", 200, 380);           //
            }
        }

        graphics.dispose();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {        // Відслідковує нажаття клавіатури
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {  // Якщо нажали пробіл то
            moves = 0;                              //
            scores  = 0;                            //  обнуляються набрані бали та довжина змійки
            lengthOfSnake = 3;                      //
            repaint();                              //
        }

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {  // Якщо нажати клавішу "право"
            moves++;                                //
            right = true;                           //
            if (!left) {                            //
                right = true;                       //  то кажемо що ми направляємося "направо"
                left = false;                       //
            }                                       //
                                                    //
            up = false;                             //
            down = false;                           //  і т.п. з іншими клавішами в руху, тут думаю ясно
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
    public void actionPerformed(ActionEvent e) {                        //  Тут вже відбувається сам процес руху
        timer.start();
        if (right) {                                                    //  Якщо ми рухаємося направо
            for (int r = lengthOfSnake-1; r >= 0; r--) {                //  r = довжині змійки -1, якщо r>=0 то виконується код в фігурних дужках, після виконання r = r-1, і так по-колу поки виконується умова
                snakeYLength.set(r+1, snakeYLength.get(r));             //  тут кажемо наступному значеню в масиві snakeYLength присвоїти його попередній
            }
            for (int r = lengthOfSnake; r >= 0; r--) {                  //  вище схожий код
                if (r == 0) {                                           //
                    snakeXLength.set(r, snakeXLength.get(r) + 25);      //  передвигаємо голову на 25 пікселів
                } else {
                    snakeXLength.set(r, snakeXLength.get(r-1));         //  передвигаємо тіло
                }
                if (snakeXLength.get(r) > 850) {                        //  якщо виходимо за межі ігрового поля,
                    snakeXLength.set(r, 25);                            //  то вийти з протилежного боку поля
                }
            }
            repaint();      // Перемалювати поле так як ми описали вище. Далі код повторюється майже так сам, тільки то для вертикалі (Y)
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