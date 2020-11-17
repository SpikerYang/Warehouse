package com.uci.warehouse.Test;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * @Author spike
 * @Date: 2020-11-14 20:04
 */
public class TestRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(OrderTest.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }

        System.out.println(result.wasSuccessful());
    }
}
