package ru.efremovdm.lesson5;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Material {

    /**
     * Semaphore ограничивает количество потоков при работе с ресурсами. Для этого служит счетчик. Если его значение
     * больше нуля, то потоку разрешается доступ, а значение уменьшается. Если счетчик равен нулю, то текущий поток
     * блокируется до освобождения ресурса. Для получения доступа используется метод acquire(), для освобождения – release().
     */
    private static void semaphoreTest() {

        Semaphore smp = new Semaphore(2);

        for (int i = 0; i < 5; i++) {

            final int w = i;

            new Thread(() -> {
                try {
                    System.out.println("Поток " + w + " перед семафором");
                    smp.acquire();
                    System.out.println("Поток " + w + " получил доступ к ресурсу");
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("Поток " + w + " освободил ресурс");
                    smp.release();
                }
            }).start();
        }
    }

    /**
     * CountDownLatch позволяет потоку ожидать завершения операций, выполняющихся в других потоках.
     * Режим ожидания запускается методом await(). При создании объекта определяется количество требуемых операций,
     * после чего уменьшается при вызове метода countDown().
     * Как только счетчик доходит до нуля, с ожидающего потока снимается блокировка.
     */
    private static void countDownLatchTest() {

        // задаем количество потоков
        final int THREADS_COUNT = 6;

        // задаем значение счетчика
        final CountDownLatch cdl = new CountDownLatch(THREADS_COUNT);
        System.out.println("Начинаем");

        for (int i = 0; i < THREADS_COUNT; i++) {

            final int w = i;
            new Thread(() -> {
                try {
                    // считаем, что выполнение задачи занимает ~1 сек
                    Thread.sleep(500 + (int)(500 * Math.random()));
                    cdl.countDown();

                    // как только задача выполнена, уменьшаем счетчик
                    System.out.println("Поток #" + w + " - готов");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        try {
            cdl.await();  // пока счетчик не приравняется нулю, будем стоять на этой строке
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // как только все потоки выполнили свои задачи - пишем сообщение
        System.out.println("Работа завершена");
    }

    /**
     * CyclicBarrier выполняет синхронизацию заданного количества потоков в одной точке.
     * Как только заданное количество потоков заблокировалось (вызовами метода await()),
     * с них одновременно снимается блокировка. => ОДНОВРЕМЕННЫЙ СТАРТ-ПРОДОЛЖЕНИЕ ПОТОКОВ С БАРЬЕРА
     */
    private static void cyclicBarrierTest() {

        CyclicBarrier cb = new CyclicBarrier(3);

        for (int i = 0; i < 3; i++) {

            final int w = i;
            new Thread(() -> {
                try {
                    System.out.println("Поток " + w + " готовится");
                    Thread.sleep(100 + (int) (3000 * Math.random()));
                    System.out.println("Поток " + w + " готов");
                    cb.await();
                    System.out.println("Поток " + w + " запустился");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    /**
     * Создаем объект типа Lock и вызываем у него метод lock() – он захватывается.
     * Если другой поток попытается вызвать этот метод у того же объекта – он будет блокирован до тех пор,
     * пока поток, удерживающий объект lock, не освободит его через метод unlock().
     * Тогда этот объект смогут захватить другие потоки.
     *
     * Основные отличия между Lock и синхронизированными блоками:
     *   Синхронизированные блоки не гарантируют, что сохранится порядок обращения потоков к критической секции;
     *   Нельзя выйти из синхронизированного блока по времени ожидания (timeout);
     *   Синхронизированные блоки должны полностью содержаться в одном методе. Lock может быть захвачен в одном методе, а освобожден в другом.
     */
    private static void lockTest() {

        ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        readWriteLock.readLock().lock();

        // множество читателей может зайти в эту секцию,
        // если нет записывающих потоков
        readWriteLock.readLock().unlock();
        readWriteLock.writeLock().lock();

        // только один поток-писатель может зайти в эту секцию,
        // при условии, что ни один из потоков не занимается чтением
        readWriteLock.writeLock().unlock();
    }

    public static void main(String[] args) {
        semaphoreTest();
        //countDownLatchTest();
        //cyclicBarrierTest();
        //lockTest();
    }
}
