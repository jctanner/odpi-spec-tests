package org.odpi.spectests.runtime;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class Runtime001 {


    @Before
    public void setUp() {
        //println "setup"
    }

    @Test
    public void systemvars() {

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
    public void hadoopvars() {

        // https://issues.apache.org/jira/browse/HADOOP-12366


        assert "hadoop envvars".execute().exitValue() == 0 : "hadoop envvars failed"
        def output = "hadoop envvars".execute().text

        /*
        def output = "JAVA_HOME=foobar\n"
        output += "HADOOP_COMMON_HOME=foobaz\n"
        */

        // tokenize the output to a hashmap
        def varmap = [:]
        output.split().each { x ->
            if ( x.contains('=') )
                def parts = x.tokenize('=')
                varmap[parts[0]] = parts[1]
        }

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

