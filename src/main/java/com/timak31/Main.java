package com.timak31;

import com.timak31.snake.GameplayColl;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame obj = new JFrame();
        GameplayColl gameplayColl = new GameplayColl();

        obj.setBounds(10, 10, 905, 700);
        obj.setBackground(Color.DARK_GRAY);
        obj.setResizable(false);
        obj.setVisible(true);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.add(gameplayColl);
    }
}
