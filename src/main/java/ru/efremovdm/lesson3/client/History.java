package ru.efremovdm.lesson3.client;

import ru.efremovdm.lesson3.FileReader;
import java.util.List;

public class History {

    private static final String TAG = "HISTORY";
    private FileReader file;

    public History(String nickName) {
        file = new FileReader("history_" + nickName + ".txt");
    }

    public List<String> load(int count) {
        return file.readLines(count);
    }

    public void add(String text) {
        file.addToEnd(text);
    }
}
