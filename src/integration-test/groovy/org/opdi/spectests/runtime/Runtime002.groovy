package org.odpi.spectests.runtime;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

//import org.apache.hadoop.util.VersionInfo

import org.odpi.helpers.ExternalGroovy;
import org.odpi.helpers.ExternalJava;

public class Runtime002 {

    /* ODPi Platforms MUST modify the version string output by Hadoop 
    components, such as those displayed in log files, or returned via 
    public API's such that they contain -(vendor string) where (vendor string) 
    matches the regular expression [A-Za-z_0-9]+ and appropriately identifies 
    the ODPi Platform vendor in the output.*/

    // [testuser1@sandbox odpi-spec-tests]$ find /usr/odp/0.9.0.1-55/ -type f -name "*.jar"  | xargs -I {} unzip -l {} | awk '{print $NF}' | fgrep "org/apache/hadoop/util" | fgrep -i version
    // org/apache/hadoop/util/TestVersionUtil.class
    // org/apache/hadoop/util/ComparableVersion.class
    // org/apache/hadoop/util/ComparableVersion$Item.class
    // org/apache/hadoop/util/ComparableVersion$IntegerItem.class
    // org/apache/hadoop/util/VersionUtil.class
    // org/apache/hadoop/util/ComparableVersion$ListItem.class
    // org/apache/hadoop/util/ComparableVersion$1.class
    // org/apache/hadoop/util/ComparableVersion$StringItem.class
    // org/apache/hadoop/util/VersionInfo.class


    @Before
    public void setUp() {
        //println "setup"
    }

    @Test
    public void hadoopversioncheck() {

        /////////////////////////////////////////////////////
        // Check Hadoop API version strings  
        /////////////////////////////////////////////////////

        /* https://hadoop.apache.org/docs/r1.2.1/api/org/apache/hadoop/util/VersionInfo.html */


        java.lang.String src = ""
        src += "import org.apache.hadoop.util.VersionInfo;\n"
        src += "class HadoopAPIVersion{\n"
        src += "    public static void main(String[] args) {\n"
        src += "        System.out.println(\"getversion=\" + VersionInfo.getVersion());\n"
        src += "        System.out.println(\"getbuildversion=\" + VersionInfo.getBuildVersion());\n"
        src += "        System.out.println(\"getbranch=\" + VersionInfo.getBranch());\n"
        src += "        System.out.println(\"getrevision=\" + VersionInfo.getRevision());\n"
        src += "        System.out.println(\"getprotocversion=\" + VersionInfo.getProtocVersion());\n"
        src += "        System.out.println(\"getsrcchecksum=\" + VersionInfo.getSrcChecksum());\n"
        src += "        System.out.println(\"geturl=\" + VersionInfo.getUrl());\n"
        src += "        System.out.println(\"getdate=\" + VersionInfo.getDate());\n"
        src += "    }\n"
        src += "}\n"

        def eg = new org.odpi.helpers.ExternalJava("HadoopAPIVersion", src)
        def cres = eg.compile()
        assert cres[0] == 0 : "compiling hadoopapiversion failed"
        def eres = eg.execute()
        assert eres[0] == 0 : "running hadoopapiversion failed"

        def vmap = [:]
        eres[1].split('\n').each{ x ->
            if ( x.contains('=') ) {
                def parts = x.split('=')
                vmap[parts[0]] = parts[1]
            }
        }
        println "### version map ..."
        println vmap

        // How do I know what the vendor substring should be? ...

    }


    @After
    public void tearDown() {
        //println "teardown"
    }
}

