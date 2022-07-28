package com.magma.engine.tests;

import com.magma.engine.debug.MagmaLogger;

public abstract class Tester {
    public Tester(){

    }

    public void check(Object result, Object expected, String description){
        if (result != expected){
            throw new TestFailedException("Assertion failed! " + description + " expected: " + expected + " -> got: " + result);
        }
        MagmaLogger.log(this,"✅ Test succeeded!");
    }
}

