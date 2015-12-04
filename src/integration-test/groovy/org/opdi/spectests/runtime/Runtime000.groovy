package org.odpi.spectests.runtime;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.odpi.helpers.ExternalCommand
import org.odpi.helpers.ExternalJava

/*
Applications on Unix platforms need to understand the base specification of some key components of which they write software. Two of those components are the Java runtime environment and the shell environment.

    Java: ODPi Platforms SHOULD support both JRE 7 and JRE 8 runtime environments 
          (64-bit only). ODPi Applications SHOULD work in at least one of these, 
          and SHOULD be clear when they don’t support both.

    Shell scripts: ODPi Platforms and Applications SHOULD use either POSIX sh or 
          GNU bash with the appropriate bang path configured for that operating 
          system. GNU bash usage SHOULD NOT require any version of GNU bash later 
          than 3.2.
*/


public class Runtime000 {

    @Before
    public void setUp() {
        //println "setup"
    }

    @Test
    public void checkjavaversion() {

        // What does it mean to "support" JRE 7+8 in a "runtime environment" ?
        // Does it matter for the platform it's self or is it application specific ?


        // Rudimentary check of the java version string ...
        java.lang.String src = ""
        src += "class CheckJavaVersion{\n"
        src += "    public static void main(String[] args) {\n"
        src += "        System.out.println(\"version=\" + System.getProperty(\"java.version\") );\n"
        src += "    }\n"
        src += "}\n"

        def eg = new ExternalJava("CheckJavaVersion", src)
        def cres = eg.compile()
        assert cres[0] == 0 : "compiling checkjavaversion failed"
        def eres = eg.execute()
        assert eres[0] == 0 : "running checkjavaversion failed"

        def jversion = eres[1].trim().split('=')[1].tokenize(".")
        println jversion

        // [1, 8, 0_40]
        assert jversion[0].toInteger() >= 1 : "java major version is " + jversion[0]
        assert ((jversion[1].toInteger() == 7) || (jversion[1].toInteger() == 8)) : "java version is " + jversion[1]

    }

    @Test
    public void checkbashversion() {

        /*
        [testuser1@sandbox odpi-spec-tests]$ bash --version
        GNU bash, version 4.2.46(1)-release (x86_64-redhat-linux-gnu)
        Copyright (C) 2011 Free Software Foundation, Inc.
        License GPLv3+: GNU GPL version 3 or later <http://gnu.org/licenses/gpl.html>

        This is free software; you are free to change and redistribute it.
        There is NO WARRANTY, to the extent permitted by law.
        */

        // Does this imply that the tests should only be run from cygwin?

       def ec = new org.odpi.helpers.ExternalCommand("bash --version")
       def res = ec.execute()

        println res
        def lines = res[1].split("\n")
        def parts = lines[0].split()
        println parts
        def bashversion = parts[3].tokenize(".")

        // [4, 2, 46(1)-release]
        println bashversion

    }


    @After
    public void tearDown() {
        //println "teardown"
    }
}

