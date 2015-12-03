package org.odpi.spectests.runtime;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.odpi.helpers.ExternalCommand


public class Runtime001 {

    /* All previously named environment variables mentioned in this section 
    MUST be either explicitly set or readable via running the appropriate 
    bin command with the envvars parameter. In the situation where these 
    variables are not explicitly set, the appropriate commands MUST be 
    available on the path. For example, hadoop envvars should provide 
    output similar to the following ... */

    @Before
    public void setUp() {
        //println "setup"
    }

    @Test
    public void systemenvvars() {

        // https://github.com/apache/hadoop/blob/0bc15cb6e60dc60885234e01dec1c7cb4557a926/hadoop-common-project/hadoop-common/src/main/bin/hadoop-layout.sh.example

        def failures = 0
        def evars = ['JAVA_HOME', 
                     'HADOOP_COMMON_HOME', 
                     'HADOOP_COMMON_DIR', 
                     'HADOOP_COMMON_LIB_JARS_DIR',
                     'HADOOP_CONF_DIR',
                     'HADOOP_HDFS_HOME',
                     'HDFS_DIR',
                     'HDFS_LIB_JARS_DIR',
                     'HADOOP_YARN_HOME',
                     'YARN_DIR',
                     'YARN_LIB_JARS_DIR',
                     'HADOOP_MAPRED_HOME',
                     'MAPRED_DIR',
                     'MAPRED_LIB_JARS_DIR',
                     'HADOOP_TOOLS_PATH' ]

        evars.each{ x ->
            def setvalue = System.getenv(x)
            try {
                assert setvalue != null : x + " is not set"
                println x + " is set"
            } catch ( AssertionError e) {
                failures += 1
                println x + " is not set"
            }
        }

        assert failures == 0

    }

    @Test
    public void hadoopenvvars() {

        // https://issues.apache.org/jira/browse/HADOOP-12366

        def thiscmd = "hadoop envvars"
        def ec = new org.odpi.helpers.ExternalCommand(thiscmd)
        def res = ec.execute()
        assert res[0] == 0 : thiscmd + " failed to run"

        // tokenize the output to a hashmap
        def varmap = [:]
        res[1].split("\n").each { x ->
            if ( x.contains('=') ) {
                def parts = x.split('=')
                println parts
                varmap[parts[0]] = parts[1]
            }
        }

        def evars = ['JAVA_HOME', 
                     'HADOOP_MAPRED_HOME',
                     'MAPRED_DIR',
                     'MAPRED_LIB_JARS_DIR',
                     'HADOOP_CONF_DIR',
                     'HADOOP_TOOLS_PATH' ]

        def failures = 0
        evars.each{ x ->

            def setvalue = System.getenv(x)

            try {
                assert varmap.containsKey(x) : x + " is not set"
                println x + " is set"
            } catch ( AssertionError e) {
                failures += 1
                println x + " is not set"
            }
        }

        assert failures == 0

    }

    @Test
    public void hdfsenvvars() {
        // https://issues.apache.org/jira/browse/HADOOP-12366

        def thiscmd = "hdfs envvars"
        def ec = new org.odpi.helpers.ExternalCommand(thiscmd)
        def res = ec.execute()
        assert res[0] == 0 : thiscmd + " failed to run"

        // tokenize the output to a hashmap
        def varmap = [:]
        res[1].split("\n").each { x ->
            if ( x.contains('=') ) {
                def parts = x.split('=')
                println parts
                varmap[parts[0]] = parts[1]
            }
        }

        def evars = ['JAVA_HOME', 
                     'HADOOP_HDFS_HOME',
                     'HDFS_DIR',
                     'HDFS_LIB_JARS_DIR',
                     'HADOOP_CONF_DIR',
                     'HADOOP_TOOLS_PATH' ]

        def failures = 0
        evars.each{ x ->

            def setvalue = System.getenv(x)

            try {
                assert varmap.containsKey(x) : x + " is not set"
                println x + " is set"
            } catch ( AssertionError e) {
                failures += 1
                println x + " is not set"
            }
        }

        assert failures == 0

    }


    @Test
    public void mapredenvvars() {
        // https://issues.apache.org/jira/browse/HADOOP-12366

        def thiscmd = "mapred envvars"
        def ec = new org.odpi.helpers.ExternalCommand(thiscmd)
        def res = ec.execute()
        assert res[0] == 0 : thiscmd + " failed to run"

        // tokenize the output to a hashmap
        def varmap = [:]
        res[1].split("\n").each { x ->
            if ( x.contains('=') ) {
                def parts = x.split('=')
                println parts
                varmap[parts[0]] = parts[1]
            }
        }

        def evars = ['JAVA_HOME', 
                     'HADOOP_MAPRED_HOME',
                     'MAPRED_DIR',
                     'MAPRED_LIB_JARS_DIR',
                     'HADOOP_CONF_DIR',
                     'HADOOP_TOOLS_PATH' ]

        def failures = 0
        evars.each{ x ->

            def setvalue = System.getenv(x)

            try {
                assert varmap.containsKey(x) : x + " is not set"
                println x + " is set"
            } catch ( AssertionError e) {
                failures += 1
                println x + " is not set"
            }
        }

        assert failures == 0

    }

    @Test
    public void yarnenvvars() {
        // https://issues.apache.org/jira/browse/HADOOP-12366

        def thiscmd = "yarn envvars"
        def ec = new org.odpi.helpers.ExternalCommand(thiscmd)
        def res = ec.execute()
        assert res[0] == 0 : thiscmd + " failed to run"

        // tokenize the output to a hashmap
        def varmap = [:]
        res[1].split("\n").each { x ->
            if ( x.contains('=') ) {
                def parts = x.split('=')
                println parts
                varmap[parts[0]] = parts[1]
            }
        }

        def evars = ['JAVA_HOME', 
                     'HADOOP_YARN_HOME',
                     'YARN_DIR',
                     'YARN_LIB_JARS_DIR',
                     'HADOOP_CONF_DIR',
                     'HADOOP_TOOLS_PATH' ]

        def failures = 0
        evars.each{ x ->

            def setvalue = System.getenv(x)

            try {
                assert varmap.containsKey(x) : x + " is not set"
                println x + " is set"
            } catch ( AssertionError e) {
                failures += 1
                println x + " is not set"
            }
        }

        assert failures == 0

    }



    @After
    public void tearDown() {
        //println "teardown"
    }
}

