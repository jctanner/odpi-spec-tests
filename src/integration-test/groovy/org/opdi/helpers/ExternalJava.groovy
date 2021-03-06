package org.odpi.helpers;

public class ExternalJava {

    def tempdirpath
    def classfile
    def classname
    def sourcecode
    def codefilename
    def codefilepath

    public ExternalJava(java.lang.String classname, java.lang.String sourcecode) {

        println "########################################"
        println "## input src ... "
        println sourcecode 
        println "########################################"

        this.tempdirpath = createlocaltempdir()
        println this.tempdirpath
        this.classname = classname
        this.sourcecode = sourcecode

        // write out the code
        this.codefilename = this.classname + ".java"
        this.codefilepath = [this.tempdirpath, this.codefilename].join(File.separator)
        new File(this.codefilepath).withWriter{ it << this.sourcecode } 
       
    }

    def gethadoopclasspath() {
        def hcp = "hadoop classpath".execute().text.trim()
        return hcp
    }

    def locategroovy() {
        def groovybin = "/home/testuser1/bin/groovy"
        return groovybin
    }

    def locatejava() {
        // what is $JAVA_HOME ?
        def env = System.getenv()
        String JAVA_HOME = env['JAVA_HOME']

        // what is the full path to java ?
        def javacmd = [JAVA_HOME, 'bin', 'java'].join(File.separator)

        return javacmd

    }

    def locatejavac() {
        // what is $JAVA_HOME ?
        def env = System.getenv()
        String JAVA_HOME = env['JAVA_HOME']

        // what is the full path to java ?
        def javacmd = [JAVA_HOME, 'bin', 'javac'].join(File.separator)

        return javacmd
    }

    def compile() {

        ////////////////////////////////////////////////
        //  COMPILE
        ////////////////////////////////////////////////

        def groovybin = locategroovy()
        def javabin = locatejava()
        def javacbin = locatejavac()
        def hcp = gethadoopclasspath()

        println "Compiling file: " + this.codefilepath
        //def cmd = "" + javacbin + " -version"
        def ccmd = "" + javacbin + " -classpath " + hcp + " " + this.codefilepath
        println ccmd

        def cprocess = new ProcessBuilder(ccmd.split()).redirectErrorStream(true).start()
        def output = ""
        cprocess.inputStream.eachLine {
            output += it
            println it
        }

        cprocess.waitFor()
        def rc = cprocess.exitValue()
        println "RC: " + rc
        return [rc, output, ""]
    }

    def execute() {

        ////////////////////////////////////////////////
        //  EXECUTE
        ////////////////////////////////////////////////

        def groovybin = locategroovy()
        def javabin = locatejava()
        def javacbin = locatejavac()
        def hcp = gethadoopclasspath()

        println "Run class: " + this.classname
        //def cmd = "" + javacbin + " -version"
        def rcmd = "" + javabin + " -classpath " + hcp + ":" + this.tempdirpath + "/."
        rcmd +=  " " + this.classname
        println rcmd

        def rprocess = new ProcessBuilder(rcmd.split()).redirectErrorStream(true).start()
        def output = ""
        rprocess.inputStream.eachLine{
            output += it + "\n"
            println it
        }
        rprocess.waitFor()
        def rc = rprocess.exitValue()
        println "RC: " + rc
        return [rc, output, ""]

    }

    def createlocaltempdir() {
        // http://stackoverflow.com/a/617438

        final File temp;

        temp = File.createTempFile("temp", Long.toString(System.nanoTime()));

        if(!(temp.delete()))
        {
            throw new IOException("Could not delete temp file: " + temp.getAbsolutePath());
        }

        if(!(temp.mkdir()))
        {
            throw new IOException("Could not create temp directory: " + temp.getAbsolutePath());
        }

        return (temp);        
    }

}
