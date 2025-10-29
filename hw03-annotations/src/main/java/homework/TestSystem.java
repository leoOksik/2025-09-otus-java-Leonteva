package homework;

import homework.test.ArrayTest;
import java.lang.reflect.InvocationTargetException;

public class TestSystem {

    public static void main(String[] args)
            throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        TestRun.run(ArrayTest.class);
    }
}
