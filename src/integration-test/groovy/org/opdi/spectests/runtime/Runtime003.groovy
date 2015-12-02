package org.odpi.spectests.runtime;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.odpi.helpers.ExternalCommand

public class Runtime003 {

    /*
    It MUST be possible to determine key Hadoop configuration values by 
    using \${HADOOP_HDFS_HOME}/bin/hdfs getconf so that directly reading 
    the XML via Hadoop’s Configuration object SHOULD NOT be required.
    */

    @Before
    public void setUp() {
        //println "setup"
    }


    @Test
    public void hdfsconfigs() {

        // [testuser1@sandbox odpi-spec-tests]$ hdfs getconf -confKey yarn.resourcemanager.address
        // sandbox.odp.com:8050

        def basecmd = "hdfs getconf -confKey"
        def confkeys = ['yarn.resourcemanager.address']

        def failures = 0
        confkeys.each{ x ->

            def thiscmd = basecmd + " " + x
            def ec = new org.odpi.helpers.ExternalCommand(thiscmd)
            def res = ec.execute()

            try {
                assert res[0] == 0: thiscmd + " failed"
                assert res[1] != null : thiscmd + " returned null output"
            } catch ( AssertionError e) {
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

