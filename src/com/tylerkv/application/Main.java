package com.tylerkv.application;

import com.tylerkv.ui.Driver;
import com.tylerkv.ui.frames.MainFrame;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
//        TestDriver.runTestSuite();
//        randomTest();
        start();
    }

    public static void randomTest(){
        ArrayList<Integer> testList = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6));
        Iterator iter = testList.iterator();
        while(iter.hasNext()) {
            int x = (Integer)iter.next();
            if(x == 2) {
                iter.remove();
            }
        }
        System.out.println(testList);
    }

    public static void start() {

        //Safety start
        EventQueue.invokeLater(() -> {
            Driver driver = new Driver();
            driver.start();
        });

    }
}
