package com.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class BF {
    private int height;
    private int width;
    private boolean flag;
    private boolean burned;
    private boolean alive;
    private BufferedImage myImage;

    private Tile[][] mainCA;
    private Tile[][] tempCA;

    private JButton[][] grid;
    private JButton start;
    private JButton reset;

    public BF(int width, int height, JButton[][] grid, JButton start, JButton reset, BufferedImage Image) {
        this.width = width;
        this.height = height;

        this.grid = grid;
        this.start = start;
        this.reset = reset;

        this.flag = true;
        this.burned = false;
        this.alive = true;

        this.myImage = Image;

        mainCA = new Tile[width][height];
        tempCA = new Tile[width][height];

        importMap();

        buttonControls();
        update();
    }

    public void importMap() {
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                Color c = new Color(myImage.getRGB(i, j));
                mainCA[j][i] = new Tile(c.getRed());
                tempCA[j][i] = new Tile(c.getRed());
            }
        }
    }

    private int countNeighbours(int x, int y) {
        int count = 0;

        if(x - 1 >= 0 && y - 1 >= 0 && mainCA[x-1][y-1].getBurning()) {
            count ++;
        }
        if(x - 1 >= 0 && mainCA[x-1][y].getBurning()) {
            count ++;
        }
        if(x - 1 >= 0 && y + 1 < height && mainCA[x-1][y+1].getBurning()) {
            count ++;
        }
        if(y - 1 >= 0 && mainCA[x][y-1].getBurning()) {
            count ++;
        }
        if(y + 1 < height && mainCA[x][y+1].getBurning()) {
            count ++;
        }
        if(x + 1 < width && y - 1 >= 0 && mainCA[x+1][y-1].getBurning()) {
            count ++;
        }
        if(x + 1 < width && mainCA[x+1][y].getBurning()) {
            count ++;
        }
        if(x + 1 < width && y + 1 < height && mainCA[x+1][y+1].getBurning()) {
            count ++;
        }

        return count;
    }

    public void play() {
        RNG rng = new RNG();

        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                if(mainCA[i][j].getStatus() == 0) {
                    tempCA[i][j].setStatus(0);
                    tempCA[i][j].setBurning(false);
                    tempCA[i][j].setWater(true);
                }
                if(mainCA[i][j].getStatus() == 1 && countNeighbours(i, j) >= 1) {
                    if(rng.setAflame(countNeighbours(i, j))) {
                        tempCA[i][j].setStatus(2);
                        tempCA[i][j].setBurning(true);
                    } else {
                        tempCA[i][j].setStatus(1);
                        tempCA[i][j].setBurning(false);
                    }
                }
                if(mainCA[i][j].getStatus() == 2) {
                    tempCA[i][j].setStatus(3);
                    tempCA[i][j].setBurning(false);
                }
                if(mainCA[i][j].getStatus() == 1 && this.alive && !this.burned) {
                    if(rng.randomIgnite()) {
                        tempCA[i][j].setStatus(2);
                        tempCA[i][j].setBurning(true);
                    }
                }
                if(mainCA[i][j].getStatus() == 3 && this.burned && !this.alive) {
                    if(rng.rebirth()) {
                        tempCA[i][j].setStatus(1);
                        tempCA[i][j].setBurning(false);
                    }
                }
            }
        }

        checkPhase();

        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                int tempS = tempCA[i][j].getStatus();
                boolean tempB = tempCA[i][j].getBurning();

                mainCA[i][j].setStatus(tempS);
                mainCA[i][j].setBurning(tempB);
            }
        }

        update();
    }

    private void checkPhase() {
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                if(mainCA[i][j].getBurning()) {
                    this.burned = false;
                    this.alive = false;
                    return;
                }
            }
        }

        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                if(mainCA[i][j].getStatus() == 3) {
                    this.burned = true;
                    this.alive = false;
                    return;
                }
            }
        }

        this.burned = false;
        this.alive = true;

    }

    private void reset() {
        importMap();

        update();
    }

    private void buttonControls() {
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {

                final int x = i;
                final int y = j;

                grid[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (mainCA[x][y].getStatus() == 0) {
                            mainCA[x][y].setStatus(1);
                            mainCA[x][y].setBurning(false);
                            grid[x][y].setBackground(Color.GREEN);
                        } else if (mainCA[x][y].getStatus() == 1) {
                            mainCA[x][y].setStatus(2);
                            mainCA[x][y].setBurning(true);
                            grid[x][y].setBackground(Color.RED);
                        } else if (mainCA[x][y].getStatus() == 2) {
                            mainCA[x][y].setStatus(3);
                            mainCA[x][y].setBurning(false);
                            grid[x][y].setBackground(Color.YELLOW);
                        } else if (mainCA[x][y].getStatus() == 3) {
                            mainCA[x][y].setStatus(0);
                            mainCA[x][y].setBurning(false);
                            grid[x][y].setBackground(new Color(0, 0, 255));
                        }
                    }
                });

            }
        }

        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                flag = true;
                Runnable r1;
                r1 = new Controller(BF.this);
                Thread t1 = new Thread(r1);
                t1.start();
            }
        });

        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                flag = false;
                BF.this.reset();
            }
        });
    }

    public void update() {
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                if(mainCA[i][j].getStatus() == 0) {
                    grid[i][j].setBackground(new Color(0, 0, 255));
                } else if(mainCA[i][j].getStatus() == 1) {
                    grid[i][j].setBackground(Color.GREEN);
                } else if(mainCA[i][j].getStatus() == 2) {
                    grid[i][j].setBackground(Color.RED);
                } else if(mainCA[i][j].getStatus() == 3) {
                    grid[i][j].setBackground(Color.YELLOW);
                }
            }
        }
    }

    public boolean getFlag() {
        return flag;
    }
}
