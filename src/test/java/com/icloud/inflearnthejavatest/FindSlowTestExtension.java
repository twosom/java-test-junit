package com.icloud.inflearnthejavatest;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.lang.reflect.Method;

public class FindSlowTestExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {

    private final long THRESHOLD;

    public FindSlowTestExtension(Long THRESHOLD) {
        this.THRESHOLD = THRESHOLD;
    }


    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        ExtensionContext.Store store = getStore(context);
        store.put("START_TIME", System.currentTimeMillis());
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        Method requiredTestMethod = context.getRequiredTestMethod();
        SlowTest annotation = requiredTestMethod.getAnnotation(SlowTest.class);

        String testMethodName = getTestMethodName(context);
        ExtensionContext.Store store = getStore(context);
        Long startTime = store.remove("START_TIME", Long.class);
        long duration = System.currentTimeMillis() - startTime;
        if (duration > THRESHOLD && annotation == null) {
            System.err.printf("Please consider mark method [ %s ] with SlowTest.\n", testMethodName);
        }
    }

    private ExtensionContext.Store getStore(ExtensionContext context) {
        String testClassName = getTextClassName(context);
        String testMethodName = getTestMethodName(context);
        return context.getStore(ExtensionContext.Namespace.create(testClassName, testMethodName));
    }

    private String getTestMethodName(ExtensionContext context) {
        return context.getRequiredTestMethod().getName();
    }

    private String getTextClassName(ExtensionContext context) {
        return context.getRequiredTestClass().getName();
    }
}
