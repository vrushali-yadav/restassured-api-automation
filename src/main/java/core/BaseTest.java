package core;

import helper.BaseTestHelper;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import utils.ExtentReport;

import java.io.IOException;

public class BaseTest {


    @BeforeSuite
    public void config() throws IOException {

        // Create a path where we will create a folder to store HTML reports
        String subFolderPath = System.getProperty("user.dir")+"/reports/"+ BaseTestHelper.timestamp();
        System.out.println(subFolderPath);

        //create sub folder
        BaseTestHelper.createFolder(subFolderPath);
        ExtentReport.initialize(subFolderPath + "/" + "API_Execution_Automation.html");
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest(ITestContext context) {
        // Create a new test entry for each test method
        ExtentReport.createTest(context.getName());
    }

    @AfterMethod(alwaysRun = true)
    public void getResult(ITestResult result) {

        if(ExtentReport.getTest() == null) return;

        switch (result.getStatus()) {

            case ITestResult.SUCCESS:
                ExtentReport.logPass("Test Case : " + result.getName() + " is passed ");
                break;

            case ITestResult.FAILURE:
                ExtentReport.logFail("Test Case : " + result.getName() + " is passed ");
                ExtentReport.logFail("Test Case failed due to " + result.getThrowable());
                break;

            case ITestResult.SKIP:
                ExtentReport.logInfo("Test Case " + result.getName() + " is skipped ");
                break;

        }
    }

    @AfterSuite(alwaysRun = true)
    public void endReport(){
        ExtentReport.flush();
    }

}
