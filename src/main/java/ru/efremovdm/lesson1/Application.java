package ru.efremovdm.lesson1;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 1. Написать метод, который меняет два элемента массива местами (массив может быть любого ссылочного типа);
 *
 * 2. Написать метод, который преобразует массив в ArrayList;
 *
 * 3. Большая задача:
 *
 * Есть классы Fruit -> Apple, Orange (больше фруктов не надо);
 * Класс Box, в который можно складывать фрукты. Коробки условно сортируются по типу фрукта, поэтому в одну коробку нельзя сложить и яблоки, и апельсины;
 *
 * Для хранения фруктов внутри коробки можно использовать ArrayList;
 * Сделать метод getWeight(), который высчитывает вес коробки, зная количество фруктов и вес одного фрукта
 * (вес яблока – 1.0f, апельсина – 1.5f. Не важно, в каких это единицах);
 *
 * Внутри класса Коробка сделать метод compare, который позволяет сравнить текущую коробку с той, которую подадут
 * в compare в качестве параметра, true – если она равны по весу, false – в противном случае (коробки с яблоками мы можем сравнивать с коробками с апельсинами);
 * Написать метод, который позволяет пересыпать фрукты из текущей коробки в другую (помним про сортировку
 * фруктов: нельзя яблоки высыпать в коробку с апельсинами). Соответственно, в текущей коробке фруктов не
 * остается, а в другую перекидываются объекты, которые были в этой коробке;
 * Не забываем про метод добавления фрукта в коробку.
 */
public class Application {

    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        task1();
        task2();
        task3();
    }

    /**
     * Написать метод, который меняет два элемента массива местами (массив может быть любого ссылочного типа);
     */
    private static void task1() {
        Integer arInt[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        String arStr[] = {"A", "B", "C", "D", "1"};

        task1Replace(arInt, 2, 3);
        task1Replace(arStr, 1, 2);
    }

    private static void task1Replace(Object[] arr, int n1, int n2) {
        Object temp = arr[n1];
        arr[n1] = arr[n2];
        arr[n2] = temp;
        System.out.println("Task1: " + Arrays.toString(arr));
    }

    /**
     * Написать метод, который преобразует массив в ArrayList;
     */
    private static void task2() {
        String[] arrayOfStrings = {"A", "B", "C", "D", "E"};
        task2AsList(arrayOfStrings);
    }

    private static <T> void task2AsList(T[]arr){
        ArrayList<T> newA = new ArrayList<>(Arrays.asList(arr));
        System.out.println("Task2: " + newA.toString());
    }

    /**
     * Большая задача
     */
    private static void task3() {

        Box<Orange> or = new Box<>();
        Box<Orange> or1 = new Box<>();
        Box<Apple> ap = new Box<>();
        Box<Apple> ap1 = new Box<>();

        System.out.println("Task3");
        System.out.println("'g' - addFruit: ");

        or.addFruit(new Orange(),10);
        or1.addFruit(new Orange(),12);
        ap.addFruit(new Apple(),8);
        ap1.addFruit(new Apple(),4);

        System.out.println("Box 1: " + or.getWeight());
        System.out.println("Box 2: " + or1.getWeight());
        System.out.println("Box 3: " + ap.getWeight());
        System.out.println("Box 4: " + ap1.getWeight());

        System.out.println("'e' - compare(): ");
        System.out.println("Box 1 equals box 3: " + or.compare(ap));
        System.out.println("Box 2 equals box 4: " + or1.compare(ap1));
        System.out.println("'f' - pourTo(): ");

        or.pourTo(or1);
        ap.pourTo(ap1);

        System.out.println("'d' - getWeight(): ");
        System.out.println("Box 1: " + or.getWeight());
        System.out.println("Box 2: " + or1.getWeight());
        System.out.println("Box 3: " + ap.getWeight());
        System.out.println("Box 4: " + ap1.getWeight());
    }
}
