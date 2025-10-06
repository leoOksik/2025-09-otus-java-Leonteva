package ru.otus;

import com.google.common.base.Splitter;
import java.util.List;

@SuppressWarnings("java:S106")
public class HelloOtus {
    public static void main(String... args) {
        String otusStr = "Hello    Otus  Class";

        List<String> strings = Splitter.onPattern("\\s+").trimResults().splitToList(otusStr);

        for (String word : strings) {
            System.out.println("*** " + word + " ***");
        }
    }
}
