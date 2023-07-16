package com.main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Frame {
    private JFrame mainFrame;
    private JPanel mainPanel;
    private JButton grid[][];
    private JButton start;
    private JButton reset;
    private Image mainImage;
    private BufferedImage myImage;

    static final int WIDTH = 1600;
    static final int HEIGHT = 900;

    int cax = 100;
    int cay = 100;

    public void setup(String title) {

        try {
            mainImage = ImageIO.read(new File("ErosionScaled4.bmp"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        myImage = new BufferedImage(((BufferedImage) mainImage).getWidth(), ((BufferedImage) mainImage).getHeight(), BufferedImage.TYPE_INT_RGB);

        cax = myImage.getWidth();
        cay = myImage.getHeight();

        for(int i = 0; i < cax; i++) {
            for(int j = 0; j < cay; j++) {
                Color c = new Color(((BufferedImage) mainImage).getRGB(i, j));
//                System.out.println(i + " " + j + " " + c);
                if(c.getRed() < 240) {
                    myImage.setRGB(i, j, 0);
                } else {
                    myImage.setRGB(i, j, 16777215);
                }
            }
        }


        mainFrame = new JFrame(title);
        mainPanel = new JPanel();
        grid = new JButton[cax][cay];
        start = new JButton("START");
        reset = new JButton("STOP");

        mainPanel.setLayout(new GridLayout(cax, cay));

        mainFrame.setLayout(new BorderLayout());
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocation(new Point(100, 20));
        mainFrame.setSize(new Dimension(WIDTH, HEIGHT));
        mainFrame.setResizable(false);

        for(int i = 0; i < cax; i++) {
            for(int j = 0; j < cay; j++) {
                grid[i][j] = new JButton("");

                mainPanel.add(grid[i][j]);
            }
        }

        mainFrame.add(start, BorderLayout.NORTH);
        mainFrame.add(reset, BorderLayout.SOUTH);

        mainFrame.add(mainPanel, BorderLayout.CENTER);
        mainFrame.setVisible(true);
    }

    public JButton[][] getGrid() {
        return grid;
    }

    public JButton getStart() {
        return start;
    }

    public JButton getReset() {
        return reset;
    }

    public BufferedImage getMyImage() {
        return myImage;
    }
}
