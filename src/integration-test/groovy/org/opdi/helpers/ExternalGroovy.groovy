package org.odpi.helpers;

public class ExternalGroovy {

    def tempfilepath

    public ExternalGroovy(java.lang.String sourcecode) {

        println "########################################"
        println "## input src ... "
        println sourcecode 
        println "########################################"

        def apath
        File.createTempFile("temp",".groovy").with {
            //deleteOnExit()
            write sourcecode
            println absolutePath
            this.tempfilepath = absolutePath
        }
        
    }

    def gethadoopclasspath() {
        def hcp = "hadoop classpath".execute().text.trim()
        return hcp
    }

    def locategroovy() {
        def groovybin = "/home/testuser1/bin/groovy"

        return groovybin

    }

    def execute() {

        println "Executing file: " + this.tempfilepath
        def groovybin = locategroovy()
        def hcp = gethadoopclasspath()

        def cmd = "" + groovybin + " --classpath=" + "\"" + hcp + "\" " + this.tempfilepath
        println cmd

        //println "## OUTPUT: " + cmd.execute().exitValue()

        def process=new ProcessBuilder(cmd).redirectErrorStream(true).start()
        process.inputStream.eachLine {println it}

    }

}
