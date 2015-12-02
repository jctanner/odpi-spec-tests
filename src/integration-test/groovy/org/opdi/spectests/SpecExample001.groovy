package org.odpi.spectests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class Spec001 {

    @Before
    public void setUp() {
        println "setup"
    }

    @Test
    public void simpletest() {
        println "Hello Test world!"
        //assertThat("bar").isEqualTo("bar");
    }

    @Test
    public void simpletest2() {
        println "Hello Test world2!"
    }

    @After
    public void tearDown() {
        println "teardown"
    }
}

