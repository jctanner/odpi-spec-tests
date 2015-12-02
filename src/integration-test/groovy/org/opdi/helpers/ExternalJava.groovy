package org.odpi.helpers;

public class ExternalJava {

    def tempdirpath
    def classfile
    def classname
    def sourcecode

    public ExternalJava(java.lang.String classname, java.lang.String sourcecode) {

        println "########################################"
        println "## input src ... "
        println sourcecode 
        println "########################################"

        this.tempdirpath = createlocaltempdir()
        println this.tempdirpath
        this.classname = classname
        this.sourcecode = sourcecode
        
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
        def javabin = "java"
        return javabin
    }

    def locatejavac() {
        def javacbin = "/bin/javac"
        return javacbin
    }

    def execute() {

        def groovybin = locategroovy()
        def javabin = locatejava()
        def javacbin = locatejavac()
        def hcp = gethadoopclasspath()

        // write out the code
        def codefilename = this.classname + ".java"
        def codefilepath = [this.tempdirpath, codefilename].join(File.separator)
        new File(codefilepath).withWriter{ it << this.sourcecode } 

        ////////////////////////////////////////////////
        //  COMPILE
        ////////////////////////////////////////////////

        println "Compiling file: " + codefilepath
        //def cmd = "" + javacbin + " -version"
        def ccmd = "" + javacbin + " -classpath " + hcp + " " + codefilepath
        println ccmd

        def cprocess = new ProcessBuilder(ccmd.split()).redirectErrorStream(true).start()
        cprocess.inputStream.eachLine {println it}

        ////////////////////////////////////////////////
        //  EXECUTE
        ////////////////////////////////////////////////

        println "Run class: " + this.classname
        //def cmd = "" + javacbin + " -version"
        def rcmd = "" + javabin + " -classpath " + hcp + ":" + this.tempdirpath + "/."
        rcmd +=  " " + this.classname
        println rcmd

        def rprocess = new ProcessBuilder(rcmd.split()).redirectErrorStream(true).start()
        rprocess.inputStream.eachLine {println it}

       

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
