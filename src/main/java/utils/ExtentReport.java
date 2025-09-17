package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.File;
import java.io.IOException;

public class ExtentReport {
    public static ExtentReports extentReport = null;
    public static ThreadLocal<ExtentTest> extentLog = new ThreadLocal<>();

    public static void initialize(String extentConfigFilePath) throws IOException {
        if (extentReport == null) {

            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(extentConfigFilePath);

            sparkReporter.loadXMLConfig(new File("resources/extent-config.xml"));

            extentReport = new ExtentReports();
            extentReport.attachReporter(sparkReporter);

            extentReport.setSystemInfo("Host Name", System.getProperty("user.name"));
            extentReport.setSystemInfo("Environment", "QA");

        }
    }

    // Create a test in the report
    public static void createTest(String testName) {
        ExtentTest test = extentReport.createTest(testName);
        extentLog.set(test);
    }


    // Get the current test instance
    public static ExtentTest getTest() {
        return extentLog.get();
    }

    // Log INFO messages
    public static void logInfo(String message) {
        if (extentLog.get() != null) {
            extentLog.get().log(Status.INFO, message);
        }
    }

    // Log PASS messages
    public static void logPass(String message) {
        if (extentLog.get() != null) {
            extentLog.get().log(Status.PASS, message);
        }
    }

    // Log FAIL messages
    public static void logFail(String message) {
        if (extentLog.get() != null) {
            extentLog.get().log(Status.FAIL, message);
        }
    }

    // Flush reports at the end of execution
    public static void flush() {
        if (extentReport != null) {
            extentReport.flush();
        }
    }
}
