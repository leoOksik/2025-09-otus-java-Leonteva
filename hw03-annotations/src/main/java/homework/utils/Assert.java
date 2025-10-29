package homework.utils;

import homework.exception.TestException;

public class Assert {

    public static void assertEquals(Object expected, Object actual) throws TestException {
        if (!expected.equals(actual)) {
            throw new TestException(String.format("\nExpected: %s \nActual: %s\n", expected, actual));
        }
    }
}
