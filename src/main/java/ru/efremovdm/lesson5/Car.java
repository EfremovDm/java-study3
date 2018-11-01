package ru.efremovdm.lesson5;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Car implements Runnable {

    private static int CARS_COUNT;
    static {
        CARS_COUNT = 0;
    }
    private Race race;
    private int speed;
    private String name;
    private CountDownLatch startCdl;
    private CyclicBarrier waitCb;
    private static ReadWriteLock rwLock = new ReentrantReadWriteLock();
    private static String winner = "";
    private CountDownLatch finishCdL;

    String getName() {
        return name;
    }

    int getSpeed() {
        return speed;
    }

    Car(Race race, int speed, CountDownLatch startCdl, CyclicBarrier waitCb, CountDownLatch finishCdL) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
        this.startCdl = startCdl;
        this.waitCb = waitCb;
        this.finishCdL = finishCdL;
    }

    @Override
    public void run() {

        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int)(Math.random() * 800));
            System.out.println(this.name + " готов");

            this.startCdl.countDown();
            this.waitCb.await();

            Thread.sleep(1); // слип для остановки текущего потока и вывода сообщения в главном потоке

        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this, i == race.getStages().size() - 1);
        }

        try {
            finishCdL.countDown(); // завершение гонки участником
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
