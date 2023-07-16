package com.main;

public class Main {

    public static void main(String[] args) {
        Frame mainFrame = new Frame();
        mainFrame.setup("BurningForest!!!");

        BF burningForest = new BF(mainFrame.cax, mainFrame.cay, mainFrame.getGrid(), mainFrame.getStart(), mainFrame.getReset(), mainFrame.getMyImage());
    }
}
