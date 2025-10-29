package homework.test;

import homework.annotation.After;
import homework.annotation.Before;
import homework.annotation.Test;
import homework.exception.TestException;
import homework.utils.Assert;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ArrayTest {

    private List<Integer> testList1 = new ArrayList<>();
    private List<Integer> testList2 = new ArrayList<>();

    @Before
    void set() {
        Random random = new Random();
        testList1 = random.ints(5, 10, 1000).boxed().toList();
        testList2 = random.ints(5, 10, 1000).boxed().toList();
    }

    @Test
    void testCorrectList() throws TestException {
        List<Integer> list = new ArrayList<>(testList1);
        Assert.assertEquals(testList1, list);
    }

    @Test
    void testInCorrectList() throws TestException {
        Assert.assertEquals(testList1, testList2);
    }

    @After
    void clear() {}
}
