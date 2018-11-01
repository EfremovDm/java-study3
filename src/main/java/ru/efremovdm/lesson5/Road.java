package ru.efremovdm.lesson5;

public class Road extends Stage {

    Road(int length) {
        this.length = length;
        this.description = "Дорога " + length + " метров";
    }

    @Override
    public void go(Car c, boolean lastBarrier) {
        try {
            System.out.println(c.getName() + " начал этап: " + description);

            Thread.sleep(length / c.getSpeed() * 1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            String res = "";
            if (lastBarrier) {
                rwLock.writeLock().lock();
                if (winner.isEmpty()) {
                    winner = c.getName() + " - WIN";
                    res = "\n" + winner;
                }
                rwLock.writeLock().unlock();
            }

            System.out.println(c.getName() + " закончил этап: " + description + res);
        }
    }
}