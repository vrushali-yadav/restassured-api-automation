package userManagement;

import org.testng.annotations.*;

public class TestNGAnnotationExecutionSequence {

    @Test(dataProvider = "dp")
    public void f(Integer n, String s) {
        System.out.println(n + s);
    }
    @BeforeMethod
    public void beforeMethod() {
        System.out.println("Beforemethod:");
    }
    @AfterMethod
    public void afterMethod() {
        System.out.println("Aftermethod:");
    }
    @DataProvider
    public Object[][] dp() {
        return new Object[][] {
                new Object[] { 1, "a" },
                new Object[] { 2, "b" },
                new Object[] { 0,"2test" }
        };
    }
    @BeforeClass
    public void beforeClass() {
        System.out.println("Beforeclass:");
    }

    @AfterClass
    public void afterClass() {
        System.out.println("AfterClass");
    }

    @BeforeTest
    public void beforeTest() {
        System.out.println("BeforeTest:");
    }

    @AfterTest
    public void afterTest() {
        System.out.println("AfterTest");
    }

    @BeforeSuite
    public void beforeSuite() {
        System.out.println("BeforeSuite");
    }

    @AfterSuite
    public void afterSuite() {
        System.out.println("AfterSuite");
    }

}
