package org.odpi.spectests.runtime;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.odpi.helpers.ExternalCommand
import org.odpi.helpers.ExternalJava

/*
The native libraries of Hadoop have historically been a particular point of pain for ISVs. 
The specifications in this subsection should reduce that pain. These options guarantee a 
minimum set of basic functionalities that MUST be available for each of these components, 
including Apache Hadoop native operating system resources required for enabling Kerberos, 
many Java+OS performance and functionality enhancements, and the GZip and Snappy codec 
compression libraries. ODPi Platforms MAY enable other features such as file system 
encryption, however they are considered optional and not part of the base specification.
*/

public class Runtime000a {

    @Before
    public void setUp() {
        //println "setup"
    }


    @Test
    public void checkhadoopnativebuild() {

        // https://issues.apache.org/jira/browse/HADOOP-11313
        // https://hadoop.apache.org/docs/current/hadoop-project-dist/hadoop-common/NativeLibraries.html
        // org.apache.hadoop.util.NativeLibraryChecker
        // https://github.com/apache/hadoop/blob/trunk/hadoop-common-project/hadoop-common/src/main/java/org/apache/hadoop/util/NativeLibraryChecker.java

        /*
        [testuser1@sandbox odpi-spec-tests]$ hadoop checknative -a
        15/12/03 04:54:54 INFO bzip2.Bzip2Factory: Successfully loaded & initialized native-bzip2 library system-native
        15/12/03 04:54:54 INFO zlib.ZlibFactory: Successfully loaded & initialized native-zlib library
        Native library checking:
        hadoop:  true /usr/odp/0.9.0.1-55/hadoop/lib/native/libhadoop.so.1.0.0
        zlib:    true /lib64/libz.so.1
        snappy:  true /usr/odp/0.9.0.1-55/hadoop/lib/native/libsnappy.so.1
        lz4:     true revision:99
        bzip2:   true /lib64/libbz2.so.1
        openssl: false Cannot load libcrypto.so (libcrypto.so: cannot open shared object file: No such file or directory)!
        */


        def ec = new org.odpi.helpers.ExternalCommand("hadoop checknative -a")
        def res = ec.execute()

        //println res
        def lines = res[1].split("\n")
        //println lines

        def lmap = [:]
        lines.each{ x ->
            def parts = x.split()
            if ( parts[0].endsWith(":") ) {
                def key = parts[0].replaceAll(":", "")
                lmap[key] = parts[1].toBoolean()
            }

        }

        println lmap

        // Should we just check that each of these is true?
        def checks = ['hadoop', 'zlib', 'snappy', 'bzip2']
        def failures = 0
        checks.each{ x ->
            if ( lmap.containsKey(x) ) {
                if ( ! lmap[x] ) {
                    println x + " is not true"
                    failures += 1
                }
            } else {
                failures += 1
            }
        }

        assert failures == 0

    }


    @After
    public void tearDown() {
        //println "teardown"
    }
}

