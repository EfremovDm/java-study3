package ru.efremovdm.lesson5;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public abstract class Stage {

    protected int length;

    protected String description;

    public String getDescription() {
        return description;
    }

    public abstract void go(Car c, boolean lastBarrier);

    protected static ReadWriteLock rwLock = new ReentrantReadWriteLock();
    protected static String winner = "";
}