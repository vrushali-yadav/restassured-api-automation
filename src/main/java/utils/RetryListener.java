package utils;

import org.testng.IAnnotationTransformer;
import org.testng.IRetryAnalyzer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class RetryListener implements IAnnotationTransformer {

    @Override
    public void transform(ITestAnnotation testAnnotation, Class testClass,
                          Constructor testConstructor, Method testMethod) {

        testAnnotation.setRetryAnalyzer(RetryAnalyzer.class);

// In newer versions of TestNG, "getRetryAnalyzer()" method may not even exist (deprecated/removed)
//       IRetryAnalyzer retry = testannotation.getRetryAnalyzer();
//        if (retry == null)    {
//            testannotation.setRetryAnalyzer(FailRetry.class);//pass the class name created in Step-1
//        }

    }
}