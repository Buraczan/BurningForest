package com.main;

public class Tile {
    private int status;
    private boolean isBurning;
    private boolean isWater;

    public Tile(int color) {
        if(color == 255) {
            this.status = 1;
            this.isBurning = false;
            this.isWater = false;
        } else if(color == 0) {
            this.status = 0;
            this.isBurning = false;
            this.isWater = true;
        } else {
            this.status = 4;
            this.isBurning = false;
            this.isWater = false;
        }
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean getBurning() {
        return isBurning;
    }

    public void setBurning(boolean burning) {
        isBurning = burning;
    }

    public boolean getWater() {
        return isWater;
    }

    public void setWater(boolean water) {
        isWater = water;
    }
}
