package ru.efremovdm.lesson1;

import java.util.ArrayList;

class Box<T extends Fruit> {

    private ArrayList<T> box = new ArrayList<>();

    Box(){

    }

    float getWeight(){

        float weight = 0.0f;

        for(T o : box){
            weight += o.getWeight();
        }

        return weight;
    }

    boolean compare(Box anotherBox) {
        return getWeight() == anotherBox.getWeight();
    }

    void pourTo(Box <T>anotherBox){
        anotherBox.box.addAll(box);
        box.clear();
    }

    void addFruit(T fruit, int amount){
        for(int i = 0; i < amount; i++){
            box.add(fruit);
        }
    }
}