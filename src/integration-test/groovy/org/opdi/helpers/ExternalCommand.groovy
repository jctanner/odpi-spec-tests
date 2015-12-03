package org.odpi.helpers;

public class ExternalCommand {

    def cmd

    public ExternalCommand(java.lang.String command) {

        this.cmd = command

    }       

    def execute() {    
        def process = new ProcessBuilder(this.cmd.split()).redirectErrorStream(true).start()
        def output = ""
        process.inputStream.eachLine {
            output += it + "\n"
        }

        process.waitFor()
        def rc = process.exitValue()
        println "RC: " + rc
        return [rc, output, ""]
    }

}
