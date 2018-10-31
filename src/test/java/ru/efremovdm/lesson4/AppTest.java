package ru.efremovdm.lesson4;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Тест для задания:
     * 1. Создать три потока, каждый из которых выводит определенную букву (A, B и C) 5 раз (порядок – ABСABСABС).
     * Используйте wait/notify/notifyAll.
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        String result = Application.getResult();
        boolean testResult = result.equals("ABCABCABCABCABC");

        assertTrue(testResult);
    }
}
