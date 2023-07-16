package com.main;

import java.util.Random;

public class RNG {

    public RNG() {

    }

    public boolean setAflame(int threshold) {
        boolean result;

        int temp = threshold * 20 + 20;

        Random rand = new Random();
        int random = rand.nextInt(100);

        if(random < temp) {
            result = true;
        } else {
            result = false;
        }
        return result;
    }

    public boolean randomIgnite() {
        boolean result;

        Random rand = new Random();
        int random = rand.nextInt(200);

        if(random < 1) {
            result = true;
        } else {
            result = false;
        }

        return result;
    }

    public boolean rebirth() {
        boolean result;

        Random rand = new Random();
        int random = rand.nextInt(100);

        if(random < 30) {
            result = true;
        } else {
            result = false;
        }

        return result;
    }
}
