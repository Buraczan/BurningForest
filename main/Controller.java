package com.main;

public class Controller implements Runnable{
    private BF mainBF;

    public Controller(BF mainBF) {
        this.mainBF = mainBF;
    }

    @Override
    public void run() {

        while(mainBF.getFlag()) {
            mainBF.play();
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
