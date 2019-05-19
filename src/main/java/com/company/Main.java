package com.company;

import com.company.snake.GameplayColl;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame obj = new JFrame();                          // Створюємо пусте вікно
        GameplayColl gameplayColl = new GameplayColl();     // Створюємо нашу ігру

        obj.setBounds(10, 10, 905, 700);    //  Задаємо розміри і координати для вікна
        obj.setBackground(Color.DARK_GRAY);                     //  його колір
        obj.setResizable(false);                                //  не можна розтягувати
        obj.setVisible(true);                                   //  візуально його можна побачити, якщо поставити false, то вікна не буде видно
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);     //  вказуємо операцію для кнопки "закрити", якщо цього не зробити, то при нажатті на "закрити", вікно закриється але сама програма працюватиме далі
        obj.add(gameplayColl);                                  //  добавляємо в вікно саму гру
    }
}
