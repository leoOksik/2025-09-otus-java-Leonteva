package homework;

import homework.annotation.After;
import homework.annotation.Before;
import homework.annotation.Test;
import homework.exception.TestException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class TestRun {

    private static final Logger log = Logger.getLogger(TestRun.class.getName());

    public static void run(Class<?> clazz)
            throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {

        Map<String, List<Method>> methods = getMethods(clazz);
        Constructor<?> constructor = clazz.getConstructor();
        var result = executeTest(methods, constructor);

        countPassedFailedTest(result.passed(), result.failed());
    }

    private static Map<String, List<Method>> getMethods(Class<?> clazz) {
        final Map<String, List<Method>> map = new HashMap<>();

        for (Method method : clazz.getDeclaredMethods()) {
            for (Annotation annotation : method.getAnnotations()) {
                var name = annotation.annotationType().getSimpleName();
                map.computeIfAbsent(name, k -> new ArrayList<>()).add(method);
            }
        }
        return map;
    }

    private static boolean invokeMethod(Method method, Object obj) {
        try {
            method.setAccessible(true);
            method.invoke(obj);

            if (method.isAnnotationPresent(Before.class) || method.isAnnotationPresent(After.class)) {
                log.info(method.getName() + " was executed successfully");
            }
            if (method.isAnnotationPresent(Test.class)) {
                log.info(method.getName() + " PASSED");
            }
            return true;

        } catch (Exception ex) {
            Throwable throwable = ex instanceof InvocationTargetException inv ? inv.getCause() : ex;

            if (throwable instanceof TestException testException) {
                log.warning(method.getName() + " FAILED: " + testException.getMessage());
            } else {
                log.warning("Error in " + method.getName() + " -> " + throwable.getMessage());
            }
        }
        return false;
    }

    private static void countPassedFailedTest(int passed, int failed) {
        log.info("passed test = " + passed + "\nfailed test = " + failed);
    }

    private static TestResult executeTest(Map<String, List<Method>> methods, Constructor<?> constructor)
            throws InvocationTargetException, InstantiationException, IllegalAccessException {

        int passed = 0, failed = 0;

        for (Method testMethod : methods.getOrDefault("Test", List.of())) {
            Object obj = constructor.newInstance();

            methods.getOrDefault("Before", List.of()).forEach(before -> invokeMethod(before, obj));

            if (invokeMethod(testMethod, obj)) {
                passed++;
            } else {
                failed++;
            }

            methods.getOrDefault("After", List.of()).forEach(after -> invokeMethod(after, obj));
        }
        return new TestResult(passed, failed);
    }

    private record TestResult(int passed, int failed) {}
}
