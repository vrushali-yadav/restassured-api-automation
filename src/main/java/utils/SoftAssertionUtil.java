package utils;

import org.testng.asserts.SoftAssert;

public class SoftAssertionUtil {

    private static SoftAssert softAssertInstance;

    private SoftAssertionUtil() {
    }

    public static SoftAssert getInstance() {
        if (softAssertInstance == null) {
            softAssertInstance = new SoftAssert();
        }
        return softAssertInstance;
    }

    public static void assertTrue(boolean status, String msg) {
        try {
            getInstance().assertTrue(status, msg);
        } catch (AssertionError e) {
            getInstance().fail(msg);
        }
    }

    public static void assertEquals(Object actual, Object expected, String msg) {
        try {
            getInstance().assertEquals(actual, expected, msg);
        } catch (AssertionError e) {
            getInstance().fail(msg);
        }
    }

    public static void assertNotEquals(Object actual, Object expected, String msg) {
        try {
            getInstance().assertNotEquals(actual, expected, msg);
        } catch (AssertionError e) {
            getInstance().fail(msg);
        }
    }

    public static void assertAll() {
        getInstance().assertAll();
    }
}
